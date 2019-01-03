package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    FoodDataAccessor foodDataAccessor;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String databaseUrl = "jdbc:mysql://localhost:3306/FoodDiaryDatabase?useSSL=false";
        String user = "myuser";
        String password = "password";

        foodDataAccessor = new FoodDataAccessor(driverClassName, databaseUrl, user, password);
        foodDataAccessor.getFoodList();
        System.out.println(foodDataAccessor.getFoodNames());

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Food Diary");
        primaryStage.setScene(new Scene(root, 300, 275));
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
