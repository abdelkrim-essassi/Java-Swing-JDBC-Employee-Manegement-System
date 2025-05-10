package DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SingletonConnection {
    private static Connection connection;

    private SingletonConnection() {}

    public static Connection getInstance() {
        if (connection == null) {
            try {
                Properties props = new Properties();
                props.load(new FileInputStream("config.properties"));

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                Class.forName("com.mysql.cj.jdbc.Driver"); 
                connection = DriverManager.getConnection(url, user, password);

            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
