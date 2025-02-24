package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.smu.view.UiUtil.*;

public class LoginPanel extends JPanel
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    private GridBagConstraints gbc;

    public LoginPanel()
    {   
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
    
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
    
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        LogoLabel logo = new LogoLabel(0.7);
        logoPanel.add(logo);
        
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.setOpaque(false);
        JLabel nameLabel = new JLabel("Saving Money Unina", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 75));
        nameLabel.setForeground(UiUtil.CAPPUCCINO);
        namePanel.add(nameLabel);
        
        JPanel federicoPanel = new JPanel(new FlowLayout());
        federicoPanel.setOpaque(false);
        JLabel federicoLogo = new JLabel(new ImageIcon((new ImageIcon(getClass().getResource("/federico.png")).getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH))));
        federicoPanel.add(federicoLogo);

        BlankPanel blankPanel = new BlankPanel(new Dimension(100, 100));

        topPanel.add(blankPanel, BorderLayout.EAST);
        topPanel.add(federicoPanel, BorderLayout.WEST);
        topPanel.add(logoPanel, BorderLayout.CENTER);
        topPanel.add(namePanel, BorderLayout.SOUTH);
    
        this.add(topPanel, BorderLayout.NORTH);
    
        JPanel credentialsPanel = new JPanel(new GridBagLayout());
        credentialsPanel.setOpaque(false);
        gbc = new GridBagConstraints();
        gbc.gridy = 0;
        setDefaultGbc();
    
        addBlank(credentialsPanel, 8);
        addUsernameField(credentialsPanel);
        addPasswordField(credentialsPanel);
        addMessageLabel(credentialsPanel);
        addLoginButton(credentialsPanel);
    
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(credentialsPanel, BorderLayout.NORTH);
    
        this.add(centerPanel, BorderLayout.CENTER);
    }

    private void setDefaultGbc() 
    {
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
    }

    private void addUsernameField(JPanel panel) 
    {
        JLabel loginLabel = new JLabel("Username");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginLabel.setForeground(Color.WHITE);
        panel.add(loginLabel, gbc);
        gbc.gridy++;
        usernameField = new JTextField(24);
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.insets = new Insets(2, 2, 30, 2);
        panel.add(usernameField, gbc);
        gbc.gridy++;
        setDefaultGbc();
    }

    private void addPasswordField(JPanel panel) 
    {
        JLabel loginLabel = new JLabel("Password");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginLabel.setForeground(Color.WHITE);
        panel.add(loginLabel, gbc);
        gbc.gridy++;
        passwordField = new JPasswordField(24);
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.insets = new Insets(2, 2, 30, 2);
        panel.add(passwordField, gbc);
        gbc.gridy++;
        setDefaultGbc();
    }

    private void addMessageLabel(JPanel panel) 
    {
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(messageLabel, gbc);
        gbc.gridy++;
    }

    private void addLoginButton(JPanel panel) 
    {
        loginButton = UiUtil.createStyledButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setContentAreaFilled(true);
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(loginButton, gbc);
        gbc.gridy++;
        setDefaultGbc();
    }

    private void addBlank(JPanel panel, int height)
    {
        for (int i = 0; i < height; i++)
        {
            JLabel blankLabel = new JLabel(" ");
            panel.add(blankLabel, gbc);
            gbc.gridy++;
        }
    }

    public JButton getLoginButton()
    {
        return loginButton;
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
        messageLabel.setForeground(UiUtil.ERROR_RED);
    }

    public void showSuccessMessage(String message) 
    {
        messageLabel.setText(message);
        messageLabel.setForeground(UiUtil.SUCCESS_GREEN);
    }

    public void clearMessage() 
    {
        messageLabel.setText(" ");
    }

}