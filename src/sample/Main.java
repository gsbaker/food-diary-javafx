package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Food Diary");
        primaryStage.setScene(new Scene(root, 720, 480));
        primaryStage.getIcons().add(new Image("file:Food Diary Icon.jpeg"));
        primaryStage.show();
        controller = loader.getController();
        primaryStage.setOnCloseRequest(event -> {
            controller.handleClose();
            Platform.exit();
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
