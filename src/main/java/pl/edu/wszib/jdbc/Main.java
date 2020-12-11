package pl.edu.wszib.jdbc;

import pl.edu.wszib.jdbc.model.Role;
import pl.edu.wszib.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static Connection connection = null;
    static String sql = "INSERT INTO tuser (login, pass, role) VALUES (?, ?, ?);";
    static PreparedStatement preparedStatement;

    public static void main(String[] args) {
        connect();
        prepareStatements();
        //insertUser(new User(5, "mateusz", "mateusz", Role.USER));
        //deleteUser(1);
        //updateUser(new User(3, "superAdmin", "superAdmin", Role.ADMIN));
        //insertUser2(new User(5, "DORP DATABASE;", "janusz", Role.USER));
        List<User> users = getAllUsers2();
        System.out.println(users);
    }

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Main.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=utf8", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void insertUser(User user) {
        try {
            String sql = "INSERT INTO tuser (login, pass, role) VALUES ('" + user.getLogin() + "', '" + user.getPass() + "', '" + user.getRole().toString() + "');";
            Statement statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteUser(int id) {
        try {
            String sql = "DELETE FROM tuser WHERE id = " + id + ";";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateUser(User user) {
        try {
            String sql = "UPDATE tuser SET login = '" + user.getLogin() + "', pass = '" + user.getPass() + "', role = '" + user.getRole().toString() + "' WHERE id = " + user.getId() + ";";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tuser;";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);

            while (results.next()) {
                int id = results.getInt("id");
                String login = results.getString("login");
                String pass = results.getString("pass");
                Role role = Role.valueOf(results.getString("role"));

                users.add(new User(id, login, pass, role));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }

    public static void insertUser2(User user) {
        try {
            preparedStatement.clearParameters();

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPass());
            preparedStatement.setString(3, user.getRole().toString());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteUser2(int id) {
        try {
            String sql = "DELETE FROM tuser WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static List<User> getAllUsers2() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tuser;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int id = results.getInt("id");
                String login = results.getString("login");
                String pass = results.getString("pass");
                Role role = Role.valueOf(results.getString("role"));

                users.add(new User(id, login, pass, role));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }

    public static void prepareStatements() {
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
