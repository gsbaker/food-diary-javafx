package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FoodDiary implements Serializable {

    private int targetCalories;
    private int totalCalories;
    private transient ObservableList<Food> breakfastFoodsObservableList;
    private transient ObservableList<Food> lunchFoodsObservableList;
    private transient ObservableList<Food> dinnerFoodsObservableList;
    private transient ObservableList<Food> snackFoodsObservableList;
    private ArrayList<Food> breakfastFoodsList;
    private ArrayList<Food> lunchFoodsList;
    private ArrayList<Food> dinnerFoodsList;
    private ArrayList<Food> SnackFoodsList;
    private transient boolean changesMade;
    private LocalDate estimatedDate;
    private transient DoubleProperty barUpdater;
    private ArrayList<Integer> savedCalories;


    public FoodDiary() {
        this.targetCalories = 2000;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void addToTotalCalories(int amount) {
        this.totalCalories += amount;
    }

    public ObservableList<Food> getBreakfastFoodsObservableList() {
        return breakfastFoodsObservableList;
    }

    public void setBreakfastFoodsObservableList(ObservableList<Food> breakfastFoodsObservableList) {
        this.breakfastFoodsObservableList = breakfastFoodsObservableList;
    }

    public ObservableList<Food> getLunchFoodsObservableList() {
        return lunchFoodsObservableList;
    }

    public void setLunchFoodsObservableList(ObservableList<Food> lunchFoodsObservableList) {
        this.lunchFoodsObservableList = lunchFoodsObservableList;
    }

    public ObservableList<Food> getDinnerFoodsObservableList() {
        return dinnerFoodsObservableList;
    }

    public void setDinnerFoodsObservableList(ObservableList<Food> dinnerFoodsObservableList) {
        this.dinnerFoodsObservableList = dinnerFoodsObservableList;
    }

    public ObservableList<Food> getSnackFoodsObservableList() {
        return snackFoodsObservableList;
    }

    public void setSnackFoodsObservableList(ObservableList<Food> snackFoodsObservableList) {
        this.snackFoodsObservableList = snackFoodsObservableList;
    }

    public int getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(int targetCalories) {
        this.targetCalories = targetCalories;
    }

    public ArrayList<Food> getBreakfastFoodsList() {
        return breakfastFoodsList;
    }

    public void setBreakfastFoodsList(ArrayList<Food> breakfastFoodsList) {
        this.breakfastFoodsList = breakfastFoodsList;
    }

    public ArrayList<Food> getLunchFoodsList() {
        return lunchFoodsList;
    }

    public void setLunchFoodsList(ArrayList<Food> lunchFoodsList) {
        this.lunchFoodsList = lunchFoodsList;
    }

    public ArrayList<Food> getDinnerFoodsList() {
        return dinnerFoodsList;
    }

    public void setDinnerFoodsList(ArrayList<Food> dinnerFoodsList) {
        this.dinnerFoodsList = dinnerFoodsList;
    }

    public ArrayList<Food> getSnackFoodsList() {
        return SnackFoodsList;
    }

    public void setSnackFoodsList(ArrayList<Food> snackFoodsList) {
        SnackFoodsList = snackFoodsList;
    }

    public boolean isChangesMade() {
        return changesMade;
    }

    public void setChangesMade(boolean changesMade) {
        this.changesMade = changesMade;
    }

    public LocalDate getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(LocalDate estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public double getBarUpdater() {
        if (barUpdater != null) {
            return barUpdater.get();
        }
        return 0;
    }

    public void setBarUpdater(double barUpdater) {
        this.barUpdaterProperty().set(barUpdater);
    }

    public DoubleProperty barUpdaterProperty() {
        if (barUpdater == null) {
            barUpdater = new SimpleDoubleProperty(0);
        }
        return barUpdater;
    }

    public ArrayList<Integer> getSavedCalories() {
        return savedCalories;
    }

    public void setSavedCalories(ArrayList<Integer> savedCalories) {
        this.savedCalories = savedCalories;
    }

    public void addToSavedCalories(int amount) {
        this.savedCalories.add(amount);
    }

}
