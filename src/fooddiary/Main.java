package fooddiary;

import fooddiary.controllers.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/view.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Food Diary");

        Scene scene = new Scene(root, 720, 480);
        scene.getStylesheets().add("fooddiary/css/style.css");

        primaryStage.setScene(scene);
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
