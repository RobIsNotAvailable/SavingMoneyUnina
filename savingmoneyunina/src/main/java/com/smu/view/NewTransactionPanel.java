package com.smu.view;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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

    public NewTransactionPanel()
    {
        addFormPanel();
    }
        
    private void addFormPanel() 
    {
        JPanel formPanel = new JPanel();

        formPanel.setPreferredSize(new Dimension(400, 350));
        formPanel.setOpaque(false);

        amount = new JTextField();
        description = new JTextField();
        incomeButton = new JRadioButton("Income");
        expenseButton = new JRadioButton("Expense");
        eurButton = new JRadioButton("EUR");
        usdButton = new JRadioButton("USD");

        ButtonGroup transactionTypeGroup = new ButtonGroup();
        transactionTypeGroup.add(incomeButton);
        transactionTypeGroup.add(expenseButton);
        incomeButton.setSelected(true);

        ButtonGroup currencyGroup = new ButtonGroup();
        currencyGroup.add(eurButton);
        currencyGroup.add(usdButton);
        eurButton.setSelected(true);

        formPanel.add(UiUtil.createStyledLabel("Amount:"));
        formPanel.add(amount);
        formPanel.add(UiUtil.createStyledLabel("Description:"));
        formPanel.add(description);
        formPanel.add(incomeButton);
        formPanel.add(expenseButton);
        formPanel.add(eurButton);
        formPanel.add(usdButton);

        insertButton = UiUtil.createStyledButton("Insert");
        formPanel.add(insertButton);
        errorLabel = UiUtil.createStyledLabel(" ");
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

    public JTextField getAmount() { return amount; }

    public JTextField getDescription() { return description; }

    public JRadioButton getIncomeButton() { return incomeButton; }

    public JRadioButton getExpenseButton() { return expenseButton; }

    public JRadioButton getEurButton() { return eurButton; }

    public JRadioButton getUsdButton() { return usdButton; }

    public JButton getInsertButton() { return insertButton; }

    public JLabel getErrorLabel() { return errorLabel; }

    public String getAmountValue()
    {
        return amount.getText();
    }

    public String getDescriptionValue()
    {
        return description.getText();
    }

    public String getTransactionType()
    {
        if (incomeButton.isSelected())
        {
            return "Income";
        }
        else if (expenseButton.isSelected())
        {
            return "Expense";
        }
        return null;
    }

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
    
}
