package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    static final String DB_URL = "jdbc:mysql://localhost/userdb";
    static final String USER = "root";
    static final String PASS = "root";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Getting connection");
          return connection;
            } catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        }
    }
