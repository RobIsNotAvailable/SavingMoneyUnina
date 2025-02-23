package com.smu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.smu.databaseConnection.DbConnection;
import com.smu.model.User;
import com.smu.model.Category;
import com.smu.model.Family;
import com.smu.model.PaymentCard;

public class UserDAO
{
    static Connection conn = DbConnection.getConnection();
    
    public static User get(String username)
    {

		String sql = "SELECT * FROM \"user\" WHERE username = ?";
		
        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                String password = rs.getString("password");

                return new User(username, password);
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();

        }
        
        return null;
    }

    public static List<PaymentCard> getCards(User user)
    {
        List<PaymentCard> cards = new ArrayList<>();

        String sql = "SELECT card_number FROM payment_card WHERE owner_username = ?";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                cards.add(PaymentCardDAO.get(rs.getString("card_number")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return cards;
    }
    
    public static List<Category> getCategories(User user)
    {
        List<Category> categories = new ArrayList<>();

        String sql = "SELECT * FROM category WHERE creator_username = ?"; // AND name <> 'Other'

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                categories.add(CategoryDAO.get(rs.getString("name"), user.getUsername()));
            }
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return categories;
    }

    public static Category getCategory(User user, String name)
    {
        return CategoryDAO.get(name, user.getUsername());
    }
    
    public static Family getFamily(User user)
    {
        return FamilyDAO.get(user.getUsername());
    }

    public static Boolean verify(User user)
    {
        String sql = "SELECT * FROM \"user\" WHERE username = ? AND password = ?";

        try
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();

            return rs.next();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
