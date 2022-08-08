package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    static final String DB_URL_DEFAULT = "jdbc:mysql://localhost/userdb";
    static final String DB_USER_DEFAULT = "root";
    static final String DB_PASS_DEFAULT = "root";

    public static Connection getConnection() throws SQLException {
        String dbUrl = getEnv("DB_URL", DB_URL_DEFAULT);
        String dbUser = getEnv("DB_USER", DB_USER_DEFAULT);
        String dbPass = getEnv("DB_PASS", DB_PASS_DEFAULT);
        // если в с-ме нет переменных окружения, то будет брать дефолтные значения DEFAULT
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        System.out.println("Getting connection");
        return connection;
    }

    private static String getEnv(String envName, String envDefault) {
        String env = System.getenv(envName);
        return env == null ? envDefault : env;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private static SessionFactory factory;

    private Util() {
    }

    public static SessionFactory getFactory() {
        if (factory == null) {
            Configuration configuration = new Configuration();

            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/userdb");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "root");

            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            settings.put(Environment.SHOW_SQL, "false");
            settings.put(Environment.HBM2DDL_AUTO, "update");

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            factory = configuration.buildSessionFactory(serviceRegistry);
        }
        return factory;
    }
}
