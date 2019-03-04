package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SpashScreen {

    private static int targetCalories;

    public static int welcome() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Food Diary");

        Label welcomeLabel = new Label("Welcome to Food Diary");
        welcomeLabel.setFont(new Font(20));

        Label message = new Label("Set Target Calories:");

        TextField calorieInput = new TextField();
        calorieInput.setPromptText("Enter a value for your target calories...");
        calorieInput.setMaxWidth(360);

        Button submitButton = new Button("Continue");
        submitButton.setId("splashContinue");
        submitButton.setOnAction(event -> {
            targetCalories = Integer.parseInt(calorieInput.getText());
            stage.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(welcomeLabel, message, calorieInput, submitButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 720, 480);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.showAndWait();

        return targetCalories;
    }

}
