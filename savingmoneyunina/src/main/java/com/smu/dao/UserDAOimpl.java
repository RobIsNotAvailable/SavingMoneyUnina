package com.smu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smu.model.Family;
import com.smu.model.User;
import com.smu.model.PaymentCard;
import com.smu.databaseConnection.DbConnection;
import com.smu.model.Category;

public class UserDAOimpl implements UserDAO
{
    Connection conn = null;

    @Override
    public User get(String username) 
    {
        conn = DbConnection.getConnection();

        String sql = "SELECT * FROM \"user\" WHERE username = ?";

        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs=ps.executeQuery();

            if(rs.next())
            {
                String name = rs.getString("username");
                String password = rs.getString("password");

                User user = new User(name, password);

                ps.close();
                conn.close();
                rs.close();

                return user;
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<User> getAll() 
    {
        conn = DbConnection.getConnection();
        String sql = "SELECT * FROM \"user\"";
        List<User> users = new ArrayList<>();
        
        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                String name = rs.getString("username");
                String password = rs.getString("password");
                User user = new User(name, password);
                users.add(user);
            }

            ps.close();
            conn.close();
            rs.close();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public Boolean checkValidUser(String username, String password) 
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkValidUser'");
    }

    @Override
    public List<PaymentCard> getCards(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCards'");
    }

    @Override
    public List<Category> getCategories(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategories'");
    }

    @Override
    public Category getOtherCategory(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOtherCategory'");
    }

    @Override
    public Family getFamily(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFamily'");
    }
}
