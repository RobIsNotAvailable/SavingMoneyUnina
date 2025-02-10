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

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public List<PaymentCard> getCards()
    {
        return UserDAO.getCards(this);
    }

    public List<Category> getCategories()
    {
        return UserDAO.getCategories(this);
    }

    public Family getFamily()
    {
        return UserDAO.getFamily(this);
    }

    public Boolean verify()
    {
        return UserDAO.verify(this);
    }
}
