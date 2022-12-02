package edu.wgu.tmaama.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQL {
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (MySQL.connection == null) {
            MySQL.connection = DriverManager.getConnection(System.getenv("MYSQL_URL"));
        }
        return MySQL.connection;
    }
}
