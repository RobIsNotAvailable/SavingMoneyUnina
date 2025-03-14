package com.smu.model;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import com.smu.dao.FamilyDAO;

public class Family
{
    private String name;
    private List<User> members;

    public Family(String name, List<User> members)
    {
        this.name = name;
        this.members = new ArrayList<User>(members);
    }

    /*************************************GETTER******************************************* */ 

    public String getName() { return name; }

    public List<User> getMembers() { return members; }
    
    public MonthlyBalance getMonthlyBalance(LocalDate date) { return FamilyDAO.getFamilyMonthlyBalance(this, date); }

    public BigDecimal getMonthlyIncome(LocalDate date) { return FamilyDAO.getMonthlyIncome(date, this); }

    public BigDecimal getMonthlyExpense(LocalDate date) { return FamilyDAO.getMonthlyExpense(date, this); }
}
