package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private FoodDiary foodDiary;

    // Food Diary
    @FXML private TableView<Food> breakfastTableView;
    @FXML private TableView<Food> lunchTableView;
    @FXML private TableView<Food> dinnerTableView;
    @FXML private TableView<Food> snackTableView;
    @FXML private TableColumn<Food, String> breakfastNameColumn;
    @FXML private TableColumn<Food, Integer> breakfastCaloriesColumn;
    @FXML private TableColumn<Food, String> lunchNameColumn;
    @FXML private TableColumn<Food, Integer> lunchCaloriesColumn;
    @FXML private TableColumn<Food, String> dinnerNameColumn;
    @FXML private TableColumn<Food, Integer> dinnerCaloriesColumn;
    @FXML private TableColumn<Food, String> snackNameColumn;
    @FXML private TableColumn<Food, Integer> snackCaloriesColumn;

    // Add Foods
    @FXML private TableView<Food> addFoodTableView;
    @FXML private TableColumn<Food, String> addFoodNameColumn;
    @FXML private TableColumn<Food, Integer> addFoodCaloriesColumn;
    @FXML private ChoiceBox<String> mealtimeChoiceBox;

    // Progress
    @FXML private Label totalCaloriesLabel;
    @FXML private Label targetCaloriesLabel;
    @FXML private Label remainingCaloriesLabel;
    @FXML private ProgressBar progressBar;


    // Settings
    @FXML private TextField targetCaloriesInput;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // --- Food Diary Tab ---
        breakfastTableView.setPlaceholder(new Label("To add foods, go to Add Food"));
        lunchTableView.setPlaceholder(new Label("To add foods, go to Add Food"));
        dinnerTableView.setPlaceholder(new Label("To add foods, go to Add Food"));
        snackTableView.setPlaceholder(new Label("To add foods, go to Add Food"));

        breakfastNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        breakfastCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        lunchNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lunchCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        dinnerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dinnerCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        snackNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        snackCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));

        // Reading in a saved food diary
        if (readFoodDiary() != null) {
            foodDiary = readFoodDiary();
            if (foodDiary.getBreakfastFoodsList() != null) {
                foodDiary.setBreakfastFoodsObservableList(FXCollections.observableList(foodDiary.getBreakfastFoodsList()));
                breakfastTableView.setItems(foodDiary.getBreakfastFoodsObservableList());
            }
            if (foodDiary.getLunchFoodsList() != null) {
                foodDiary.setLunchFoodsObservableList(FXCollections.observableList(foodDiary.getLunchFoodsList()));
                lunchTableView.setItems(foodDiary.getLunchFoodsObservableList());
            }
            if (foodDiary.getDinnerFoodsList() != null) {
                foodDiary.setDinnerFoodsObservableList(FXCollections.observableList(foodDiary.getDinnerFoodsList()));
                dinnerTableView.setItems(foodDiary.getDinnerFoodsObservableList());
            }
            if (foodDiary.getSnackFoodsList() != null) {
                foodDiary.setSnackFoodsObservableList(FXCollections.observableList(foodDiary.getSnackFoodsList()));
                snackTableView.setItems(foodDiary.getSnackFoodsObservableList());
            }
        }
        else {
            // TODO: put a welcome screen where the user can set target calories etc.
            this.foodDiary = new FoodDiary();
        }

        foodDiary.setChangesMade(false);

        // --- Add Foods Tab ---
        // Populating the Add Food Table

        addFoodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addFoodCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        try {
            FoodDataAccessor foodDataAccessor = new FoodDataAccessor();
            ObservableList<Food> foods = foodDataAccessor.getFoodObservableList(); // Getting the foods from the database as Food objects
            addFoodTableView.setItems(foods);
            addFoodTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Choosing a meal time
        ObservableList<String> mealtimeChoices = FXCollections.observableArrayList();
        mealtimeChoices.addAll("Breakfast", "Lunch", "Dinner", "Snacks");
        mealtimeChoiceBox.setItems(mealtimeChoices);
        mealtimeChoiceBox.setValue("Breakfast");

        // --- Progress Tab ---
        Font font = new Font(20);
        totalCaloriesLabel.setFont(font);
        targetCaloriesLabel.setFont(font);
        remainingCaloriesLabel.setFont(font);
        totalCaloriesLabel.setText("Total Calories: " + foodDiary.getTotalCalories());
        targetCaloriesLabel.setText("Target Calories: " + foodDiary.getTargetCalories());
        remainingCaloriesLabel.setText("Remaining Calories: " + (foodDiary.getTargetCalories() - foodDiary.getTotalCalories()));
        double progress = foodDiary.getTotalCalories() / (double)foodDiary.getTargetCalories();
//        System.out.println(foodDiary.getTotalCalories());
//        System.out.println(foodDiary.getTargetCalories());
//        System.out.println(progress);
        progressBar.setProgress(progress);
    }


    // --- Methods relating to Food Diary ---

    public void saveEventHandler() {
        if (foodDiary.getBreakfastFoodsObservableList() != null) {
            foodDiary.setBreakfastFoodsList(new ArrayList<>(foodDiary.getBreakfastFoodsObservableList()));
        }
        if (foodDiary.getLunchFoodsObservableList() != null) {
            foodDiary.setLunchFoodsList(new ArrayList<>(foodDiary.getLunchFoodsObservableList()));
        }
        if (foodDiary.getDinnerFoodsObservableList() != null) {
            foodDiary.setDinnerFoodsList(new ArrayList<>(foodDiary.getDinnerFoodsObservableList()));
        }
        if (foodDiary.getSnackFoodsObservableList() != null) {
            foodDiary.setSnackFoodsList(new ArrayList<>(foodDiary.getSnackFoodsObservableList()));
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("FoodDiarySaveFile.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(foodDiary);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FoodDiary readFoodDiary() {
        try {
            FileInputStream fileInputStream = new FileInputStream("FoodDiarySaveFile.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (FoodDiary) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --- Methods relating to Add Food ---
    public void addFoodsEventHandler() {
        ObservableList<Food> foodsSelected = FXCollections.observableArrayList();
        foodsSelected.addAll(addFoodTableView.getSelectionModel().getSelectedItems());
        String mealtime = mealtimeChoiceBox.getValue();
        addFoods(mealtime, foodsSelected);
    }

    private void addFoods(String mealtime, ObservableList<Food> newFoods) {
        ObservableList<Food> updatedFoodList = FXCollections.observableArrayList();
        ObservableList<Food> currentFoods;
        switch (mealtime) {
            case "Breakfast":
                currentFoods = foodDiary.getBreakfastFoodsObservableList();
                addFoodsHelper(newFoods, updatedFoodList, currentFoods);
                foodDiary.setBreakfastFoodsObservableList(updatedFoodList);
                breakfastTableView.setItems(foodDiary.getBreakfastFoodsObservableList());
                break;
            case "Lunch":
                currentFoods = foodDiary.getLunchFoodsObservableList();
                addFoodsHelper(newFoods, updatedFoodList, currentFoods);
                foodDiary.setLunchFoodsObservableList(updatedFoodList);
                lunchTableView.setItems(foodDiary.getLunchFoodsObservableList());
                break;
            case "Dinner":
                currentFoods = foodDiary.getDinnerFoodsObservableList();
                addFoodsHelper(newFoods, updatedFoodList, currentFoods);
                foodDiary.setDinnerFoodsObservableList(updatedFoodList);
                dinnerTableView.setItems(foodDiary.getDinnerFoodsObservableList());
                break;
            case "Snacks":
                currentFoods = foodDiary.getSnackFoodsObservableList();
                addFoodsHelper(newFoods, updatedFoodList, currentFoods);
                foodDiary.setSnackFoodsObservableList(updatedFoodList);
                snackTableView.setItems(foodDiary.getSnackFoodsObservableList());
                break;
        }
        foodDiary.setChangesMade(true);
    }

    // Creates the updated food Observable list
    private void addFoodsHelper(ObservableList<Food> newFoods, ObservableList<Food> foodsToAdd, ObservableList<Food> currentFoods) {
        if (currentFoods == null) {
            foodsToAdd.addAll(newFoods);
            for (Food food: foodsToAdd) {
                foodDiary.addToTotalCalories(food.getCalories());
            }
        }
        else {
            foodsToAdd.addAll(currentFoods);
            foodsToAdd.addAll(newFoods);
            for (Food food: newFoods) {
                foodDiary.addToTotalCalories(food.getCalories());
            }
        }
    }


    // --- Methods relating to progress ---


    // --- Methods relating to settings ---
    public void setTargetCaloriesEventHandler() {
        int newTargetCalories = Integer.parseInt(targetCaloriesInput.getText());
        foodDiary.setTargetCalories(newTargetCalories);
        foodDiary.setChangesMade(true);
    }


}
