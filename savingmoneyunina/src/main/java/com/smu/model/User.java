package com.smu.model;

// import java.util.List;
// import java.util.ArrayList;

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

    // public List<PaymentCard> getCards()
    // {
    //     return dao cards;
    // }

    // public List<Category> getCategories()
    // {
    //     return dao categories;
    // }

    // public Category getOtherCategory()
    // {
    //     return dao otherCategory;
    // }

    // public Family getFamily()
    // {
    //     return dao.getfamily(this);
    // }
}
