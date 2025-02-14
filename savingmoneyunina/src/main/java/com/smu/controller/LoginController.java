package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.smu.model.User;
import com.smu.view.LoginPanel;

public class LoginController 
{
    private LoginPanel view;

    public LoginController(LoginPanel view) 
    {
        this.view = view;
        this.view.addLoginListener(new LoginListener());
    }

    private class LoginListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            if (username.isEmpty() || password.isEmpty()) 
            {
                view.showErrorMessage("Username and password cannot be empty");
                return;
            }

            if (new User(username, password).verify()) 
            {
                view.showSuccessMessage("Login successful");
                // Proceed to the next screen
            } else 
            {
                view.showErrorMessage("Invalid credentials");
            }
        }
    }
}
