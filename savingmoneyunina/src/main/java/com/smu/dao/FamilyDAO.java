package com.smu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}