package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.smu.view.UiUtil.BlankPanel;
import com.smu.view.UiUtil.TriangleButton;

public class CardManager extends JPanel
{
    private JFrame cardDetailsFrame;

    private JButton cardButton;
    private TriangleButton leftTriangleButton;
    private TriangleButton rightTriangleButton;
    
    private JLabel incomeLabel = UiUtil.createStyledLabel("0.00");
    private JLabel expensesLabel = UiUtil.createStyledLabel("0.00");
    private JLabel balanceLabel = UiUtil.createStyledLabel("0.00");
    
    public CardManager()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);

        this.add(Box.createVerticalGlue());

        cardButton = UiUtil.createStyledButton("");
        cardButton.setMargin(new Insets(30, 0, -20, 0));
        cardButton.setPreferredSize(new Dimension(450, 330));
        cardButton.setOpaque(false);

        leftTriangleButton = new TriangleButton(TriangleButton.Direction.LEFT);
        rightTriangleButton = new TriangleButton(TriangleButton.Direction.RIGHT);

        leftTriangleButton.setPreferredSize(new Dimension(80, 80));
        rightTriangleButton.setPreferredSize(new Dimension(80, 80));
        JPanel leftButtonPanel = new JPanel(new BorderLayout());
        leftButtonPanel.setOpaque(false);
        leftButtonPanel.add(new BlankPanel(new Dimension(1,20)), BorderLayout.CENTER);
        leftButtonPanel.add(leftTriangleButton, BorderLayout.SOUTH);
        leftButtonPanel.setPreferredSize(new Dimension(80,130));

        JPanel rightButtonPanel = new JPanel(new BorderLayout());
        rightButtonPanel.setOpaque(false);
        rightButtonPanel.add(new BlankPanel(new Dimension(1,20)), BorderLayout.CENTER);
        rightButtonPanel.add(rightTriangleButton, BorderLayout.SOUTH);
        rightButtonPanel.setPreferredSize(new Dimension(80,130));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, -50));
        buttonRow.setOpaque(false);

        buttonRow.add(leftButtonPanel);
        buttonRow.add(cardButton);
        buttonRow.add(rightButtonPanel);

        buttonRow.setPreferredSize(new Dimension(800, 280));
        buttonRow.setMinimumSize(buttonRow.getPreferredSize());
        buttonRow.setMaximumSize(buttonRow.getPreferredSize());

        JPanel financePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        financePanel.setOpaque(false);
        financePanel.setPreferredSize(new Dimension(530, 40));

        financePanel.add(incomeLabel);
        financePanel.add(expensesLabel);
        financePanel.add(balanceLabel);

        financePanel.setPreferredSize(new Dimension(800, 70));
        financePanel.setMinimumSize(financePanel.getPreferredSize());
        financePanel.setMaximumSize(financePanel.getPreferredSize());

        this.add(buttonRow);
        this.add(Box.createVerticalStrut(10));
        this.add(financePanel);
        this.add(new BlankPanel(new Dimension(1,50)));
    }

    public void updateDetails(BigDecimal income, BigDecimal expense, BigDecimal balance)
    {
        incomeLabel.setText(String.format("<html><font color='white'>Income: </font><font color='%s'>%.2f€</font></html>", UiUtil.CAPPUCCINO_RGB, income));
        expensesLabel.setText(String.format("<html><font color='white'>Expense: </font><font color='%s'>%.2f€</font></html>", UiUtil.WHITE_RGB, expense));
        balanceLabel.setText(String.format("<html><font color='white'>Balance: </font><font color='%s'>%.2f€</font></html>", UiUtil.CAPPUCCINO_RGB, balance));
    }

    public void displayCardDetails(String cardNumber, String pin, String expirationDate, String cvv)
    {
        if (cardDetailsFrame != null && cardDetailsFrame.isDisplayable()) 
            cardDetailsFrame.dispose();

        cardDetailsFrame = new JFrame();
        cardDetailsFrame.setTitle("Card Details");
        cardDetailsFrame.setIconImage(new ImageIcon(HomePanel.class.getResource("/logo.png")).getImage());
        cardDetailsFrame.getContentPane().setBackground(UiUtil.BACKGROUND_BLACK);

        cardDetailsFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        detailsPanel.setOpaque(false);
                    
        JPanel coloredLine = new JPanel();
        coloredLine.setBackground(UiUtil.CAPPUCCINO);
        coloredLine.setPreferredSize(new Dimension(30, 5));

        JLabel titleLabel = UiUtil.createStyledLabel("Card Details");
        JLabel numberLabel = UiUtil.createStyledLabel("Number: " + cardNumber); 
        JLabel pinLabel = UiUtil.createStyledLabel("Pin: " + pin); 
        JLabel expiryLabel = UiUtil.createStyledLabel("Valid thru: " + expirationDate); 
        JLabel cvvLabel = UiUtil.createStyledLabel("CVV: " + cvv);

        detailsPanel.add(titleLabel);
        detailsPanel.add(new BlankPanel(new Dimension(30, 10)));
        detailsPanel.add(coloredLine);
        detailsPanel.add(new BlankPanel(new Dimension(30, 10)));
        detailsPanel.add(numberLabel);
        detailsPanel.add(pinLabel);
        detailsPanel.add(cvvLabel);
        detailsPanel.add(expiryLabel);

        cardDetailsFrame.getContentPane().add(detailsPanel, BorderLayout.CENTER);
        cardDetailsFrame.pack();
        cardDetailsFrame.setLocationRelativeTo(null); 
        cardDetailsFrame.setVisible(true);
        cardDetailsFrame.setResizable(false);
    }

    public JButton getCardButton() { return cardButton; }

    public TriangleButton getLeftTriangleButton() { return leftTriangleButton; }

    public TriangleButton getRightTriangleButton() { return rightTriangleButton; }
    
    public JLabel getIncomeLabel() { return incomeLabel; }

    public JLabel getExpensesLabel() { return expensesLabel; }

    public JLabel getBalanceLabel() { return balanceLabel; }

}
