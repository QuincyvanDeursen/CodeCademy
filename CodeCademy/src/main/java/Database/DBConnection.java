package Database;

import java.sql.*;

public class DBConnection {
    private static final String connectionURL = "jdbc:sqlserver://localhost;databaseName=Codecademy;integratedSecurity=true;";
    private static final String SQLDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    // Method to create the connection with the CodeCademy database;
    // Method is static so no DBConnection object has to be innitialized.
    // Method is protected to ensure the method can only be used within the Database package.
    protected static Connection getConnection() {
        try {
            Class.forName(SQLDriver);
            Connection connect = DriverManager.getConnection(connectionURL);
            return connect;

        } catch (Exception e) {
            System.out.println("Problem with database connection occured.");
            e.printStackTrace();
            return null;
        }
    }

}
