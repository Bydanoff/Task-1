package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

public class Util {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "root";
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static SessionFactory getHibConnection() {
        try {
            if (sessionFactory == null) {
                sessionFactory = new Configuration()
                        .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/postgres")
                        .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "root")
                        .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "postgres")
                        .setProperty(AvailableSettings.SHOW_SQL, "true")
                        .setProperty(AvailableSettings.FORMAT_SQL, "true")
                        .setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgresPlusDialect")
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
            }
            return sessionFactory;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
