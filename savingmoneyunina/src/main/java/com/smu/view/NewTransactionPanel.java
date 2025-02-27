package com.smu.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.smu.model.Transaction.Direction;

public class NewTransactionPanel extends DefaultPanel
{
    private JTextField amount;
    private JTextField description;
    private JRadioButton incomeButton;
    private JRadioButton expenseButton;
    private JRadioButton eurButton;
    private JRadioButton usdButton;

    private JButton insertButton;
    private JLabel errorLabel;

    private JButton directionButton;
    private JButton currencyButton;

    public NewTransactionPanel()
    {
        addFormPanel();
    }
        
    private void addFormPanel() 
    {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5); // Padding

        formPanel.setPreferredSize(new Dimension(1000, 400));
        formPanel.setOpaque(false);

        // Large Amount Field
        amount = new JTextField("00,00", 10);
        amount.setBackground(UiUtil.LOGO_BLACK);
        amount.setBorder(BorderFactory.createLineBorder(UiUtil.CAPPUCCINO, 2));
        amount.setMinimumSize(new Dimension(300, 150)); // Reduced size
        amount.setMaximumSize(new Dimension(500, 150)); // Reduced size
        amount.setFont(new Font("Arial", Font.BOLD, 100)); 
        amount.setForeground(Color.WHITE);
        amount.setHorizontalAlignment(JTextField.CENTER); // Center text

        directionButton = UiUtil.createStyledButton("+");;

        currencyButton = UiUtil.createStyledButton("EUR");
        currencyButton.setFont(new Font("Courier New", Font.BOLD, 50));
        // Add direction button
        gbc.gridx = 1; // Moved to the right
        formPanel.add(directionButton, gbc);

        // Add amount field next to direction button
        gbc.gridx = 2; // Moved to the right
        gbc.insets = new Insets(10, 20, 10, 5); // Adjusted padding to move right
        formPanel.add(amount, gbc);

        gbc.gridx = 3; // Moved to the right
        formPanel.add(currencyButton, gbc);

        // Add blank panel to the left
        gbc.gridx = 0; // Position to the left
        formPanel.add(new UiUtil.BlankPanel(new Dimension(60, 100)), gbc);

        JPanel blankPanel = new UiUtil.BlankPanel(new Dimension(1000,50));
        blankPanel.setOpaque(false);
        gbc.gridx = 2; // Moved to the right
        gbc.gridy = 1;
        formPanel.add(blankPanel, gbc);
        

        // Description Field
        description = new JTextField("Description", 20);
        description.setBackground(UiUtil.LOGO_BLACK);
        description.setBorder(BorderFactory.createLineBorder(UiUtil.CAPPUCCINO, 2));
        description.setMinimumSize(new Dimension(300, 100));
        description.setMaximumSize(new Dimension(500, 100));
        description.setFont(new Font("Arial", Font.PLAIN, 40));
        description.setForeground(Color.WHITE);
        description.setHorizontalAlignment(JTextField.CENTER); // Center text

        // Add description field under blank panel
        gbc.gridx = 2; // Moved to the right
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 20, 10, 5); // Adjusted padding to move right
        formPanel.add(description, gbc);


        insertButton = UiUtil.createStyledButton("Insert");
        insertButton.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 2; // Moved to the right
        gbc.gridy = 3; // Positioned under the description field
        gbc.insets = new Insets(20, 20, 10, 5); // Adjusted padding
        formPanel.add(insertButton, gbc);

        this.add(formPanel, BorderLayout.SOUTH);
    }

    public void showErrorMessage(String message)
    {
        errorLabel.setText(message);
    }

    public void clearErrorMessage()
    {
        errorLabel.setText(" ");
    }

    public JTextField getAmountButton() { return amount; }

    public JTextField getDescription() { return description; }

    public JRadioButton getIncomeButton() { return incomeButton; }

    public JRadioButton getExpenseButton() { return expenseButton; }

    public JRadioButton getEurButton() { return eurButton; }

    public JRadioButton getUsdButton() { return usdButton; }

    public JButton getInsertButton() { return insertButton; }

    public JLabel getErrorLabel() { return errorLabel; }

    public JButton getDirectionButton() {return directionButton; }

    public JButton getCurrencyButton() {return currencyButton; }

    public double getAmount() { return Double.parseDouble(amount.getText()); }

    public String getDescriptionValue(){return description.getText();}

    public String getCurrencyType() 
    {
        if (eurButton.isSelected()) 
        {
            return "EUR";
        } 
        else if (usdButton.isSelected()) 
        {
            return "USD";
        }
        return null;
    }
    
    public Direction getDirection() throws Exception
    {
        String direction = directionButton.getText(); 

        if(direction.equals("+"))
            return Direction.INCOME;

        if(direction.equals("-"))
            return Direction.EXPENSE;

        throw new Exception("Invalid direction");
    }
}
