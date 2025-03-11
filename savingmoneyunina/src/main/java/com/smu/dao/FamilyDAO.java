package com.smu.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.smu.model.Family;
import com.smu.model.MonthlyBalance;
import com.smu.model.User;


public class FamilyDAO extends DAOconnection
{
	public static Family get(String memberUsername) 
    {
        Family family = null;
        List<User> members = new ArrayList<>();
        
		String sql = "SELECT family.name AS family_name, username, password, \"user\".name AS user_name  FROM family JOIN \"user\" ON family.id = \"user\".family_id WHERE id = (SELECT family_id FROM \"user\" WHERE username = ? )";
		
        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, memberUsername);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                String familyName = rs.getString("family_name");                
                do
                {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String name = rs.getString("user_name");

                    members.add(new User(username, password, name));
                }
                while(rs.next());

                family = new Family(familyName, members);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
        return family;
	}

    public static MonthlyBalance getFamilyMonthlyBalance(Family family, LocalDate date) 
    {
        String sql = "SELECT initial_balance, final_balance FROM get_family_monthly_balance(?, ?)";

        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, getId(family));
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

    public static BigDecimal getMonthlyIncome(LocalDate date, Family family) 
    {
        String sql = "SELECT get_family_monthly_income(?, ?)";

        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, getId(family));
            ps.setDate(2, java.sql.Date.valueOf(date));

            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                return rs.getBigDecimal("get_family_monthly_income");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static BigDecimal getMonthlyExpense(LocalDate date, Family family) 
    {
        String sql = "SELECT get_family_monthly_expense(?, ?)";

        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, getId(family));
            ps.setDate(2, java.sql.Date.valueOf(date));

            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                return rs.getBigDecimal("get_family_monthly_expense");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static int getId(Family family)
    {
        String sql = "SELECT family_id FROM \"user\" WHERE username = ?";

        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, family.getMembers().get(0).getUsername());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                return rs.getInt("family_id");                
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
        return 0;
    }
}