package com.smu.model;

import java.util.List;
import java.util.ArrayList;

import com.smu.dao.CategoryDAO;

public class Category 
{
    private String name;
    private User creator;
    private List<Transaction> categorizedTransactions;
    private List<String> keywords;

    public Category(String name, User creator)
    {
        this.name = name;
        this.creator = creator;
        this.categorizedTransactions = new ArrayList<Transaction>();
        this.keywords = new ArrayList<String>();
    }

    public Category(String name, User creator, List<Transaction> categorizedTransactions, List<String> keywords)
    {
        this(name, creator);
        this.categorizedTransactions = categorizedTransactions;
        this.keywords = keywords;
    }


    /*****************************************************GETTERS******************************************** */

    public String getName() { return name; }

    public User getCreator() { return creator; }

    public List<Transaction> getTransactions() { return categorizedTransactions; }

    public List<String> getKeywords() { return keywords; }

    /*****************************************************SETTERS******************************************** */

    public void setTransactions(List<Transaction> categorizedTransactions)
    {
        this.categorizedTransactions = categorizedTransactions;
    }

    public void setKeywords(List<String> keywords)
    {
        this.keywords = keywords;
    }

    /*****************************************************METHODS******************************************** */
    
    public Boolean insertIfMatches(Transaction transaction)
    {
        for (String keyword : getKeywords())
        {
            if (transaction.getDescription().toLowerCase().contains(keyword.toLowerCase()))
            {
                insertTransaction(transaction);
                return true;
            }
        }

        return false;
    }

    public void insertTransaction(Transaction transaction)
    {
        categorizedTransactions.add(transaction);
        CategoryDAO.insertTransaction(transaction, this);
    }

    @Override
    public boolean equals(Object obj)  
    {  
        if (this == obj) 
            return true;  

        if (obj == null || getClass() != obj.getClass()) 
            return false;  

        Category category = (Category) obj;

        return (name.equals(category.name) && creator.equals(category.creator));
    }
    /*****************************************************DEBUG******************************************** */
   public String toString()
    {
        return name;
    }
}
