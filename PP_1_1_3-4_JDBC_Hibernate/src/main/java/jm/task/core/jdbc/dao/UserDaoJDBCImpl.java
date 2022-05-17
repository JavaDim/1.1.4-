package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    Util util = new Util();

    public void createUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Users (id BIGINT PRIMARY KEY AUTO_INCREMENT, "
                    + "name varchar(50), lastname varchar(50), age int)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = util.getConnection().createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement("INSERT INTO Users VALUES(0, ?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем –" + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement("DELETE FROM Users WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement("SELECT * FROM Users");
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                users.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement("DELETE FROM Users")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
