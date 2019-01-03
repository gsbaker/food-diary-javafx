package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private FoodDataAccessor foodDataAccessor;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final String driverClassName = "com.mysql.cj.jdbc.Driver";
        final String databaseUrl = "jdbc:mysql://localhost:3306/FoodDiaryDatabase?useSSL=false";
        final String user = "myuser";
        final String password = "password";

        foodDataAccessor = new FoodDataAccessor(driverClassName, databaseUrl, user, password);
        foodDataAccessor.getFoodList();
        System.out.println(foodDataAccessor.getFoodNames());
        System.out.println(foodDataAccessor.getCalories("Apple"));

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
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
