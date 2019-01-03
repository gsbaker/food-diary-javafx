package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {

    public TextField targetCaloriesInput;
    FoodDiary foodDiary = new FoodDiary();

    public void editTargetCalories() {

        System.out.println("Old target calories: " + foodDiary.getTargetCalories());

        int newTargetCalories = Integer.parseInt(targetCaloriesInput.getText());
        foodDiary.setTargetCalories(newTargetCalories);

        System.out.println("New target calories: " + foodDiary.getTargetCalories());
    }

}
