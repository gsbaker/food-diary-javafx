package fooddiary.dao;

import fooddiary.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDiaryDAO {

    private Connection connection;

    public FoodDiaryDAO() throws SQLException, ClassNotFoundException {
        final String driverClassName = "com.mysql.cj.jdbc.Driver";
        final String databaseUrl = "jdbc:mysql://localhost:3306/FoodDiary?useSSL=false";
        final String user = "myuser";
        final String password = "password";
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(databaseUrl, user, password);
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public ObservableList<Food> getFoodObservableList() throws SQLException {
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM food");
        ){
            ObservableList<Food> foodObservableList = FXCollections.observableArrayList();

            extractResultSet(resultSet, foodObservableList);
            return foodObservableList;
        }
    }

    public ArrayList<Food> getFoodArrayList() throws SQLException {
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM food");
        ){
            ArrayList<Food> foodArrayList = new ArrayList<>();
            extractResultSet(resultSet, foodArrayList);

            return foodArrayList;
        }
    }


    public List<String> getFoodNames() throws SQLException {
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM food");
        ){
            List<String> foodNames = new ArrayList<>();

            while (resultSet.next()) {
                foodNames.add(resultSet.getString("name"));
            }
            return foodNames;
        }
    }

    public int getCalories(String name) throws SQLException {
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM food");
        ){

            while (resultSet.next()) {
                if (resultSet.getString("name").equals(name)) {
                    return resultSet.getInt("calories");
                }
            }
        }
        return 0;
    }

    public void insertFood(Food food) throws SQLException {
        int id = food.getId();
        String name = food.getName();
        int calories = food.getCalories();
        Statement statement = connection.createStatement();
        String values = "(" + id + "," + "'" + name + "'" + "," + calories + ")";
        statement.executeUpdate("INSERT INTO food VALUES " + values);
    }

    public int generateId() throws SQLException {
        ArrayList<Food> currentFoods = getFoodArrayList();
        if (currentFoods.size() > 0) {
            return currentFoods.get(currentFoods.size() - 1).getId();
        }
        return 0;
    }

    public ObservableList<Food> search(String searchTerm) throws SQLException {

        String query = "SELECT * FROM food WHERE name LIKE '%" + searchTerm + "%'";

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ){
            ObservableList<Food> foodObservableList = FXCollections.observableArrayList();
            extractResultSet(resultSet, foodObservableList);
            return foodObservableList;
        }
    }

//    public ObservableList<Food> smartSearch(String searchTerm) throws SQLException {
//
//        final int ERROR_MARGIN = 0;
//        String searchString = ""; // string that is used in the query
//        int lettersMatched = 0;
//
//        for (char c : searchTerm.toCharArray()) {
//            String s = Character.toString(c);
//            searchString += s;
//
//            String query = "SELECT * FROM food WHERE name LIKE %'" + s+ "%'"; // find all words that contain searchString
//            try (
//                    Statement statement = connection.createStatement();
//                    ResultSet resultSet = statement.executeQuery(query);
//            ){
//                ObservableList<Food> foodObservableList = FXCollections.observableArrayList();
//                while (resultSet.next()) { // adds all of the matched foods tofoodObservableList
//                    int id = resultSet.getInt("id");
//                    String name = resultSet.getString("name");
//                    int calories = resultSet.getInt("calories");
//                    Food food = new Food(id, name, calories);
//                    foodObservableList.add(food);
//
//                    int[] counters = new int[foodObservableList.size()];
//                    for (Food f : foodObservableList) { // find out if the food was added
//
//                    }
//                }
//            }
//
//        }
//    }


    private void extractResultSet(ResultSet resultSet, ObservableList<Food> foodObservableList) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int calories = resultSet.getInt("calories");
            Food food = new Food(id, name, calories);
            foodObservableList.add(food);
        }
    }

    private void extractResultSet(ResultSet resultSet, ArrayList<Food> foodArrayList) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int calories = resultSet.getInt("calories");
            Food food = new Food(id, name, calories);
            foodArrayList.add(food);
        }
    }


}
