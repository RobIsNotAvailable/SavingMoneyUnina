package com.smu.dao;

import java.sql.Connection;

import com.smu.databaseConnection.DbConnection;

public interface DAOconnection
{
    static Connection conn = DbConnection.getConnection();
}