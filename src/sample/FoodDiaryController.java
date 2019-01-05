package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FoodDiaryController {

    FoodDiary foodDiary = new FoodDiary();
    @FXML private TextField targetCaloriesInput;
    @FXML private TableView<Food> bTable;
    @FXML private TableView<Food> lTable;
    @FXML private TableView<Food> dTable;
    @FXML private TableView<Food> sTable;
    @FXML private TableColumn<Food, Integer> idColumn;
    @FXML private TableColumn<Food, String> nameColumn;
    @FXML private TableColumn<Food, Integer> caloriesColumn;

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
    }

    public void editTargetCalories() {

        System.out.println("Old target calories: " + foodDiary.getTargetCalories());

        int newTargetCalories = Integer.parseInt(targetCaloriesInput.getText());
        foodDiary.setTargetCalories(newTargetCalories);

        System.out.println("New target calories: " + foodDiary.getTargetCalories());
    }

    public void addFoodEventHandler() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("addFoodView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Add Food");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void addFoods(String mealtime, ObservableList<Food> foods) {
        switch (mealtime) {
            case "Breakfast":
                bTable.setItems(foods);
                break;
            case "Lunch":
                lTable.setItems(foods);
                break;
            case "Dinner":
                dTable.setItems(foods);
                break;
            case "Snacks":
                sTable.setItems(foods);
                break;
        }
    }
}
