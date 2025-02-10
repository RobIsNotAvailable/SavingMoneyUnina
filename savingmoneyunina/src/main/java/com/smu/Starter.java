package com.smu;

import com.smu.dao.FamilyDAO;
import com.smu.model.Family;
import com.smu.model.User;

public class Starter 
{
    public static void main( String[] args )
    {
        FamilyDAO familyDAO = new FamilyDAO();
        Family f = familyDAO.get("alice");
        
        for(User u: f.getMembers())
        {
            System.out.println(u.getUsername());
        }
    }
}