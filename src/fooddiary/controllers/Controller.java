package fooddiary.controllers;

import fooddiary.Food;
import fooddiary.FoodDiary;
import fooddiary.dao.FoodDiaryDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private FoodDiary foodDiary;
    private FoodDiaryDAO foodDiaryDAO;
    @FXML private TableView<Food> diaryTable;
    @FXML private TableColumn<Food, String> diaryNameColumn;
    @FXML private TableColumn<Food, Integer> diaryCaloriesColumn;
    @FXML private TableView<Food> addTable;
    @FXML private TableColumn<Food, String> addNameColumn;
    @FXML private TableColumn<Food, Integer> addCaloriesColumn;
    @FXML private TextField nameInput;
    @FXML private TextField caloriesInput;
    @FXML private Label messageLabel;
    @FXML private TextField searchField;
    @FXML private Label totalCaloriesLabel;
    @FXML private Label targetCaloriesLabel;
    @FXML private Label remainingCaloriesLabel;
    @FXML private ProgressBar progressBar;
    @FXML private LineChart<Number, Number> lineChart;
    @FXML private NumberAxis xAxis;
    @FXML private TextField targetCaloriesInput;
    @FXML private Label targetCaloriesMessage;
    private final int FAVOURITE_MIN;


    public Controller() {
        FAVOURITE_MIN = 2;
        try {
            foodDiaryDAO = new FoodDiaryDAO();
        } catch (ClassNotFoundException| SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        diaryTable.setPlaceholder(new Label("To add foods, go to Add Food"));
        diaryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        diaryNameColumn.setSortable(false);
        diaryCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        diaryCaloriesColumn.setSortable(false);

        // Reading in a saved food diary
        foodDiary = readFoodDiary();
        if (foodDiary != null) {
            LocalDate currentDate = LocalDate.now();
            if (foodDiary.getEstimatedDate().compareTo(currentDate) == 0) {
                if (foodDiary.getFoods() != null) {
                    diaryTable.setItems(FXCollections.observableList(foodDiary.getFoods()));
                }
            }
            else {
                FoodDiary savedFoodDiary = foodDiary;
                foodDiary = new FoodDiary();
                foodDiary.setEstimatedDate(currentDate);
                foodDiary.setTargetCalories(savedFoodDiary.getTargetCalories());
                if (savedFoodDiary.getSavedCalories() == null) {
                    foodDiary.setSavedCalories(new ArrayList<>());
                }
                else {
                    foodDiary.setSavedCalories(savedFoodDiary.getSavedCalories());
                    foodDiary.saveCalories(savedFoodDiary.getTotalCalories());
                }

                if (savedFoodDiary.getFavourites() == null) {
                    foodDiary.setFavourites(new ArrayList<>());
                }
                else {
                    foodDiary.setFavourites(savedFoodDiary.getFavourites());
                }

            }
        }
        // Create a completely new Food Diary
        else {
            // TODO: call createWelcomeScreen here once finished
            foodDiary = new FoodDiary();
            foodDiary.setFoods(new ArrayList<>());
            foodDiary.setFavourites(new ArrayList<>());
            foodDiary.setTargetCalories(2000);
            foodDiary.setEstimatedDate(LocalDate.now());
            foodDiary.setSavedCalories(new ArrayList<>());
        }

        foodDiary.setChangesMade(false);


        addNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addNameColumn.setSortable(false);
        addCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        addCaloriesColumn.setSortable(false);


        try {
            if (foodDiary.getFavourites().size() > 0) {
                addTable.setItems(FXCollections.observableList(foodDiary.getFavourites()));
            } else {
                addTable.setItems(FXCollections.observableList(FXCollections.observableList(foodDiaryDAO.getAllFoods())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // TODO: display favourite/recent foods

        searchField.textProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    try {
                        if (newValue.trim().length() > 0) {
                            addTable.setItems(FXCollections.observableArrayList(foodDiaryDAO.search(newValue.trim())));
                            addTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                })
        );


        Font font = new Font(20);
        totalCaloriesLabel.setFont(font);
        targetCaloriesLabel.setFont(font);
        remainingCaloriesLabel.setFont(font);
        updateProgress();
        progressBar.progressProperty().bind(foodDiary.barUpdaterProperty());
        lineChart.setTitle("Last " + (foodDiary.getSavedCalories().size() - 1) + " days of tracking");
        XYChart.Series<Number, Number> targetSeries = new XYChart.Series<>();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        targetSeries.setName("Target Calories");
        series.setName("Your Progress");
        for (int i = 0; i <= foodDiary.getSavedCalories().size() - 1; i ++) {
            targetSeries.getData().add(new XYChart.Data<>(i, foodDiary.getTargetCalories()));
            series.getData().add(new XYChart.Data<>(i, foodDiary.getSavedCalories().get(i)));
        }
        lineChart.getData().add(targetSeries);
        lineChart.getData().add(series);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(foodDiary.getSavedCalories().size());
        xAxis.setTickUnit(1);

    }

    private void save() {
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
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("FoodDiarySaveFile.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (FoodDiary) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void removeFoods() {
        ArrayList<Food> selected = new ArrayList<>(diaryTable.getSelectionModel().getSelectedItems());
        selected.forEach(row -> {
            diaryTable.getItems().remove(row);
            foodDiary.getFoods().remove(row);
            foodDiary.decreaseCalories(row.getCalories());
        });
        updateProgress();
        foodDiary.setChangesMade(true);
    }


    public void addFoods() {
        ArrayList<Food> selected = new ArrayList<>(addTable.getSelectionModel().getSelectedItems());
        foodDiary.addFoods(selected);
        diaryTable.setItems(FXCollections.observableList(foodDiary.getFoods()));
        ArrayList<Food> newItems = new ArrayList<>();
        for (Food f : selected) {
            f.incrementFrequency();
            if (f.getFrequency() >= FAVOURITE_MIN) {
                if (foodDiary.getFavourites().size() > 0) {
                    for (Food favourite : foodDiary.getFavourites()) {
                        if (f.getId() != favourite.getId()) {
                            newItems.add(f);
                        }
                    }
                }
                else {
                    foodDiary.addFavourite(f);
                }
            }
            foodDiary.increaseCalories(f.getCalories());
        }
        foodDiary.addFavourites(newItems);
        updateProgress();
        foodDiary.setChangesMade(true);
    }

    public void addUserFood() {
        try {
            int id = foodDiaryDAO.generateId();
            String name = nameInput.getText();
            String caloriesString = caloriesInput.getText();
            if (isAlpha(name) && isNumber(caloriesString) && name.length() > 0) {
                int calories = Integer.parseInt(caloriesString);
                Food food = new Food(id, name, calories);
                foodDiaryDAO.insertFood(food);
                addTable.setItems(FXCollections.observableList(foodDiaryDAO.getAllFoods()));
                messageLabel.getStyleClass().add("successLabel");
                messageLabel.getStyleClass().remove("errorLabel");
                messageLabel.setText(name + " was successfully added");
            }
            else {
                messageLabel.getStyleClass().add("errorLabel");
                messageLabel.getStyleClass().remove("successLabel");
                messageLabel.setText("Please enter in a valid format");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isAlpha(String s) {
        char[] chars = s.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }

        return true;
    }

    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private void updateProgress() {
        totalCaloriesLabel.setText("Total Calories: " + foodDiary.getTotalCalories());
        targetCaloriesLabel.setText("Target Calories: " + foodDiary.getTargetCalories());
        remainingCaloriesLabel.setText("Remaining Calories: " + (foodDiary.getTargetCalories() - foodDiary.getTotalCalories()));
        foodDiary.setBarUpdater((foodDiary.getTotalCalories() / (double)foodDiary.getTargetCalories()));
    }

    public void setTargetCalories() {
        String targetCaloriesString = targetCaloriesInput.getText();
        if (isNumber(targetCaloriesString)) { // check that it's a number not letters
            int newTargetCalories = Integer.parseInt(targetCaloriesString);
            if (newTargetCalories >= 1800 && newTargetCalories <= 4000) { // check within range
                // set as new target calories etc.
                foodDiary.setTargetCalories(newTargetCalories);
                targetCaloriesLabel.setText("Target Calories: " + foodDiary.getTargetCalories());
                remainingCaloriesLabel.setText("Remaining Calories: " + (foodDiary.getTargetCalories() - foodDiary.getTotalCalories()));
                foodDiary.setBarUpdater((foodDiary.getTotalCalories() / (double)foodDiary.getTargetCalories()));
                targetCaloriesMessage.getStyleClass().add("successLabel");
                targetCaloriesMessage.getStyleClass().remove("errorLabel");
                targetCaloriesMessage.setText("Target Calories changed to " + targetCaloriesString + " calories");
                foodDiary.setChangesMade(true);
            }
            else { // not within range
                targetCaloriesMessage.getStyleClass().add("errorLabel");
                targetCaloriesMessage.getStyleClass().remove("successLabel");
                targetCaloriesMessage.setText("Please enter a number between 1800 and 4000");
            }
        }
        else { // not a number, they entered non numeric characters
            targetCaloriesMessage.getStyleClass().add("errorLabel");
            targetCaloriesMessage.getStyleClass().remove("successLabel");
            targetCaloriesMessage.setText("Please enter a number between 1800 and 4000");
        }
    }

    public void handleClose() {
        if (foodDiary.isChangesMade()) {
            save();
        }
    }
}
