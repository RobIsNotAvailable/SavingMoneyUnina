package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.smu.controller.HomeController;
import com.smu.model.PaymentCard;
import com.smu.model.Transaction;
import com.smu.view.UiUtil.*;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class HomePanel extends JPanel
{
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
    
    private JTable transactions = null;

    public HomePanel(List<PaymentCard> cards)
    {
        cardButton = new JButton();

        cardButton.setContentAreaFilled(false); 
        cardButton.setBorderPainted(false);     
        cardButton.setOpaque(false);
        cardButton.setFocusPainted(false);           

        cardButton.setPreferredSize(new Dimension(449, 280));//374, 233
        cardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        navbar = new Navbar();
        this.add(navbar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(new BlankPanel(new Dimension(1,150)),BorderLayout.NORTH);

        JPanel financePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        financePanel.setOpaque(false);

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

        centerPanel.add(buttonRow, BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);

        createTable(cards.get(HomeController.cardIndex).getTransactions());
    }

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

    public void updateDetails(BigDecimal income, BigDecimal expense, BigDecimal balance)
    {
        String incomeColor = "rgb(" + UiUtil.CAPPUCCINO.getRed() + ", " + UiUtil.CAPPUCCINO.getGreen() + ", " + UiUtil.CAPPUCCINO.getBlue() + ")";
        String expenseColor = "rgb(255,255, 255)";
        
        incomeLabel.setText("<html><font color='white'>Income: </font><font color='" + incomeColor + "'>" + income + "€</font></html>");
        expensesLabel.setText("<html><font color='white'>Expense: </font><font color='" + expenseColor + "'>" + expense + "€</font></html>");
        balanceLabel.setText("<html><font color='white'>Balance: </font><font color='" + incomeColor + "'>" + balance + "€</font></html>");
    }

    public void createTable(List<Transaction> transactionList)  
    {  
        if (transactions != null)    
            this.remove(transactions.getParent().getParent().getParent());

        Object[][] data = new Object[(transactionList != null) ? transactionList.size() : 0][3];  

        for (int i = 0; i < transactionList.size(); i++)  
        {  
            Transaction transaction = transactionList.get(i);  
            data[i][0] = transaction.getDate();  
            data[i][1] = transaction.getDescription();  

            Color color = (transaction.getDirection() == Transaction.Direction.INCOME) ? UiUtil.CAPPUCCINO : Color.WHITE;  
            
            String hexColor = String.format("#%06X", (0xFFFFFF & color.getRGB()));  
            String sign = (transaction.getDirection() == Transaction.Direction.INCOME) ? "+ " : "- ";
            data[i][2] = String.format("<html><font color='%s'>%s%.2f€   </font></html>", hexColor, sign, transaction.getAmount());  
        }  
        
        transactions = new TransparentTable(data, new String[] {"", "", ""});

        int columnWidth = 180; 
        transactions.getColumnModel().getColumn(0).setMinWidth(columnWidth);
        transactions.getColumnModel().getColumn(0).setMaxWidth(columnWidth);

        transactions.getColumnModel().getColumn(2).setMinWidth(columnWidth);
        transactions.getColumnModel().getColumn(2).setMaxWidth(columnWidth);

        DefaultTableCellRenderer rightAlignRenderer = new DefaultTableCellRenderer();
        rightAlignRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        transactions.getColumnModel().getColumn(2).setCellRenderer(rightAlignRenderer);

        JPanel tablePanel = new JPanel(new BorderLayout());  
        
        tablePanel.setPreferredSize(new Dimension(400, 300));  
        tablePanel.setOpaque(false); 

        tablePanel.add(new TransparentScrollPanel(transactions,400,200), BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 200)); 
        
        tablePanel.add(new BlankPanel(new Dimension(1, 50)), BorderLayout.SOUTH);  
        tablePanel.add(new BlankPanel(new Dimension(200, 1)), BorderLayout.WEST);

        this.add(tablePanel, BorderLayout.SOUTH);  
    }
}

