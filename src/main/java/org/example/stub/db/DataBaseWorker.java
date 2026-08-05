package org.example.stub.db;

import org.example.stub.dto.User;
import org.example.stub.exception.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;
import java.sql.*;

@Repository
public class DataBaseWorker {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = DataBaseWorker.class.getClassLoader()
                .getResourceAsStream("database.properties")) {

            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String URL = PROPERTIES.getProperty("db.url");
    private static final String USER = PROPERTIES.getProperty("db.user");
    private static final String PASSWORD = PROPERTIES.getProperty("db.password");

    private static final String userByLoginSql =
            "SELECT u.login, u.password, u.registration_date, e.email " +
            "FROM users u " +
            "JOIN user_emails e ON u.login = e.login " +
            "WHERE u.login = ?";

    private static final String insertUserSql =
            "INSERT INTO users(login, password, registration_date) VALUES (?, ?, ?)";

    private static final String insertEmailSql =
            "INSERT INTO user_emails(login, email) VALUES (?, ?)";


    public User getUserByLogin(String login) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(userByLoginSql)) {

            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return new User(
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getDate("registration_date").toLocalDate()
                    );

                } else {
                    throw new UserNotFoundException(login);
                }

        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    public int insertUser(User user) {

        if (user.getRegistrationDate() == null) {
            user.setRegistrationDate(LocalDate.now());
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement psUser = connection.prepareStatement(insertUserSql);
             PreparedStatement psEmail = connection.prepareStatement(insertEmailSql)) {

            connection.setAutoCommit(false);

            psUser.setString(1, user.getLogin());
            psUser.setString(2, user.getPassword());
            psUser.setDate(3, Date.valueOf(user.getRegistrationDate()));

            int rows = psUser.executeUpdate();

            psEmail.setString(1, user.getLogin());
            psEmail.setString(2, user.getEmail());

            rows += psEmail.executeUpdate();


            if (rows != 2) {
                throw new RuntimeException();
            }

            connection.commit();

            return rows;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}

