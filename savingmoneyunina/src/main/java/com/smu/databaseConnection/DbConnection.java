package com.smu.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection 
{
    private static String jdbcURL = "jdbc:postgresql://localhost:5432/SMU";
    private static String username = "postgres";	
    private static String password = "8caratteri";

    private DbConnection(){}

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
    }    
}
