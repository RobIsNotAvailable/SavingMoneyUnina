package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.smu.MainController;
import com.smu.model.User;
import com.smu.view.LoginPanel;


public class LoginController 
{
    private MainController main;
    private LoginPanel view;

    public LoginController(MainController main, LoginPanel view) 
    {
        // Loads the DB connection and relative objects, making login button more responsive on the first attempt
        new User(null, null).verify();
        this.main = main;
        this.view = view;
        this.view.addLoginListener(new LoginListener());
    }

    private class LoginListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String username = view.getUsername();
            String password = view.getPassword();

            if (username.isEmpty() || password.isEmpty()) 
            {
                view.showErrorMessage("Fill both fields and try again");
                return;
            }
            
            User user = new User(username, password);

            if (user.verify()) 
            {
                view.showSuccessMessage("Login successful");
                main.loadScreens(user);
                main.showScreen("Home");
            } else 
            {
                view.showErrorMessage("Invalid credentials");
            }
        }
    }
}
