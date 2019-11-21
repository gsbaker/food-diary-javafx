package fooddiary;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class FoodDiary implements Serializable {

    private ArrayList<Food> foods;
    private ArrayList<Food> favourites;
    private int targetCalories;
    private int totalCalories;
    private transient boolean changesMade;
    private LocalDate estimatedDate;
    private transient DoubleProperty barUpdater;
    private ArrayList<Integer> savedCalories;


    public FoodDiary() {
        this.targetCalories = 2000;
        this.foods = new ArrayList<>();
        this.favourites = new ArrayList<>();
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void addFoods(ArrayList<Food> foods) {
        this.foods.addAll(foods);
    }

    public void addFood(Food e) {
        this.foods.add(e);
    }

    public ArrayList<Food> getFavourites() {
        return favourites;
    }

    public void addFavourites(ArrayList<Food> foods) {
        this.favourites.addAll(foods);
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public void setFavourites(ArrayList<Food> favourites) {
        this.favourites = favourites;
    }

    public void addFavourite(Food e) {
        this.favourites.add(e);
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void increaseCalories(int amount) {
        this.totalCalories += amount;
    }

    public void decreaseCalories(int amount) {
        this.totalCalories -= amount;
    }

    public int getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(int targetCalories) {
        this.targetCalories = targetCalories;
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

    public void saveCalories(int amount) {
        this.savedCalories.add(amount);
    }

}
