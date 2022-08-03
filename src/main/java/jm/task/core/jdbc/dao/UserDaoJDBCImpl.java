package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    //private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE USERS " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(255), " +
                    " lastName VARCHAR(255), " +
                    " age INTEGER, " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void dropUsersTable() throws SQLException {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            Statement stmt = connection.createStatement();
            String sql = "DROP TABLE IF EXISTS USERS";
            stmt.executeUpdate(sql);
            System.out.println("Table deleted in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = null;
        try {

            connection = Util.getConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO users (name, lastName, age) Values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            int rows = preparedStatement.executeUpdate();

            System.out.println("User is added: " + rows);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }


    public void removeUserById(long id) throws SQLException {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            int rows = preparedStatement.executeUpdate();

            System.out.println("User is deleted: " + rows);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            ResultSet resultSet = null;
            Statement stmt = connection.createStatement();
            String sqlSelectQuery = "SELECT * FROM USERS";
            resultSet = stmt.executeQuery(sqlSelectQuery);
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            String sql = "Truncate table USERS";
            stmt.executeUpdate(sql);
            System.out.println("Table truncated....");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.close();
        }

    }
}
