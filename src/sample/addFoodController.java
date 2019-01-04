package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class addFoodController {

    @FXML private ChoiceBox<String> mealtimeChoiceBox;
    public ObservableList<String> mealtimeChoices;


    // This method runs when the view is loaded -> so, initialize
    @FXML
    public void initialize() {
        ObservableList<String> mealtimeChoices = FXCollections.observableArrayList();
        mealtimeChoices.addAll("Breakfast", "Lunch", "Dinner", "Snacks");

        mealtimeChoiceBox.setItems(mealtimeChoices);
        mealtimeChoiceBox.setValue("Breakfast");
    }

}
