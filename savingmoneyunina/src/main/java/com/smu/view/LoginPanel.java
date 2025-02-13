package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel
{

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    // how to refactor: declare the gbc as a class variable
    // make a setDefaultGbc() method that sets it to the most used values, without changing gridy
    // separate each component insertion in its own method, that:
    // makes the necessary changes to gbc
    // inserts the component
    // increases gridy
    // calls setDefaultGbc()
    public LoginPanel()
    {   
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        LogoLabel logo = new LogoLabel(1.3);
        logoPanel.add(logo, SwingConstants.CENTER);
        this.add(logoPanel, BorderLayout.NORTH);

        JPanel credentialsPanel = new JPanel(new GridBagLayout());
        credentialsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel loginLabel = new JLabel("Log in");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        credentialsPanel.add(loginLabel, gbc);

        usernameField = new JTextField(30);
        usernameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy++;
        credentialsPanel.add(usernameField, gbc);

        passwordField = new JPasswordField(30);
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridy++;
        credentialsPanel.add(passwordField, gbc);

        messageLabel = new JLabel(" ");
        gbc.gridy++;
        gbc.gridheight = 2;
        credentialsPanel.add(messageLabel, gbc);

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridy += 2; // the previous cell is 2 cells high
        gbc.gridheight = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        credentialsPanel.add(loginButton, gbc);

        this.add(credentialsPanel, BorderLayout.CENTER);
    }
        
    public String getUsername() 
    {
        return usernameField.getText();
    }

    public String getPassword() 
    {
        return new String(passwordField.getPassword());
    }

    public void showErrorMessage(String message) 
    {
        messageLabel.setText(message);
        messageLabel.setForeground(Color.RED);
    }

    public void showSuccessMessage(String message) 
    {
        messageLabel.setText(message);
        messageLabel.setForeground(Color.GREEN);
    }

    public void addLoginListener(ActionListener listener) 
    {
        loginButton.addActionListener(listener);
    }

    public void clearMessage() 
    {
        messageLabel.setText(" ");
    }
}

