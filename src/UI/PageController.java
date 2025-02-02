package UI;

import UI.Pages.Calendar.Calendar;
import UI.Pages.MainPage.Message;
import UI.Pages.Page;
import UI.Pages.Temp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageController {

    public List<Message> log = new ArrayList<>();

    private static PageController instance;

    public final Stage stage;
    final int windowWidth;
    final int windowHeight;
    HashMap<String, Page> controllers = new HashMap<>();

    public static PageController instance(Stage primaryStage, int windowWidth, int windowHeight) throws IOException {
        if(instance==null) {
            instance = new PageController(primaryStage, windowWidth, windowHeight);
        }
        return instance;
    }
    public static PageController instance(){
        return instance;
    }
    public static void init() throws IOException {
        if(Main.username().equals("")){
            instance().setScene("frontpage");
        }else{
            instance().setScene("mainpage");
        }
    }

    public static void scene(String scene) throws IOException {
        instance.setScene(scene);
    }

    private PageController(Stage primaryStage, int windowWidth, int windowHeight) throws IOException {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        stage = primaryStage;
    }

    private void setScene(String scene) throws IOException {
        scene = scene.toLowerCase();
        if(!controllers.containsKey(scene)){
            if(Helper.in(scene, new String[]{"frontpage", "mainpage", "skillcreation"})){
                getSceneByFXML(scene);
            }
            else{
                switch (scene){
                    case "temp":
                        Temp t = new Temp();
                        init(scene, t.root, t);
                        break;
                    case "calendar":
                        Calendar c = new Calendar();
                        init(scene, c.root, c);
                        break;
                }
            }
        }
        Page controller = controllers.get(scene);
        assert controller!=null;
        controller.show();
        stage.setScene(controller.scene);
        stage.show();
    }

    private void getSceneByFXML(String scene) throws IOException {
        String path ="";
        switch(scene){
            case "frontpage":
                path = "/UI/FXML/FrontPage.fxml";
                break;
            case "mainpage":
                path = "/UI/FXML/MainPage.fxml";
                break;
            case "skillcreation":
                path = "/UI/FXML/skillCreationPage.fxml";
                break;
        }
        assert !path.equals("");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        init(scene, loader.load(), loader.getController());
    }

    private void init(String scene, Parent root, Page controller){
        controllers.put(scene, controller);
        controllers.get(scene).scene = new Scene(root, windowWidth, windowHeight);
        controller.init();
    }
}
