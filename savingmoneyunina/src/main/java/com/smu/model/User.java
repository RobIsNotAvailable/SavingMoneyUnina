package com.smu.model;

import java.util.List;

import com.smu.dao.UserDAO;

public class User 
{
    private String username;
    private String password;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

        public Boolean verify(){return UserDAO.verify(this);}

        public String toString(){return "Username: " + username + "\nPassword: " + password;}
    /***********************************************************GETTERS****************************************************** */

    @Override
    public boolean equals(Object obj)  
    {  
        if (this == obj) 
            return true;  

        if (obj == null || getClass() != obj.getClass()) 
            return false;  

        User user = (User) obj;

        return username.equals(user.username);
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public List<PaymentCard> getCards() { return UserDAO.getCards(this); }

    public List<Category> getCategories() { return UserDAO.getCategories(this); }

    public Category getCategory(String name) { return UserDAO.getCategory(this, name); }
    
    public Family getFamily() { return UserDAO.getFamily(this); }
}
