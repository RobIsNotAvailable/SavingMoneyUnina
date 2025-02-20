package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.smu.model.PaymentCard;
import com.smu.model.Transaction;
import com.smu.view.UiUtil.*;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

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
    
    private JTable transactions = null;

    public HomePanel(List<PaymentCard> cards)
    {
        cardButton = new JButton();

        cardButton.setContentAreaFilled(false); 
        cardButton.setBorderPainted(false);     
        cardButton.setOpaque(false);
        cardButton.setFocusPainted(false);           

        cardButton.setPreferredSize(new Dimension(449, 280));//374, 233
        
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

        createTable(cards.get(cardIndex).getTransactions());
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
        String green = String.format("rgb(%d, %d, %d)", UiUtil.SUCCESS_GREEN.getRed(), UiUtil.SUCCESS_GREEN.getGreen(), UiUtil.SUCCESS_GREEN.getBlue());
        String red = String.format("rgb(%d, %d, %d)", UiUtil.ERROR_RED.getRed(), UiUtil.ERROR_RED.getGreen(), UiUtil.ERROR_RED.getBlue());
        
        incomeLabel.setText("<html><font color='white'>Income: </font><font color='" + green + "'>" + income + "€</font></html>");
        expensesLabel.setText("<html><font color='white'>Expense: </font><font color='" + red + "'>" + expense + "€</font></html>");
        balanceLabel.setText("<html><font color='white'>Balance: </font><font color='" + green + "'>" + balance + "€</font></html>");
    }

    public void createTable(List<Transaction> transactionList)  
    {  
        Object[][] data = new Object[(transactionList != null) ? 5 : 0][3];  

        if (transactionList != null)  
        {  
            for (int i = 0; i < 5; i++)  
            {  
                Transaction transaction = transactionList.get(i);  
                data[i][0] = transaction.getDate();  
                data[i][1] = transaction.getDescription();  

                Color color = (transaction.getDirection() == Transaction.Direction.INCOME) ? UiUtil.SUCCESS_GREEN : UiUtil.ERROR_RED;  
                String hexColor = String.format("#%06X", (0xFFFFFF & color.getRGB()));  
                data[i][2] = "<html><font color='" + hexColor + "'>" + transaction.getAmount() + "€</font></html>";  
            }  
        }  

        if (transactions != null)    
            this.remove(transactions.getParent());  

        transactions = new JTable(data, new String[] {"", "", ""})  
        {  
            @Override  
            public Class<?> getColumnClass(int column)  
            {  
                return String.class;  
            }  
        };  

        int columnWidth = 180; 
        transactions.getColumnModel().getColumn(0).setPreferredWidth(columnWidth); // Adjust width as needed 
        transactions.getColumnModel().getColumn(0).setMinWidth(columnWidth);       
        transactions.getColumnModel().getColumn(0).setMaxWidth(columnWidth);  


        transactions.setPreferredScrollableViewportSize(new Dimension(700, 70));  
        transactions.setFillsViewportHeight(true);  
        transactions.setOpaque(false);  
        transactions.setBackground(new Color(0, 0, 0, 0));  
        transactions.setForeground(Color.WHITE);  
        transactions.setFont(transactions.getFont().deriveFont(16f));  
        transactions.setRowHeight(30);  
        transactions.setShowGrid(false); 


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();  
        centerRenderer.setHorizontalAlignment(JLabel.LEFT);  
        for (int i = 0; i < transactions.getColumnCount(); i++)  
        {  
            transactions.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);  
        }  


        JPanel tablePanel = new JPanel(new BorderLayout());  
        tablePanel.setOpaque(false);  
        tablePanel.add(transactions, BorderLayout.CENTER);  
        tablePanel.add(new BlankPanel(new Dimension(1, 100)), BorderLayout.SOUTH);  
        tablePanel.add(new BlankPanel(new Dimension(400, 1)), BorderLayout.WEST);  

        this.add(tablePanel, BorderLayout.SOUTH);  
    }  
}

