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

    public Category(String name, String description, User creator, List<Transaction> categorizedTransactions, List<String> keywords)
    {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.categorizedTransactions = new ArrayList<Transaction>(categorizedTransactions);
        this.keywords = new ArrayList<String>(keywords);
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

    public void addTransaction(Transaction transaction)
    {
        categorizedTransactions.add(0, transaction);
    }
}
