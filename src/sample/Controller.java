package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private FoodDiary foodDiary;

    // Food Diary
    @FXML private TableView<Food> breakfastTableView;
    @FXML private TableView<Food> lunchTableView;
    @FXML private TableView<Food> dinnerTableView;
    @FXML private TableView<Food> snackTableView;
    @FXML private TableColumn<Food, Integer> breakfastIdColumn;
    @FXML private TableColumn<Food, String> breakfastNameColumn;
    @FXML private TableColumn<Food, Integer> breakfastCaloriesColumn;
    @FXML private TableColumn<Food, Integer> lunchIdColumn;
    @FXML private TableColumn<Food, String> lunchNameColumn;
    @FXML private TableColumn<Food, Integer> lunchCaloriesColumn;
    @FXML private TableColumn<Food, Integer> dinnerIdColumn;
    @FXML private TableColumn<Food, String> dinnerNameColumn;
    @FXML private TableColumn<Food, Integer> dinnerCaloriesColumn;
    @FXML private TableColumn<Food, Integer> snackIdColumn;
    @FXML private TableColumn<Food, String> snackNameColumn;
    @FXML private TableColumn<Food, Integer> snackCaloriesColumn;


    // Add Foods
    @FXML private TableView<Food> addFoodTableView;
    @FXML private TableColumn<Food, Integer> addFoodIdColumn;
    @FXML private TableColumn<Food, String> addFoodNameColumn;
    @FXML private TableColumn<Food, Integer> addFoodCaloriesColumn;
    @FXML private ChoiceBox<String> mealtimeChoiceBox;

    // Settings
    @FXML private TextField targetCaloriesInput;

    // Constructor
    public Controller() {
        this.foodDiary = new FoodDiary();
    }

    // Initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // --- Food Diary Tab ---
        breakfastIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        breakfastNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        breakfastCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        lunchIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        lunchNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lunchCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        dinnerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dinnerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dinnerCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        snackIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        snackNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        snackCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));

        // --- Add Foods Tab ---
        // Populating the Add Food Table
        addFoodIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addFoodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addFoodCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        try {
            FoodDataAccessor foodDataAccessor = new FoodDataAccessor();
            ObservableList<Food> foods = foodDataAccessor.getFoodObservableList(); // Getting the foods from the database as Food objects
            addFoodTableView.setItems(foods);
            addFoodTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Choosing a meal time
        ObservableList<String> mealtimeChoices = FXCollections.observableArrayList();
        mealtimeChoices.addAll("Breakfast", "Lunch", "Dinner", "Snacks");
        mealtimeChoiceBox.setItems(mealtimeChoices);
        mealtimeChoiceBox.setValue("Breakfast");
    }


    // --- Methods relating to Add Food ---
    public void addFoodsEventHandler() {
        ObservableList<Food> foodsSelected = FXCollections.observableArrayList();
        foodsSelected.addAll(addFoodTableView.getSelectionModel().getSelectedItems());
        String mealtime = mealtimeChoiceBox.getValue();
        addFoods(mealtime, foodsSelected);
    }


    private void addFoods(String mealtime, ObservableList<Food> foods) {
        switch (mealtime) {
            case "Breakfast":
                breakfastTableView.setItems(foods);
                break;
            case "Lunch":
                lunchTableView.setItems(foods);
                break;
            case "Dinner":
                dinnerTableView.setItems(foods);
                break;
            case "Snacks":
                snackTableView.setItems(foods);
                break;
        }
    }


    // --- Methods relating to settings ---
    public void editTargetCalories() {

        System.out.println("Old target calories: " + foodDiary.getTargetCalories());

        int newTargetCalories = Integer.parseInt(targetCaloriesInput.getText());
        foodDiary.setTargetCalories(newTargetCalories);

        System.out.println("New target calories: " + foodDiary.getTargetCalories());
    }


}
