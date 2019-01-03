package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {

    private FoodDiary foodDiary;
    @FXML private TextField setTargetCaloriesTextField;

    public void editTargetCalories() {
        foodDiary = new FoodDiary();
        int newTargetCalories = Integer.parseInt(setTargetCaloriesTextField.getText());
        foodDiary.setTargetCalories(newTargetCalories);
    }

}
