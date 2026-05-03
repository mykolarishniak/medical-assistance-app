package com.nickolas.medicalassistanceapp.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/medical_app?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

//    public static Connection getConnection() throws Exception {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
public static Connection getConnection() {
    try {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
}
