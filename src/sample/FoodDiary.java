package sample;

import javafx.collections.ObservableList;

import java.util.List;

public class FoodDiary {

    private int targetCalories;
    private ObservableList<Food> breakfastFoods;
    private ObservableList<Food> lunchFoods;
    private ObservableList<Food> dinnerFoods;
    private ObservableList<Food> snackFoods;

    public ObservableList<Food> getBreakfastFoods() {
        return breakfastFoods;
    }

    public void setBreakfastFoods(ObservableList<Food> breakfastFoods) {
        this.breakfastFoods = breakfastFoods;
    }

    public ObservableList<Food> getLunchFoods() {
        return lunchFoods;
    }

    public void setLunchFoods(ObservableList<Food> lunchFoods) {
        this.lunchFoods = lunchFoods;
    }

    public ObservableList<Food> getDinnerFoods() {
        return dinnerFoods;
    }

    public void setDinnerFoods(ObservableList<Food> dinnerFoods) {
        this.dinnerFoods = dinnerFoods;
    }

    public ObservableList<Food> getSnackFoods() {
        return snackFoods;
    }

    public void setSnackFoods(ObservableList<Food> snackFoods) {
        this.snackFoods = snackFoods;
    }

    public int getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(int targetCalories) {
        this.targetCalories = targetCalories;
    }


}
