import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Window extends Application {

    private final int WIDTH = 700;
    private final int HEIGHT = 500;

    private double rucksackCapacity;

    private Group pane;

    private TextField bagWeightTextField;
    private TextField objNameTextField;
    private TextField objValueTextField;
    private TextField objWeightTextField;

    private Button addItemButton;
    private Button saveWeightButton;
    private Button startButton;

    private ArrayList<Item> itemsList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {

        this.pane = new Group();

        bagWeightTextField = new TextField();
        bagWeightTextField.setPrefWidth(200);
        bagWeightTextField.setTranslateX(400);
        bagWeightTextField.setTranslateY(150);
        bagWeightTextField.setPromptText("Bag max weight...");

        objNameTextField = new TextField();
        objNameTextField.setPrefWidth(200);
        objNameTextField.setTranslateX(100);
        objNameTextField.setTranslateY(150);
        objNameTextField.setPromptText("Name...");

        objValueTextField = new TextField();
        objValueTextField.setPrefWidth(200);
        objValueTextField.setTranslateX(objNameTextField.getTranslateX());
        objValueTextField.setTranslateY(objNameTextField.getTranslateY()+50);
        objValueTextField.setPromptText("Value...");

        objWeightTextField = new TextField();
        objWeightTextField.setPrefWidth(200);
        objWeightTextField.setTranslateX(objNameTextField.getTranslateX());
        objWeightTextField.setTranslateY(objNameTextField.getTranslateY()+100);
        objWeightTextField.setPromptText("Weight...");

        pane.getChildren().addAll(bagWeightTextField, objNameTextField, objValueTextField, objWeightTextField);

        addItemButton = new Button("Add item");
        addItemButton.setTranslateX(objNameTextField.getTranslateX()+65);
        addItemButton.setTranslateY(objNameTextField.getTranslateY()+150);

        saveWeightButton = new Button("Save");
        saveWeightButton.setTranslateX(bagWeightTextField.getTranslateX()+70);
        saveWeightButton.setTranslateY(bagWeightTextField.getTranslateY()+50);

        startButton = new Button("Start");
        startButton.setTranslateX(objNameTextField.getTranslateX()+230);
        startButton.setTranslateY(objNameTextField.getTranslateY()+250);

        pane.getChildren().addAll(addItemButton, saveWeightButton, startButton);

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Knapsack Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
        pane.requestFocus();

        scene.setOnKeyPressed(t -> {
            KeyCode key = t.getCode();
            switch (key) {
                case ESCAPE:
                    System.exit(0);
                    break;
            }
        });

        addItemButton.setOnAction(t -> {
            itemsList.add(new Item(objNameTextField.getText(), Integer.parseInt(objValueTextField.getText()), Double.parseDouble(objWeightTextField.getText())));
            System.out.println("Item " + objNameTextField.getText() + " with a value of " + objValueTextField.getText() + " and a weight of " + objWeightTextField.getText() + " added successfully");
            objNameTextField.setText("");
            objValueTextField.setText("");
            objWeightTextField.setText("");
        });

        startButton.setOnAction(t -> {
            Item[] items = itemsList.toArray(new Item[0]);
            Knapsack knapsack = new Knapsack(200, 200, 0.7, 0.01, rucksackCapacity, items);
            knapsack.start();
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            VBox dialogVbox = new VBox(20);
            Text text = new Text("\n \t You should bring in your " + knapsack.getBag().getType() + ":\n\n" + knapsack.getBestItemInList());
            text.setFont(Font.font("Arial", 13));
            Button restartButton = new Button("Restart");
            restartButton.setTranslateX(100);
            restartButton.setOnAction(event -> {
                dialog.close();
                itemsList = new ArrayList<>();
                objNameTextField.setText("");
                objValueTextField.setText("");
                objWeightTextField.setText("");
                bagWeightTextField.setText("");
            });
            dialogVbox.getChildren().addAll(text, restartButton);
            Scene dialogScene = new Scene(dialogVbox, 260, 400);
            dialog.setOnCloseRequest(event -> {
                System.exit(0);
            });
            dialogScene.setOnKeyPressed(event -> {
                KeyCode keyCode = event.getCode();
                if (keyCode == KeyCode.ESCAPE) {
                    System.exit(0);
                }
            });
            dialog.setScene(dialogScene);
            dialog.setResizable(false);
            dialog.show();
        });

        saveWeightButton.setOnAction(t -> {
            rucksackCapacity = Double.parseDouble(bagWeightTextField.getText());
            System.out.println();
            System.out.println("Rucksack capacity changed to " + rucksackCapacity);
            System.out.println();
        });
    }
}
