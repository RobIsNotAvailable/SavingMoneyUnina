package com.smu.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import com.smu.model.User;

public class FamilyPanel extends DefaultPanel
{
    private JPanel usersTable;

    public FamilyPanel()
    {
        remove(cardManager);

        usersTable = new JPanel(new GridLayout());
        usersTable.setOpaque(false);

        this.add(usersTable, BorderLayout.CENTER);
    }

    public void showUsers(List<User> users)
    {
        
    }
}
