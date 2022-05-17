package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/testik";
    private static final String USER = "root";
    private static final String PASSWORD = "Insar@2014"; // реализуйте настройку соеденения с БД
    private static SessionFactory sessionFactory;
    private Connection connection;

    public Util() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
               // properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL, URL); //"jdbc:mysql://localhost:3306/testik?useSSL=false"
                properties.put(Environment.USER, USER);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(Environment.HBM2DDL_AUTO, "none");
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
