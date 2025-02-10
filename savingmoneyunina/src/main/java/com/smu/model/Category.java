package com.smu.model;

import java.util.List;
import java.util.ArrayList;

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

    public void setTransactions(List<Transaction> categorizedTransactions)
    {
        this.categorizedTransactions = categorizedTransactions;
    }

    public void setKeywords(List<String> keywords)
    {
        this.keywords = keywords;
    }

    public void addTransaction(Transaction transaction)
    {
        categorizedTransactions.add(0, transaction);
    }

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
