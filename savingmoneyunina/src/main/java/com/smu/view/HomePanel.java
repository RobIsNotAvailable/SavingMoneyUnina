package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.smu.view.UiUtil.*;

public class HomePanel extends JPanel
{
    public static int cardIndex = 0;

    private Navbar navbar;
    private JButton cardButton;
    private JButton homeButton;
    private JButton familyButton;
    private JButton transactionButton;
    private JButton newTransactionButton;
    private JButton reporButton;

    private TriangleButton leftTriangleButton;
    private TriangleButton rightTriangleButton;

    private JLabel incomeLabel = UiUtil.createStyledLabel("");
    private JLabel expensesLabel = UiUtil.createStyledLabel("");
    private JLabel balanceLabel = UiUtil.createStyledLabel("");

    public Navbar getNavbar()
    {
        return navbar;
    }

    public JButton getCardButton()
    {
        return cardButton;
    }

    public JButton getHomeButton()
    {
        return homeButton;
    }

    public JButton getFamilyButton()
    {
        return familyButton;
    }

    public JButton getTransactionButton()
    {
        return transactionButton;
    }

    public JButton getNewTransactionButton()
    {
        return newTransactionButton;
    }

    public JButton getReporButton()
    {
        return reporButton;
    }

    public TriangleButton getLeftTriangleButton()
    {
        return leftTriangleButton;
    }

    public TriangleButton getRightTriangleButton()
    {
        return rightTriangleButton;
    }

    public JLabel getIncomeLabel()
    {
        return incomeLabel;
    }

    public JLabel getExpensesLabel()
    {
        return expensesLabel;
    }

    public JLabel getBalanceLabel()
    {
        return balanceLabel;
    }

    public HomePanel()
    {

        cardButton = UiUtil.createStyledButton(null);

        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        navbar = new Navbar();
        this.add(navbar, BorderLayout.NORTH);

        // Wrapper panel for spacing and buttons
        JPanel buttonContainer = new JPanel(new BorderLayout());
        buttonContainer.setOpaque(false);

        // Panel for buttons and finance labels
        JPanel buttonWrapper = new JPanel(new BorderLayout());
        buttonWrapper.setOpaque(false);

        // Add spacing between navbar and card
        buttonWrapper.add(new BlankPanel(new Dimension(1, 100)), BorderLayout.NORTH);
        leftTriangleButton = new TriangleButton(TriangleButton.Direction.LEFT);
        rightTriangleButton = new TriangleButton(TriangleButton.Direction.RIGHT);

        leftTriangleButton.setPreferredSize(new Dimension(80, 80));
        rightTriangleButton.setPreferredSize(new Dimension(80, 80));

        cardButton.setPreferredSize(new Dimension(400, 200));
        cardButton.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setOpaque(false);
        cardPanel.add(cardButton, BorderLayout.CENTER);

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

    public void updateDetails(BigDecimal income, BigDecimal expense, BigDecimal balance)
    {
        incomeLabel.setText("Income: " + income + "€");
        expensesLabel.setText("Expense: " + expense + "€");
        balanceLabel.setText("Balance: " + balance + "€");
    }


}
