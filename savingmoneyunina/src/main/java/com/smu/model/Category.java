package com.smu.model;

import java.util.List;
import java.util.ArrayList;

import com.smu.dao.CategoryDAO;

public class Category 
{
    private String name;
    private String description;
    private User creator;
    private List<Transaction> categorizedTransactions;
    private List<String> keywords;

    public Category(String name, String description, User creator)
    {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.categorizedTransactions = new ArrayList<Transaction>();
        this.keywords = new ArrayList<String>();
    }

    public Category(String name, String description, User creator, List<Transaction> categorizedTransactions, List<String> keywords)
    {
        this(name, description, creator);
        this.categorizedTransactions = categorizedTransactions;
        this.keywords = keywords;
    }


/*****************************************************GETTERS******************************************** */

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public User getCreator()
    {
        return creator;
    }

    public List<Transaction> getTransactions()
    {
        return categorizedTransactions;
    }

    public List<String> getKeywords()
    {
        return keywords;
    }

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
            if (description.toLowerCase().contains(keyword.toLowerCase()))
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
/*****************************************************DEBUG******************************************** */
   public String toString()
    {
        String output = new String("Category: " + name + "\nDescription: " + description + "\nCreator: " + creator.getUsername() + "\nTransactions:");
        for (Transaction transaction : categorizedTransactions)
        {
            output += "\n" + transaction.toString();
        } 
        return output;
    }
}
