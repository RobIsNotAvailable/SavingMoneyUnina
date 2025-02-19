package com.smu.view;  

import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.swing.*;

import com.smu.model.PaymentCard;
import com.smu.model.User;  

public class HomePanel extends JPanel  
{  
    public static int cardIndex = 0;

    User user;

    public JButton centerButton; 
    public JButton homeButton;  
    public JButton familButton;  
    public JButton transactionButton;  
    public JButton newTransactionButton;  
    public JButton reporButton; 

    public LeftTriangleButton leftTriangleButton;  
    public RightTriangleButton rightTriangleButton; 

    public JLabel incomeLabel = createStyledLabel("income: 00$");  
    public JLabel expensesLabel = createStyledLabel("expense: 00$"); 
    public JLabel balanceLabel = createStyledLabel("balance: 00$"); 

    public HomePanel()  
    {  
        centerButton = createStyledButton("");

        this.setOpaque(false);  
        this.setLayout(new BorderLayout());  

        // NAVBAR  
        JPanel topPanel = new JPanel(new BorderLayout());  
        topPanel.setOpaque(false);  

        JPanel navbarPanel = new JPanel(new GridLayout(1, 0, 20, 0));  
        navbarPanel.setOpaque(false);  

        homeButton = createStyledButton("Home");  
        familButton = createStyledButton("Family");  
        transactionButton = createStyledButton("Transactions");  
        newTransactionButton = createStyledButton("New Transaction");  
        reporButton = createStyledButton("Reports");  

        navbarPanel.add(homeButton);  
        navbarPanel.add(familButton);  
        navbarPanel.add(transactionButton);  
        navbarPanel.add(newTransactionButton);  
        navbarPanel.add(reporButton);  

        topPanel.add(navbarPanel, BorderLayout.CENTER);  
        this.add(topPanel, BorderLayout.NORTH);  
        // END NAVBAR  

        // Wrapper panel for spacing and buttons  
        JPanel buttonContainer = new JPanel();  
        buttonContainer.setOpaque(false);  
        buttonContainer.setLayout(new BorderLayout());  

        // Panel for buttons and finance labels  
        JPanel buttonWrapper = new JPanel();  
        buttonWrapper.setOpaque(false);  
        buttonWrapper.setLayout(new BorderLayout());  

        // Add spacing between navbar and card  
        buttonWrapper.add(new BlankPanel(new Dimension(1, 100)), BorderLayout.NORTH);  

        leftTriangleButton = new LeftTriangleButton(this);  
        rightTriangleButton = new RightTriangleButton(this);  

        leftTriangleButton.setPreferredSize(new Dimension(80, 80));  
        rightTriangleButton.setPreferredSize(new Dimension(80, 80));  
 
        centerButton.setPreferredSize(new Dimension(400, 200));  
        centerButton.setFont(new Font("Arial", Font.BOLD, 14)); 

        JPanel cardPanel = new JPanel();  
        cardPanel.setOpaque(false);  
        cardPanel.setLayout(new BorderLayout());  
        cardPanel.add(centerButton, BorderLayout.CENTER);  

        // Finance panel with proper spacing and alignment  
        JPanel financeWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));  
        financeWrapper.setOpaque(false);  

        JPanel financePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));  
        financePanel.setOpaque(false);  

        financePanel.add(incomeLabel);  
        financePanel.add(expensesLabel);  
        financePanel.add(balanceLabel);  

        // Add spacing between the card and finance labels  
        financeWrapper.add(new BlankPanel(new Dimension(1, 30)));  
        financeWrapper.add(financePanel);  

        cardPanel.add(financeWrapper, BorderLayout.SOUTH);  

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, -30));  
        buttonRow.setOpaque(false);  
        buttonRow.add(leftTriangleButton);  
        buttonRow.add(cardPanel);  
        buttonRow.add(rightTriangleButton);  

        buttonWrapper.add(buttonRow, BorderLayout.CENTER);  
        buttonContainer.add(buttonWrapper, BorderLayout.CENTER);  

        this.add(buttonContainer, BorderLayout.CENTER);  
    }  

    private JButton createStyledButton(String text)  
    {  
        JButton button = new JButton(text);  
        button.setFont(new Font("Arial", Font.BOLD, 14));  
        button.setBackground(new Color(0, 0, 0, 127));  
        button.setForeground(Color.WHITE);  
        button.setBorderPainted(false);  
        button.setFocusPainted(false);  
        button.setMargin(new Insets(10, 15, 15, 15));  

        
        button.addMouseListener(new java.awt.event.MouseAdapter()  
        {  
            public void mouseEntered(java.awt.event.MouseEvent evt)  
            {  
                button.setBackground(new Color(0, 0, 0, 150));  
            }  

            public void mouseExited(java.awt.event.MouseEvent evt)  
            {  
                button.setBackground(new Color(0, 0, 0, 127));  
            }  
        });  

        return button;  
    }  

    private JLabel createStyledLabel(String text)  
    {  
        JLabel label = new JLabel(text, SwingConstants.CENTER);  
        label.setForeground(Color.WHITE);  
        label.setFont(new Font("Arial", Font.BOLD, 14));  
        return label;  
    }  

    public static class BlankPanel extends JPanel  
    {  
        public BlankPanel(Dimension dimension)  
        {  
            setOpaque(false);  
            setPreferredSize(dimension);  
        }  
    }  

    public void addListener(JButton button, ActionListener listener)
    {
        button.addActionListener(listener);
    }

    public void updateDetails(BigDecimal income, BigDecimal expense, BigDecimal balance)
    {
        incomeLabel.setText("Income: " + income + "€");  
        expensesLabel.setText("Expense: " + expense + "€");  
        balanceLabel.setText("Balance: " + balance + "€");
    }
}  
