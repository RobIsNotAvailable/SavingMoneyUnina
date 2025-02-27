package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.math.BigDecimal;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.smu.model.CurrencyConverter.Currency;
import com.smu.model.Transaction.Direction;

public class NewTransactionPanel extends DefaultPanel
{
    private JFormattedTextField amountField;
    private JTextField descriptionField;

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
        
        // Create a DecimalFormat to enforce numeric input and limit to 2 decimal places
        DecimalFormat format = new DecimalFormat("0.00");
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        
        NumberFormatter amountFormatter = new NumberFormatter(format);
        amountFormatter.setValueClass(Double.class);
        amountFormatter.setAllowsInvalid(false);
        amountFormatter.setOverwriteMode(false);  // Try setting to false
        amountFormatter.setMinimum(0.00);
        
        amountField = new JFormattedTextField(new DefaultFormatterFactory(amountFormatter));
        amountField.setValue(0.00);
        UiUtil.styleComponent(amountField);
        amountField.setMinimumSize(new Dimension(300, 150)); // Reduced size
        amountField.setMaximumSize(new Dimension(500, 150)); // Reduced size
        amountField.setFont(new Font("Arial", Font.BOLD, 100)); 
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        
    
        directionButton = UiUtil.createStyledButton("-");
        directionButton.setFont(new Font("Courier New", Font.BOLD, 50));
    
        currencyButton = UiUtil.createStyledButton("€");
        currencyButton.setFont(new Font("Arial", Font.BOLD, 50));
    
        // Add direction button
        gbc.gridx = 1; // Moved to the right
        formPanel.add(directionButton, gbc);
    
        // Add amount field next to direction button
        gbc.gridx = 2; // Moved to the right
        gbc.insets = new Insets(10, 20, 10, 5); // Adjusted padding to move right
        formPanel.add(amountField, gbc);
    
        gbc.gridx = 3; // Moved to the right
        formPanel.add(currencyButton, gbc);
    
        JPanel blankPanel = new UiUtil.BlankPanel(new Dimension(1000,50));
        blankPanel.setOpaque(false);
        gbc.gridx = 2; // Moved to the right
        gbc.gridy = 1;
        formPanel.add(blankPanel, gbc);
        
        // Description Label
        JLabel descriptionLabel = UiUtil.createStyledLabel("Description");
        descriptionLabel.setHorizontalAlignment(JLabel.LEFT);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        descriptionLabel.setForeground(Color.WHITE);
        gbc.gridx = 2; // Aligned with the description field
        gbc.gridy = 2; // Positioned directly above the description field
        gbc.insets = new Insets(0, 20, 0, 5); // No extra padding
        formPanel.add(descriptionLabel, gbc);
    
        // Move to next row before adding description field
        gbc.gridy++;
        gbc.insets = new Insets(2, 20, 10, 5); // Small top padding for closeness
    
        descriptionField = new JTextField();
        UiUtil.styleComponent(descriptionField);
        descriptionField.setMinimumSize(new Dimension(300, 100));
        descriptionField.setMaximumSize(new Dimension(500, 100));
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 40));
        formPanel.add(descriptionField, gbc);
    
        insertButton = UiUtil.createStyledButton("Insert");
        insertButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        insertButton.setOpaque(true);
        insertButton.setContentAreaFilled(true);
        insertButton.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 2; // Moved to the right
        gbc.gridy = 4; // Positioned under the description field
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

    public JFormattedTextField getAmountField() { return amountField; }

    public JTextField getDescriptionField() { return descriptionField; }

    public JButton getInsertButton() { return insertButton; }

    public JLabel getErrorLabel() { return errorLabel; }

    public JButton getDirectionButton() {return directionButton; }

    public JButton getCurrencyButton() {return currencyButton; }

    public BigDecimal getAmountValue() throws Exception
    {
        String amountString = amountField.getText();

        if (amountString.equals("0,00"))
            throw new Exception("The amount can't be zero");

        amountString = amountString.replace(",", ".");
        return new BigDecimal(amountString);
    }

    public String getDescriptionValue() throws Exception
    {
        String description = descriptionField.getText();
        if (description.length() > 70)
        {
            throw new Exception("Description is too long");
        }
        return description;
    }

    public Currency getCurrencyValue() throws Exception
    {
        String currency = currencyButton.getText(); 

        if (currency.equals("€"))
            return Currency.EUR;

        if (currency.equals("$"))
            return Currency.USD;

        throw new Exception("Invalid currency");
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