package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.smu.MainController;
import com.smu.view.Navbar;
import com.smu.view.UiUtil;

public class NavbarController
{
    private MainController main;
    
    public NavbarController(MainController main, Navbar view)
    {
        this.main = main;

        UiUtil.addListener(view.getHomeButton(), new HomeListener());
        UiUtil.addListener(view.getLogoutButton(), new LogoutListener());
        UiUtil.addListener(view.getFamilyButton(), new FamilyListener());
        UiUtil.addListener(view.getNewTransactionButton(), new NewTransactionListener());
        UiUtil.addListener(view.getreportButton(), new ReportListener());
    }

    private class HomeListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            main.getHomeController().refresh();
            main.showScreen("Home");
        }
    }

    private class NewTransactionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            main.getNewTransactionController().refresh();
            main.showScreen("New transaction");
        }
    }

    private class FamilyListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            main.getFamilyController().refresh();
            main.showScreen("Family");
        }
    }

    private class ReportListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            main.getReportController().refresh();
            main.showScreen("Reports");
        }
        
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
