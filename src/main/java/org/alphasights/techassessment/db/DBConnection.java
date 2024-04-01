package org.alphasights.techassessment.db;


import org.alphasights.techassessment.util.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            String url = Constants.DB_URL;
            String user = Constants.DB_USER;
            String password = Constants.DB_PASSWORD;

            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException | SQLException ex) {
                LOGGER.log(Level.SEVERE, "Connection error", ex);
            }
        }

        return connection;
    }
}
