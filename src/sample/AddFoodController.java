package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class AddFoodController {

    @FXML private ChoiceBox<String> mealtimeChoiceBox;
    @FXML private TableView<Food> tableView;
    @FXML private TableColumn<Food, Integer> idColumn;
    @FXML private TableColumn<Food, String> nameColumn;
    @FXML private TableColumn<Food, Integer> caloriesColumn;
    public FoodDiaryController foodDiaryController;


    // This method runs when the view is loaded -> so, initialize
    @FXML
    public void initialize() {

        // Initialize FoodDiaryController
        foodDiaryController = new FoodDiaryController();

        // Populating the table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        try {
            FoodDataAccessor foodDataAccessor = new FoodDataAccessor();
            ObservableList<Food> foods = foodDataAccessor.getFoodObservableList(); // Getting the foods from the database as Food objects
            tableView.setItems(foods);
            tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

    public void handleButtonClick() {
        ObservableList<Food> foodsSelected = FXCollections.observableArrayList();
        foodsSelected.addAll(tableView.getSelectionModel().getSelectedItems());
        String mealtime = mealtimeChoiceBox.getValue();
        foodDiaryController.addFoods(mealtime, foodsSelected);
    }



}
