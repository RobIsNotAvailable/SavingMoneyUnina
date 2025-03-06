package com.smu.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.smu.databaseConnection.DbConnection;
import com.smu.model.Family;
import com.smu.model.User;


public class FamilyDAO
{
    static Connection conn = DbConnection.getConnection();

	public static Family get(String memberUsername) 
    {
        Family family = null;
        List<User> members = new ArrayList<>();
        
		String sql = "SELECT * FROM family JOIN \"user\" ON family.id = \"user\".family_id WHERE id = (SELECT family_id FROM \"user\" WHERE username = ? )";
		
        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, memberUsername);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                String familyName = rs.getString("name");                
                do
                {
                    String name = rs.getString("username");
                    String password = rs.getString("password");

                    members.add(new User(name, password));
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

    public static BigDecimal getFamilyInitialBalance(LocalDate date, Family family) 
    {
        String sql = "SELECT get_family_monthly_income(?,?)";

        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            //ps.setInt(1, family.getId());
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return null;
    }

    public static BigDecimal getFamilyFinalBalance(LocalDate date, Family family) 
    {
        throw new UnsupportedOperationException("Unimplemented method 'getFamilyFinalBalance'");
    }

    public static BigDecimal getTotalIncome(LocalDate date, Family family) 
    {
        throw new UnsupportedOperationException("Unimplemented method 'getTotalIncome'");
    }

    public static BigDecimal getTotalExpense(LocalDate date, Family family) 
    {
        throw new UnsupportedOperationException("Unimplemented method 'getTotalExpense'");
    }
}