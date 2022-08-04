package jm.task.core.jdbc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    static final String DB_URL = "jdbc:mysql://localhost/userdb";
    static final String USER = "root";
    static final String PASS = "root";

        private static Connection connection = null;
        private static Util instance = null;

        private Util() {
            try {
                if (null == connection || connection.isClosed()) {
                    connection = DriverManager.getConnection(DB_URL, USER, PASS);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static Util getInstance() {
            if (null == instance) {
                instance = new Util();
            }
            return instance;
        }

        public Connection getConnection() {
            return connection;
        }
    }
