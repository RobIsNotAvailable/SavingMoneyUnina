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
    private JLabel messageLabel;

    private JButton directionButton;
    private JButton currencyButton;

    public NewTransactionPanel()
    {
        addFormPanel();
    }

    private void addFormPanel()
    {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(1000, 400));
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);

        addDirectionButton(formPanel, gbc);
        addAmountField(formPanel, gbc);
        addCurrencyButton(formPanel, gbc);
        addBlankPanel(formPanel, gbc);
        addDescriptionLabel(formPanel, gbc);
        addDescriptionField(formPanel, gbc);
        addInsertButton(formPanel, gbc);
        addMessageLabel(formPanel, gbc);

        this.add(formPanel, BorderLayout.SOUTH);
    }

    private void addDirectionButton(JPanel panel, GridBagConstraints gbc)
    {
        directionButton = UiUtil.createStyledButton("-");
        directionButton.setFont(new Font("Courier New", Font.BOLD, 50));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(directionButton, gbc);
    }

    private void addAmountField(JPanel panel, GridBagConstraints gbc)
    {
        DecimalFormat format = new DecimalFormat("0.00");
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);

        NumberFormatter amountFormatter = new NumberFormatter(format);
        amountFormatter.setValueClass(Double.class);
        amountFormatter.setAllowsInvalid(false);
        amountFormatter.setOverwriteMode(false);
        amountFormatter.setMinimum(0.00);

        amountField = new JFormattedTextField(new DefaultFormatterFactory(amountFormatter));
        amountField.setValue(0.00);
        UiUtil.styleComponent(amountField);
        amountField.setMinimumSize(new Dimension(400, 150));
        amountField.setMaximumSize(new Dimension(500, 150));

        amountField.setFont(new Font("Arial", Font.BOLD, 100));
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);

        gbc.gridx = 2;
        gbc.insets = new Insets(10, 20, 10, 5);
        panel.add(amountField, gbc);
    }

    private void addCurrencyButton(JPanel panel, GridBagConstraints gbc)
    {
        currencyButton = UiUtil.createStyledButton("€");
        currencyButton.setFont(new Font("Arial", Font.BOLD, 50));
        gbc.gridx = 3;
        gbc.insets = new Insets(10, 5, 10, 5);
        panel.add(currencyButton, gbc);
    }

    private void addBlankPanel(JPanel panel, GridBagConstraints gbc)
    {
        JPanel blankPanel = new UiUtil.BlankPanel(new Dimension(1000, 10));
        blankPanel.setOpaque(false);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(blankPanel, gbc);
    }

    private void addDescriptionLabel(JPanel panel, GridBagConstraints gbc)
    {
        JLabel descriptionLabel = UiUtil.createStyledLabel("Description");
        descriptionLabel.setHorizontalAlignment(JLabel.LEFT);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 30));
        descriptionLabel.setForeground(Color.WHITE);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 0, 5);
        panel.add(descriptionLabel, gbc);
    }

    private void addDescriptionField(JPanel panel, GridBagConstraints gbc)
    {
        descriptionField = new JTextField(1);
        UiUtil.styleComponent(descriptionField);
        descriptionField.setMinimumSize(new Dimension(300, 100));
        descriptionField.setMaximumSize(new Dimension(500, 100));
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 40));
        gbc.gridy = 3;
        gbc.insets = new Insets(2, 20, 10, 5);
        panel.add(descriptionField, gbc);
    }

    private void addInsertButton(JPanel panel, GridBagConstraints gbc)
    {
        insertButton = UiUtil.createStyledButton("Insert");
        insertButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        insertButton.setOpaque(true);
        insertButton.setContentAreaFilled(true);
        insertButton.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 20, 10, 5);
        panel.add(insertButton, gbc);
    }
    
    private void addMessageLabel(JPanel panel, GridBagConstraints gbc)  
    {  
        messageLabel = UiUtil.createStyledLabel(" ");  
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));  
        messageLabel.setForeground(UiUtil.ERROR_RED);  

        gbc.gridx = 2;  
        gbc.gridy = 2; 
        gbc.insets = new Insets(10, 20, 10, 5);  
        panel.add(messageLabel, gbc);  
    }

    public void showErrorMessage(String message)
    {
        messageLabel.setForeground(UiUtil.ERROR_RED);
        messageLabel.setText(message);
    }

    public void showSuccessMessage(String message)
    {
        messageLabel.setForeground(UiUtil.SUCCESS_GREEN);
        messageLabel.setText(message);
            
        
        new Thread(() ->  
        {  
            try  
            {  
                Thread.sleep(4000); 
            }  
            catch (InterruptedException e)  
            {  
                e.printStackTrace();  
            }  

            // Update UI safely after the delay
            javax.swing.SwingUtilities.invokeLater(() -> resetMessage());  
        }).start();  
    }

    public void resetMessage()
    {
        messageLabel.setText(" ");
    }

    public void resetAmountField()
    {
        amountField.setValue(0.00);
    }

    public void resetDescriptionField()
    {
        descriptionField.setText("");
    }

    public void resetDirection()
    {
        if(directionButton.getText().equals("+"))
            directionButton.doClick();
    }

    /****************************************** GETTERS ************************************************************** */
    public JFormattedTextField getAmountField() { return amountField; }

    public JTextField getDescriptionField() { return descriptionField; }

    public JButton getInsertButton() { return insertButton; }

    public JLabel getMessageLabel() { return messageLabel; }

    public JButton getDirectionButton() { return directionButton; }

    public JButton getCurrencyButton() { return currencyButton; }

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