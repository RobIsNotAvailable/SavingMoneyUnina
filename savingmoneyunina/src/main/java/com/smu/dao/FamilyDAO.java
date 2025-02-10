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


public class FamilyDAO implements DAO<Family, String>
{
    Connection conn = null;

    @Override
	public Family get(String usernameMember) 
    {
        conn = DbConnection.getConnection();
        List <User> members = new ArrayList<>();
        Family family = null;

        //WHERE \"user\".username = ?" questo deve diventare dove id famiglai è lo stersso dello user passato in input
        //oppure separa le stringhe e recupera gli utenti dopo
		String sql = "SELECT * FROM family JOIN \"user\" ON family.id = \"user\".family_id WHERE \"user\".username = ?";
		
        try 
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usernameMember);
            ResultSet rs=ps.executeQuery();

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

	@Override
	public List<Family> getAll() 
    {
		// Implementation here
		return new ArrayList<>();
	}


}