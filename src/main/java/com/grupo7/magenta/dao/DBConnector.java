package com.grupo7.magenta.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
    
    public static final String URL = "jdbc:mysql://localhost:3306/cine_db";
    public static final String USER = "poo";
    public static final String PASS = "password123";
    



    public static Connection getConnection() {
        Connection con = null;
        
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexion Exitosa");

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver JDBC");
        } catch (SQLException e) {
            System.out.println("Error al establecer la conexión a la base de datos");
        }
        return con;
    }
}
