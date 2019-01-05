package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private FoodDataAccessor foodDataAccessor;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("foodDiaryView.fxml"));
        primaryStage.setTitle("Food Diary");
        primaryStage.setScene(new Scene(root, 720, 480));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (foodDataAccessor != null) {
            foodDataAccessor.shutdown();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
