package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDataAccessor {

    private Connection connection;

    public FoodDataAccessor(String driverClassName, String databaseUrl, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(databaseUrl, user, password);
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public List<Food> getFoodList() throws SQLException {
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM food");
                ){
            List<Food> foodList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int calories = resultSet.getInt("calories");
                Food food = new Food(id, name, calories);
                foodList.add(food);
            }
            return foodList;
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
