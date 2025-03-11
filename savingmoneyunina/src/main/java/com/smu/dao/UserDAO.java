package com.smu.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.smu.model.User;
import com.smu.model.Category;
import com.smu.model.Family;
import com.smu.model.MonthlyBalance;
import com.smu.model.PaymentCard;

public class UserDAO extends DAOconnection
{
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
                String name = rs.getString("name");

                return new User(username, password, name);
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

        String sql = "SELECT card_number FROM payment_card WHERE owner_username = ? ORDER BY card_number";

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

        String sql = "SELECT * FROM category WHERE creator_username = ?";

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

    public static String retrieveName(User user)
    {

		String sql = "SELECT name FROM \"user\" WHERE username = ?";
		
        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                return rs.getString("name");
            }

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();

        }
        
        return null;
    }

    public static BigDecimal getMonthlyIncome(User user, LocalDate date)
    {
        String sql = "SELECT get_user_monthly_income(?,?)";
        
        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, user.getUsername());
            ps.setDate(2, java.sql.Date.valueOf(date));

            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                BigDecimal totalIncome = rs.getBigDecimal("get_user_monthly_income");
                
                return totalIncome;
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return null;    
    }

    public static BigDecimal getMonthlyExpense(User user, LocalDate date)
    {
        String sql = "SELECT get_user_monthly_expense(?,?)";
        
        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, user.getUsername());
            ps.setDate(2, java.sql.Date.valueOf(date));

            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                BigDecimal totalExpense = rs.getBigDecimal("get_user_monthly_expense");
                
                return totalExpense;
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return null; 
    }

    public static MonthlyBalance getMonthlyBalance(User user, LocalDate date) 
    {
        String sql = "SELECT initial_balance, final_balance FROM get_user_monthly_balance(?, ?)";

        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setDate(2, java.sql.Date.valueOf(date));

            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                BigDecimal initialBalance = rs.getBigDecimal("initial_balance");
                BigDecimal finalBalance = rs.getBigDecimal("final_balance");
                
                return new MonthlyBalance(initialBalance, finalBalance);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    
}

