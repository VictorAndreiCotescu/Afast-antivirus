package Afast.pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    public DBConnection() {
    }

    public static Connection connectDB() {
        String url = "jdbc:mysql://localhost:3306/afast?autoReconnect=true&SSL=false";

        try {
            Connection connection = DriverManager.getConnection(url, "root", "passwd");
            Statement statement = connection.createStatement();
            String query = "select * from utilizator";
            statement.executeQuery(query);
            return connection;
        } catch (SQLException ex) {
            System.out.println("Conexiunea la baza de date a esuat!");
            ex.printStackTrace();
            return null;
        }
    }

}
