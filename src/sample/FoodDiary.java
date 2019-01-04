package sample;

import java.util.List;

public class FoodDiary {

    private int targetCalories;
    private List<Food> breakfastFoods;
    private List<Food> lunchFoods;
    private List<Food> dinnerFoods;
    private List<Food> snackFoods;


    public int getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(int targetCalories) {
        this.targetCalories = targetCalories;
    }

    public List<Food> getBreakfastFoods() {
        return breakfastFoods;
    }

    public void setBreakfastFoods(List<Food> breakfastFoods) {
        this.breakfastFoods = breakfastFoods;
    }

    public List<Food> getLunchFoods() {
        return lunchFoods;
    }

    public void setLunchFoods(List<Food> lunchFoods) {
        this.lunchFoods = lunchFoods;
    }

    public List<Food> getDinnerFoods() {
        return dinnerFoods;
    }

    public void setDinnerFoods(List<Food> dinnerFoods) {
        this.dinnerFoods = dinnerFoods;
    }

    public List<Food> getSnackFoods() {
        return snackFoods;
    }

    public void setSnackFoods(List<Food> snackFoods) {
        this.snackFoods = snackFoods;
    }
}
