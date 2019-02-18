package sample;

import java.io.Serializable;

public class Food implements Serializable {

    private int id;
    private String name;
    private int calories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Food(int id, String name, int calories) {
        this.id = id;
        this.name = name;
        this.calories = calories;
    }



}
