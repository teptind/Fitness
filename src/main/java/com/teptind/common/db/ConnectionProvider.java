package com.teptind.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionProvider {
    //TODO: fill credentials
    private static final String DB = "jdbc:postgresql://address";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public static Connection connect() throws Exception {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(DB, props);
    }
}
