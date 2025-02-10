package com.smu.model;

import java.util.List;

import com.smu.dao.FamilyDAO;

import java.util.ArrayList;

public class Family
{
    private String name;
    private List<User> members;

    public Family(String name, List<User> members)
    {
        this.name = name;
        this.members = new ArrayList<User>(members);
    }

    public Family(User user)
    {
        Family familyLoader = FamilyDAO.get(user.getUsername());
        this.name = familyLoader.getName();
        this.members = familyLoader.getMembers();
    }

    public String getName()
    {
        return name;
    }

    public List<User> getMembers()
    {
        return members;
    }

    @Override
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
