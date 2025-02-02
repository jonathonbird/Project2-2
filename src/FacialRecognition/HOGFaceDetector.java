package FacialRecognition;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.SVM;
import org.opencv.objdetect.HOGDescriptor;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.List;


public class HOGFaceDetector implements FaceDetector {

    public static boolean foundFace;
    private Mat matrix;
    Stage stage;
    Group root;
    Size dim = new Size(128,64);

    @Override
    public boolean isFaceFound() {
        return foundFace;
    }

    @Override
    public void init(Stage stage) {
        this.stage=stage;
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        WritableImage writableImage = captureSnapShot();
        saveImage();
        ImageView imageView = new ImageView(writableImage);
        imageView.setFitHeight(400);
        imageView.setFitWidth(600);

        // Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        // Creating a Group object
        root = new Group(imageView);

        // Creating a scene object
        Scene scene = new Scene(root, 600, 400);

        // Setting title to the Stage
        stage.setTitle("Capturing an image");

        // Adding scene to the stage
        stage.setScene(scene);

        // Displaying the contents of the stage
        stage.show();

    }

    public WritableImage captureSnapShot() {
        WritableImage WritableImage = null;

        // Loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        // Instantiating the VideoCapture class (camera:: 0)
        VideoCapture capture = new VideoCapture(0);

        // Reading the next video frame from the camera
        Mat matrix = new Mat();
        capture.read(matrix);

        // If camera is opened
        if( capture.isOpened()) {
            // If there is next video frame
            if (capture.read(matrix)) {
                // Creating BuffredImage from the matrix
                BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
                WritableRaster raster = image.getRaster();
                DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
                byte[] data = dataBuffer.getData();
                matrix.get(0, 0, data);
                this.matrix = matrix;

                // Creating the Writable Image
                WritableImage = SwingFXUtils.toFXImage(image, null);
            }
        }
        return WritableImage;
    }
    public void saveImage() {
        // Saving the Image
        //String file = "E:/OpenCV/chap22/sanpshot.jpg";
        String file = "./res/snapshot.jpg";

        // Instantiating the imgcodecs class
        Imgcodecs imageCodecs = new Imgcodecs();

        // Saving it again
        imageCodecs.imwrite(file, matrix);
    }

    @Override
    public void findFace() {
        SVM svm = SVM.load("detect.xml");
        Mat bicubic = new Mat();
        Imgproc.resize(matrix,bicubic,dim,0,0,Imgproc.INTER_CUBIC);
        Mat grey = new Mat();
        Imgproc.cvtColor(bicubic,grey,Imgproc.COLOR_BGR2GRAY);
        MatOfInt uint8 = new MatOfInt();
        grey.convertTo(uint8,CvType.CV_8U);

        HOGDescriptor hog = new HOGDescriptor(dim,new Size(16,16),new Size(8,8),new Size(8,8),9,1,4.,0,2.0000000000000001e-01,false,64);
        MatOfFloat hogimage = new MatOfFloat();
        hog.compute(uint8,hogimage);


        float val = svm.predict(hogimage.reshape(1,1));
        String name = "";
        System.out.println(val == 1 ? "face found" : "face not found");
        foundFace= val == 1;
        if(foundFace){
            SVM svm1 = SVM.load("recogmodel.xml");
            float val1 = svm1.predict(hogimage.reshape(1,1));
            System.out.println(val1);
            if(val1==0){
                name = "Alaa";
            }
            if(val1==1){
                name = "Jonathon";
            }
            if(val1==2){
                name = "Jonathon";
            }
            if(val1==3){
                name= "Davit";
            }
            if(val1==4){
                name = "Ivan";
            }
            Label result = new Label(name);
            result.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 80, 0.7), new CornerRadii(5.0), new Insets(-5.0))));
            result.setFont(new Font("Arial", 30));
            result.setTextFill(Color.WHITE);
            root.getChildren().add(result);
            stage.show();
        }
    }
}
