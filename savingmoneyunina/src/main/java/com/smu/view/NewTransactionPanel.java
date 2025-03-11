package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import com.smu.model.CurrencyConverter.Currency;
import com.smu.model.Transaction.Direction;
import com.smu.view.UiUtil.BlankPanel;


public class NewTransactionPanel extends DefaultPanel
{
    private JFormattedTextField amountField;
    private JFormattedTextField decimalAmountField;

    private JTextField descriptionField;

    private JButton insertButton;

    private JButton directionButton;
    private JButton currencyButton;

    private JLabel comaLabel = new JLabel(",");

    public NewTransactionPanel()
    {
        addFormPanel();
    }

    public void addFormPanel()
    {
        JPanel formPanel = new JPanel(new FlowLayout());
        
        formPanel.setPreferredSize(new Dimension(1000, 370));
        formPanel.setOpaque(false);

        addDirectionButton(formPanel);
        addAmountField(formPanel);
        addCurrencyButton(formPanel);
        formPanel.add(new BlankPanel(new Dimension(1200,30)));
        addLabelsPanel(formPanel);
        addDescriptionField(formPanel);
        addInsertButton(formPanel);
        

        this.add(formPanel, BorderLayout.SOUTH);
    }

    private void addDirectionButton(JPanel panel)
    {
        directionButton = UiUtil.createStyledButton("-");
        directionButton.setFont(new Font("Courier New", Font.BOLD, 50));
        panel.add(directionButton);
    }

    private void addAmountField(JPanel panel)
    {

        DecimalFormat integerFormat = new DecimalFormat("#,###");
        integerFormat.setRoundingMode(RoundingMode.DOWN);

        DecimalFormat decimalFormat = new DecimalFormat("##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);


        NumberFormatter integerFormatter = new NumberFormatter(integerFormat);
        integerFormatter.setValueClass(Integer.class);
        integerFormatter.setMinimum(0);
        integerFormatter.setMaximum(999999999);
        integerFormatter.setAllowsInvalid(false);

        NumberFormatter decimalFormatter = new NumberFormatter(decimalFormat);
        decimalFormatter.setValueClass(Integer.class);
        decimalFormatter.setMinimum(0);
        decimalFormatter.setMaximum(99);
        decimalFormatter.setAllowsInvalid(false);

        amountField = new JFormattedTextField(integerFormatter);
        amountField.setPreferredSize(new Dimension(700, 100));
        amountField.setFont(new Font("Arial", Font.BOLD, 80));
        amountField.setHorizontalAlignment(JTextField.CENTER);

        decimalAmountField = new JFormattedTextField(decimalFormatter);
        decimalAmountField.setPreferredSize(new Dimension(200, 100));
        decimalAmountField.setFont(new Font("Arial", Font.BOLD, 80));
        decimalAmountField.setHorizontalAlignment(JTextField.CENTER);

        UiUtil.styleComponent(amountField);
        UiUtil.styleComponent(decimalAmountField);

        panel.add(amountField);
        panel.add(createComaPanel());
        panel.add(decimalAmountField);
    }

    private void addCurrencyButton(JPanel panel)
    {
        currencyButton = UiUtil.createStyledButton("€");
        currencyButton.setFont(new Font("Arial", Font.BOLD, 50));
        panel.add(currencyButton);
        }

    private void addLabelsPanel(JPanel panel)  
    {  
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));  
        labelPanel.setPreferredSize(new Dimension(975, 50));  
        labelPanel.setOpaque(false);  
    
        JLabel descriptionLabel = new JLabel("Description");  
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 30));  
        descriptionLabel.setForeground(Color.WHITE);  
        descriptionLabel.setPreferredSize(new Dimension(200, 50));  
    
        messageLabel = new JLabel();  
        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));  
        messageLabel.setPreferredSize(new Dimension(600, 50));  
    
        labelPanel.add(descriptionLabel);  
        labelPanel.add(new BlankPanel(new Dimension(100, 50)));
        labelPanel.add(messageLabel);  
    
        panel.add(labelPanel);  
    }

    private void addDescriptionField(JPanel panel)
    {
        descriptionField = new JTextField(40);
        UiUtil.styleComponent(descriptionField);

        descriptionField.setPreferredSize(new Dimension(900, 50));


        descriptionField.setFont(new Font("Arial", Font.PLAIN, 28));
        descriptionField.setHorizontalAlignment(JTextField.LEFT);
        UiUtil.styleComponent(descriptionField);

        JPanel descriptionFieldPanel = new JPanel(new FlowLayout());
        descriptionFieldPanel.setPreferredSize(new Dimension(1000, 70));
        descriptionFieldPanel.setOpaque(false);
        descriptionFieldPanel.add(descriptionField);

        panel.add(descriptionFieldPanel);
    }

    private void addInsertButton(JPanel panel)
    {
        insertButton = UiUtil.createStyledButton("Insert");
        insertButton.setFont(new Font("Arial", Font.BOLD, 30));
        insertButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        insertButton.setOpaque(true);
        insertButton.setContentAreaFilled(true);
        insertButton.setPreferredSize(new Dimension(966, 50)); 

        JPanel insertButtonPanel = new JPanel(new FlowLayout());
        insertButtonPanel.setPreferredSize(new Dimension(1000, 70));
        insertButtonPanel.setOpaque(false);
        
        insertButtonPanel.add(insertButton);

        panel.add(insertButtonPanel);
    }

    private JPanel createComaPanel()
    {
        JPanel comaPanel = new JPanel();
        comaPanel.setPreferredSize(new Dimension(55, 100));
        comaPanel.setOpaque(false);
        
        comaLabel.setFont(new Font("Arial", Font.BOLD, 80));
        comaLabel.setForeground(Color.WHITE);
        comaPanel.add(comaLabel);
        return comaPanel;
    }

    public void resetAmountField()
    {
        amountField.setValue(null);   
    }

    public void resetDecimalAmountField()
    {
        decimalAmountField.setValue(null);
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

    public JFormattedTextField getDecimalField() { return decimalAmountField; }

    public JTextField getDescriptionField() { return descriptionField; }

    public JButton getInsertButton() { return insertButton; }

    public JLabel getMessageLabel() { return messageLabel; }

    public JButton getDirectionButton() { return directionButton; }

    public JButton getCurrencyButton() { return currencyButton; }

    public JLabel getComJLabel() { return comaLabel; }

    public BigDecimal getAmountValue() throws Exception
    {
        String integerPart = amountField.getText().replace(".", "");
        String decimalPart = decimalAmountField.getText();

        if (decimalPart.isEmpty()) 
            decimalPart = "00";
        

        String amountString = integerPart + "." + decimalPart;

        BigDecimal amount = new BigDecimal(amountString);

        if (amount.equals(BigDecimal.ZERO)) {
            throw new Exception("The amount can't be zero");
        }

        return amount;
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