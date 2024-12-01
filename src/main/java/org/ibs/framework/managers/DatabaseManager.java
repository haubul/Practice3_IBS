package org.ibs.framework.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для управления базой данных
 */

public class DatabaseManager {

    /**
     * Переменная для хранения объекта соединения
     */

    private final Connection connection;

    /**
     *  Инициализирует соединение с базой данных
     * @throws SQLException - если возникает ошибка при соединении с базой данных
     */

    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost:9092/mem:testdb",
                "user",
                "pass"
        );
    }

    /**
     * Проверяет, существует ли товар с заданными параметрами в базе данных
     * @param name - название товара
     * @param type - тип товара
     * @param exotic - параметр - чекбокс (экзотический или не экзотический)
     * @return возвращает true, если товар существует в БД, иначе возвращает false
     * @throws SQLException - если возникает ошибка при выполнении запроса к базе данных
     */

    public boolean isProductInDatabase(String name, String type, int exotic) throws SQLException {
        String query = "SELECT food_name, food_type, food_exotic FROM FOOD WHERE food_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("food_name").equals(name) &&
                        resultSet.getString("food_type").equals(type) &&
                        resultSet.getInt("food_exotic") == exotic;
            }
            return false;
        }
    }


    /**
     * Удаляет товар с заданными параметрами из базы данных
     * @param name - название товара
     * @throws SQLException - если возникает ошибка при выполнении запроса к базе данных
     */

    public void deleteProductFromDatabase(String name) throws SQLException {
        String query = "DELETE FROM FOOD WHERE food_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        }
    }

    /**
     * Закрывает соединение с базой данных
     */

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}