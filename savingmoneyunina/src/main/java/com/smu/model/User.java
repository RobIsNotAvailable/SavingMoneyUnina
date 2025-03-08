package com.smu.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.smu.dao.UserDAO;

public class User 
{
    private String username;
    private String password;
    private String name = null;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String name)
    {
        this(username, password);
        this.name = name;
    }

    public Boolean verify(){ return UserDAO.verify(this); }

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

    public String getName() { return name; }

    public void retrieveName() { this.name = UserDAO.retrieveName(this); }

    public List<PaymentCard> getCards() { return UserDAO.getCards(this); }

    public List<Category> getCategories() { return UserDAO.getCategories(this); }

    public Category getCategory(String name) { return UserDAO.getCategory(this, name); }
    
    public Family getFamily() { return UserDAO.getFamily(this); }

    public BigDecimal getInitialBalance(LocalDate date) { return new BigDecimal(21.00); } //placeholder

    public BigDecimal getFinalBalance(LocalDate date) { return new BigDecimal(21.00); } //placeholder

    public BigDecimal getMonthlyIncome(LocalDate date) { return new BigDecimal(21.00); } //placeholder

    public BigDecimal getMonthlyExpense(LocalDate date) { return new BigDecimal(21.00); } //placeholder

}
