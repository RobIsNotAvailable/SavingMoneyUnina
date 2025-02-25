package com.smu.model;

import java.util.List;

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

    /*************************************GETTER******************************************* */ 

    public String getName() { return name; }

    public List<User> getMembers() { return members; }

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
