package internet.market.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find mysql Driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "puv34g7hhe4jkopp2!1e$");
        String url = "jdbc:mysql://localhost:3306/internet_shop?serverTimezone=UTC";
        try {
            Connection connection = DriverManager.getConnection(url, dbProperties);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Cant established the connection to db", e);
        }
    }
}

