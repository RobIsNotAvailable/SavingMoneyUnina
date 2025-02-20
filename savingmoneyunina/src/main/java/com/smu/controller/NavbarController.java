package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.smu.MainController;
import com.smu.view.UiUtil;
import com.smu.view.UiUtil.Navbar;

public class NavbarController
{
    private MainController main;
    
    public NavbarController(MainController main, Navbar view)
    {
        this.main = main;

        UiUtil.addListener(view.getLogoutButton(), new LogoutListener());
    }

    private class LogoutListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int response = JOptionPane.showConfirmDialog
            (
                null,
                "Are you sure you want to log out?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION)
                main.showScreen("Login");
            

        }
    }
}
