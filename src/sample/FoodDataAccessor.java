package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDataAccessor {

    private Connection connection;

    public FoodDataAccessor() throws SQLException, ClassNotFoundException {
        final String driverClassName = "com.mysql.cj.jdbc.Driver";
        final String databaseUrl = "jdbc:mysql://localhost:3306/FoodDiaryDatabase?useSSL=false";
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

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int calories = resultSet.getInt("calories");
                Food food = new Food(id, name, calories);
                foodObservableList.add(food);
            }
            return foodObservableList;
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


}
