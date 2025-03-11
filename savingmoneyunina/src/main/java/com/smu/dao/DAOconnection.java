package com.smu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DAOconnection
{

    private static String jdbcURL = "jdbc:postgresql://localhost:5432/SMU";
    private static String username = "postgres";	
    private static String password = "8caratteri";

    static Connection conn = getConnection();

    public static Connection getConnection()
    {
        try 
        {
            Connection conn = DriverManager.getConnection(jdbcURL,username,password);

            return conn;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
        return null;
    }   }