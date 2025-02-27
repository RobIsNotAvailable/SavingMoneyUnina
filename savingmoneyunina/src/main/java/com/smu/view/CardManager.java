package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
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
    
    private JLabel incomeLabel = UiUtil.createStyledLabel(" ");
    private JLabel expensesLabel = UiUtil.createStyledLabel(" ");
    private JLabel balanceLabel = UiUtil.createStyledLabel(" ");
    
    public CardManager()
    {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        add(new BlankPanel(new Dimension(1,150)),BorderLayout.NORTH);
        
        cardButton = UiUtil.createStyledButton("");
        cardButton.setMargin(new Insets(30, 0, -20, 0));
        cardButton.setPreferredSize(new Dimension(450, 330));
        cardButton.setOpaque(true);

        JPanel financePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        financePanel.setOpaque(false);
        financePanel.setPreferredSize(new Dimension(530, 40));

        financePanel.add(new BlankPanel(new Dimension(1,50)),BorderLayout.NORTH);
        financePanel.add(incomeLabel);
        financePanel.add(expensesLabel);
        financePanel.add(balanceLabel);

        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setOpaque(false);
        cardPanel.add(cardButton, BorderLayout.CENTER);
        cardPanel.add(financePanel, BorderLayout.SOUTH);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, -50));
        buttonRow.setOpaque(false);

        leftTriangleButton = new TriangleButton(TriangleButton.Direction.LEFT);
        rightTriangleButton = new TriangleButton(TriangleButton.Direction.RIGHT);

        leftTriangleButton.setPreferredSize(new Dimension(80, 80));
        rightTriangleButton.setPreferredSize(new Dimension(80, 80));

        buttonRow.add(leftTriangleButton);
        buttonRow.add(cardPanel);
        buttonRow.add(rightTriangleButton);

        add(buttonRow);
    }

    public void updateDetails(BigDecimal income, BigDecimal expense, BigDecimal balance)
    {
        String incomeColor = "rgb(" + UiUtil.CAPPUCCINO.getRed() + ", " + UiUtil.CAPPUCCINO.getGreen() + ", " + UiUtil.CAPPUCCINO.getBlue() + ")";
        String expenseColor = "rgb(255,255, 255)";
        
        incomeLabel.setText("<html><font color='white'>Income: </font><font color='" + incomeColor + "'>" + income + "€</font></html>");
        expensesLabel.setText("<html><font color='white'>Expense: </font><font color='" + expenseColor + "'>" + expense + "€</font></html>");
        balanceLabel.setText("<html><font color='white'>Balance: </font><font color='" + incomeColor + "'>" + balance + "€</font></html>");
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
