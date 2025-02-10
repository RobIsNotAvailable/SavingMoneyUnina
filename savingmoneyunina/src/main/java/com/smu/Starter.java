package com.smu;

import com.smu.dao.UserDAO;
import com.smu.dao.UserDAOimpl;
import com.smu.model.User;

public class Starter 
{
    public static void main( String[] args )
    {
        UserDAO dao= new UserDAOimpl();
        
        for(User u: dao.getAll())
            System.out.println(u.getUsername()+ " " + u.getPassword());
    }
}