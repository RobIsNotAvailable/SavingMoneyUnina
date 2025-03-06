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
    
    public BigDecimal getInitialBalance(LocalDate date){return FamilyDAO.getFamilyInitialBalance(date, this); }

    public BigDecimal getFinalBalance(LocalDate date){return FamilyDAO.getFamilyFinalBalance(date, this); }

    public BigDecimal getTotalIncome(LocalDate date){return FamilyDAO.getTotalIncome(date, this); }

    public BigDecimal getTotalExpense(LocalDate date){return FamilyDAO.getTotalExpense(date, this); }

    /*************************************DEBUG******************************************* */ 
    public String toString()
    {
        String string = "Family name: " + name + " | Members:";	
        for (User member : members)
        {
            string += " " + member.getUsername();
        }

        return string;
    }
}
