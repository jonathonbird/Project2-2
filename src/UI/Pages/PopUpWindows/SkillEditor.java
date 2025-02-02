package UI.Pages.PopUpWindows;

import Actions.*;
import Inputs.SEFetchArticles;
import Utils.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SkillEditor{

    static final int skillEditorWidth=450;
    static final int skillEditorHeight=450;

    public static void open() {
        Stage stage = new Stage();
        TextField commandInput = new TextField();
        commandInput.setPrefWidth(skillEditorWidth - 150);
        Label cm = new Label("Enter command");
        ObservableList<Action> skillList = FXCollections.observableList(Data.allSkills);
        ComboBox skills = new ComboBox(skillList);
        final Action[] ac = new Action[1];
        ComboBox objects = new ComboBox();
        Label objectLabel = new Label("Choose article");
        objectLabel.setVisible(false);
        objects.setVisible(false);
        ComboBox inputs = new ComboBox();
        Label inputLabel = new Label("Choose user input, click 'Enter input' to insert it in the command line");
        inputLabel.setVisible(false);
        inputs.setVisible(false);
        ComboBox limiters = new ComboBox();
        Label limiterLabel = new Label("Click interpret on the command line to see what limiters are available for your user inputs.");
        limiterLabel.setVisible(false);
        limiters.setVisible(false);
        Button interpret = new Button("Interpret");
        Button enterInput = new Button("Enter input");
        interpret.setVisible(false);
        enterInput.setVisible(false);
        Button addLimiter = new Button("Add");
        Button removeLimiter = new Button("Remove");
        addLimiter.setVisible(false);
        removeLimiter.setVisible(false);
        EventHandler<ActionEvent> setAc =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        ac[0] = (Action) skills.getValue();
                        if (skills.getValue() instanceof Fetch) {
                            ObservableList<SEFetchArticles> objectList = FXCollections.observableArrayList(Data.seFetch.articles);
                            objects.setItems(objectList);
                            objects.setVisible(true);
                            objectLabel.setVisible(true);
                        }
                        if (skills.getValue() instanceof Open) {
                            objects.setItems(null);
                            ObservableList<String> inpus = FXCollections.observableArrayList(Data.seOpen.inputs);
                            inputs.setItems(inpus);
                            inputs.setVisible(true);
                            inputLabel.setVisible(true);
                            interpret.setVisible(true);
                            enterInput.setVisible(true);
                            objectLabel.setVisible(false);
                            objects.setVisible(false);
                            limiterLabel.setVisible(false);
                            limiters.setVisible(false);
                            addLimiter.setVisible(false);
                            removeLimiter.setVisible(false);
                        }
                        if (skills.getValue() instanceof Create) {
                            objects.setItems(null);
                            ArrayList<String> inp = new ArrayList<>();
                            inp.add("<ARTICLE>");
                            ObservableList<String> inpus = FXCollections.observableArrayList(inp);
                            inputs.setItems(inpus);
                            inputs.setVisible(true);
                            inputLabel.setVisible(true);
                            interpret.setVisible(true);
                            enterInput.setVisible(true);
                            objectLabel.setVisible(false);
                            objects.setVisible(false);
                            limiterLabel.setVisible(false);
                            limiters.setVisible(false);
                            addLimiter.setVisible(false);
                            removeLimiter.setVisible(false);
                        }
                        if (skills.getValue() instanceof Set) {
                            objects.setItems(null);
                            ArrayList<String> inp = new ArrayList<>();
                            inp.add("<TIME>");
                            ObservableList<String> inpus = FXCollections.observableArrayList(inp);
                            inputs.setItems(inpus);
                            inputs.setVisible(true);
                            inputLabel.setVisible(true);
                            interpret.setVisible(true);
                            enterInput.setVisible(true);
                            objectLabel.setVisible(false);
                            objects.setVisible(false);
                            limiterLabel.setVisible(false);
                            limiters.setVisible(false);
                            addLimiter.setVisible(false);
                            removeLimiter.setVisible(false);
                        }
                    }
                };
        skills.setOnAction(setAc);
        final SEFetchArticles[] in = {null};
        inputs.setMinWidth(200);
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        if (ac[0] instanceof Fetch) {
                            in[0] = (SEFetchArticles) (objects.getValue());
                            ObservableList<String> inputList = FXCollections.observableArrayList(in[0].inputs);
                            inputs.setItems(inputList);
                            inputs.setVisible(true);
                            inputLabel.setVisible(true);
                            interpret.setVisible(true);
                            enterInput.setVisible(true);
                            ObservableList<String> initialLimits;
                            if (in[0].nonInputLimiter) {
                                initialLimits = FXCollections.observableArrayList(in[0].eachInputsLimiters.get(0));
                            } else {
                                initialLimits = FXCollections.observableArrayList(new ArrayList<>());
                            }
                            limiters.setItems(initialLimits);
                            limiters.setVisible(true);
                            limiterLabel.setVisible(true);
                            addLimiter.setVisible(true);
                            removeLimiter.setVisible(true);
                        } else {
                            limiters.setItems(null);
                        }
                    }
                };

        // Set on action
        objects.setOnAction(event);
        EventHandler<ActionEvent> event2 =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        if (commandInput.getText().length() == 0 || commandInput.getText().equalsIgnoreCase(" ")) {
                            commandInput.setText("" + inputs.getValue());
                        } else {
                            commandInput.setText(commandInput.getText().trim() + " " + inputs.getValue());
                        }
                    }
                };

        enterInput.setOnAction(event2);
        enterInput.setMinWidth(100);
        EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (ac[0] instanceof Fetch) {
                    ArrayList<Integer> possibleLimiters = new ArrayList<>();
                    String[] toAnalyze = commandInput.getText().split(" ");
                    for (int p = 0; p < in[0].inputs.size(); p++) {
                        for (String s : toAnalyze) {
                            System.out.println(s);
                            if (s.equalsIgnoreCase(in[0].inputs.get(p))) {
                                System.out.println(in[0].inputs.get(p));
                                if (in[0].nonInputLimiter) {
                                    possibleLimiters.add(p + 1);
                                } else {
                                    possibleLimiters.add(p);
                                }
                            }
                        }
                    }
                    ArrayList<String> limits = new ArrayList<>();
                    if (in[0].nonInputLimiter) {
                        for (int i = 0; i < in[0].eachInputsLimiters.get(0).size(); i++) {
                            limits.add(in[0].eachInputsLimiters.get(0).get(i));
                        }
                    }
                    for (Integer i : possibleLimiters) {
                        limits.addAll(in[0].eachInputsLimiters.get(i));
                    }
                    ObservableList<String> limits2 = FXCollections.observableArrayList(limits);
                    limiters.setItems(limits2);
                }
            }
        };
        interpret.setOnAction(event3);
        Label limiterText = new Label();
        EventHandler<ActionEvent> event4 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (ac[0] instanceof Fetch) {
                    if (limiterText.getText().length() > 1) {
                        limiterText.setText(limiterText.getText().trim() + " && " + limiters.getValue().toString());
                    } else {
                        limiterText.setText(limiters.getValue().toString());
                    }
                }
            }
        };
        addLimiter.setOnAction(event4);
        EventHandler<ActionEvent> event5 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (ac[0] instanceof Fetch) {
                    String[] temp = limiterText.getText().split("&&");
                    String toEnter = "";
                    for (int i = 0; i < temp.length - 2; i++) {
                        toEnter += temp[i];
                        if (i != temp.length - 2) {
                            toEnter += " && ";
                        }
                    }
                    limiterText.setText(toEnter);
                }
            }
        };
        removeLimiter.setOnAction(event5);
        GridPane root = new GridPane();
        root.add(cm, 0, 0, 1, 1);
        root.add(commandInput, 1, 0);
        root.add(interpret, 2, 0);
        root.add(skills, 1, 1);
        Label skillLabel = new Label("Choose action");
        root.add(skillLabel, 0, 1);
        root.add(objects, 1, 2);
        objectLabel.setWrapText(true);
        root.add(objectLabel, 0, 2);
        root.add(inputs, 1, 3);
        inputLabel.setWrapText(true);
        root.add(inputLabel, 0, 3);
        root.add(enterInput, 2, 3);
        root.add(limiters, 0, 5);
        limiterLabel.setWrapText(true);
        root.add(limiterLabel, 0, 6);
        root.add(limiterText, 0, 7, 5, 2);
        root.add(addLimiter, 1, 5);
        root.add(removeLimiter, 2, 5);
        Button generate = new Button("Generate");
        EventHandler<ActionEvent> event6 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!commandInput.getText().equalsIgnoreCase("")) {
                    try {
                        FileWriter writer = new FileWriter("src/SkillParserFiles/skills.txt", true);
                        if (!Create.newLineExists(new File("src/SkillParserFiles/skills.txt"))) {
                            writer.write("\n");
                        }
                        if (ac[0] instanceof Fetch) {
                            writer.write(commandInput.getText() + "\n");
                            writer.write(skills.getValue().toString() + "\n");
                            writer.write(objects.getValue().toString() + "\n");
                            if (limiterText.getText().length() == 0) {
                                writer.write("all" + "\n");
                            } else {
                                writer.write(limiterText.getText() + "\n");
                            }
                            writer.write("all" + "\n");
                            writer.write("-");
                        }
                        if (ac[0] instanceof Open) {
                            writer.write(commandInput.getText() + "\n");
                            writer.write(ac[0].toString() + "\n");
                            writer.write("Webpage" + "\n");
                            writer.write("-");
                        }
                        if (ac[0] instanceof Create) {
                            writer.write(commandInput.getText() + "\n");
                            writer.write(ac[0].toString() + "\n");
                            writer.write("-");
                        }
                        if (ac[0] instanceof Set) {
                            writer.write(commandInput.getText() + "\n");
                            writer.write(ac[0].toString() + "\n");
                            writer.write("Timer" + "\n");
                            writer.write("-");
                        }
                        writer.close();
                        System.out.println("Data length: " + Data.commands.size());
                        Data.fillData();
                        System.out.println("Data length2:" + Data.commands.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        generate.setOnAction(event6);
        root.add(generate, 0, 9);
        stage.setTitle("Skill Editor");
        stage.setScene(new Scene(root, skillEditorWidth, skillEditorHeight));
        stage.getScene().getStylesheets().add("UI/CSS/theme.css");
        stage.show();
    }
}
