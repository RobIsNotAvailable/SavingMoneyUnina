package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.smu.model.Category;
import com.smu.model.Transaction;
import com.smu.view.UiUtil.*;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

public class HomePanel extends JPanel
{
    private Navbar navbar;

    private JButton cardButton;
    private TriangleButton leftTriangleButton;
    private TriangleButton rightTriangleButton;

    private JFrame cardDetailsFrame;

    private JLabel incomeLabel = UiUtil.createStyledLabel("");
    private JLabel expensesLabel = UiUtil.createStyledLabel("");
    private JLabel balanceLabel = UiUtil.createStyledLabel("");
    
    private JFormattedTextField filterInitialDate;
    private JFormattedTextField filterFinalDate;
    private JComboBox<String> filterDirection;
    private JComboBox<Category> filterCategory;
    private JButton filterButton;
    private JLabel filterErrorLabel;

    private JTable transactions = null;

    public HomePanel()
    {
        cardButton = UiUtil.createStyledButton("");
        cardButton.setMargin(new Insets(30, 0, -20, 0));
        cardButton.setPreferredSize(new Dimension(450, 330));
        
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        navbar = new Navbar();
        this.add(navbar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(new BlankPanel(new Dimension(1,65)),BorderLayout.NORTH);

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

        addTablePanel();
    }

    public Navbar getNavbar() { return navbar; }

    public JButton getCardButton() { return cardButton; }

    public TriangleButton getLeftTriangleButton() { return leftTriangleButton; }

    public TriangleButton getRightTriangleButton() { return rightTriangleButton; }

    public JLabel getIncomeLabel() { return incomeLabel; }

    public JLabel getExpensesLabel() { return expensesLabel; }

    public JLabel getBalanceLabel() { return balanceLabel; }

    public JFormattedTextField getFilterInitialDate() { return filterInitialDate; }

    public JFormattedTextField getFilterFinalDate() { return filterFinalDate; }

    public JComboBox<String> getFilterDirection() { return filterDirection; }

    public JComboBox<Category> getFilterCategory() { return filterCategory; }

    public JButton getFilterButton() { return filterButton; }

    private void addTablePanel()
    {
        transactions = new TransparentTable(new Object[0][4], new String[] {"", "", "", ""});

        JPanel tablePanel = new JPanel(new BorderLayout());
        
        tablePanel.setPreferredSize(new Dimension(400, 300));
        tablePanel.setOpaque(false);

        tablePanel.add(new TransparentScrollPanel(transactions,400,200), BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 200));
        
        tablePanel.add(new BlankPanel(new Dimension(1, 50)), BorderLayout.SOUTH);
        tablePanel.add(new BlankPanel(new Dimension(200, 1)), BorderLayout.WEST);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setOpaque(false);

        try
        {
            MaskFormatter formatter = new MaskFormatter("##/##/####");
            formatter.setPlaceholderCharacter('-');
            filterInitialDate = new JFormattedTextField(formatter);
            filterFinalDate = new JFormattedTextField(formatter);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        filterPanel.add(new BlankPanel(new Dimension(185, 1)));

        filterInitialDate.setFont(new Font("Arial", Font.PLAIN, 19));
        filterInitialDate.setColumns(6);
        filterPanel.add(new JLabel("From:"));
        filterPanel.add(filterInitialDate);

        filterFinalDate.setFont(new Font("Arial", Font.PLAIN, 19));
        filterFinalDate.setColumns(6);
        filterPanel.add(new JLabel("To:"));
        filterPanel.add(filterFinalDate);

        filterPanel.add(new BlankPanel(new Dimension(40, 1)));

        filterDirection = new JComboBox<>();
        filterPanel.add(filterDirection);

        filterCategory = new JComboBox<>();
        filterPanel.add(filterCategory);

        filterPanel.add(new BlankPanel(new Dimension(35, 1)));

        filterButton = UiUtil.createStyledButton("Filter");
        filterButton.setContentAreaFilled(true);
        filterButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        filterButton.setMargin(new Insets(3, 35, 3, 35));
        filterPanel.add(filterButton);

        filterErrorLabel = UiUtil.createStyledLabel("placeholder error text");
        filterPanel.add(filterErrorLabel);

        tablePanel.add(filterPanel, BorderLayout.NORTH);

        this.add(tablePanel, BorderLayout.SOUTH);
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
        cardDetailsFrame.getContentPane().setBackground(UiUtil.BACKGROUND_GRAY);

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

    public void showTransactions(List<Transaction> transactionList)
    {
        Object[][] data = new Object[(transactionList != null) ? transactionList.size() : 0][4];

        for (int i = 0; i < transactionList.size(); i++)
        {
            Transaction transaction = transactionList.get(i);
            data[i][0] = transaction.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            data[i][1] = transaction.getDescription();

            String categories = "";
            for (Category c : transaction.getCategories())
            {
                categories += c;
            }
            

            data[i][2] = categories;

            Color color = (transaction.getDirection() == Transaction.Direction.INCOME) ? UiUtil.CAPPUCCINO : Color.WHITE;
            
            String hexColor = String.format("#%06X", (0xFFFFFF & color.getRGB()));
            String sign = (transaction.getDirection() == Transaction.Direction.INCOME) ? "+ " : "- ";
            data[i][3] = String.format("<html><font color='%s'>%s%.2f €    </font></html>", hexColor, sign, transaction.getAmount());
        }
        
        transactions.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {"", "", "", ""}));

        int columnWidth = 180;
        transactions.getColumnModel().getColumn(0).setMinWidth(columnWidth);
        transactions.getColumnModel().getColumn(0).setMaxWidth(columnWidth);

        transactions.getColumnModel().getColumn(2).setMinWidth(columnWidth);
        transactions.getColumnModel().getColumn(2).setMaxWidth(columnWidth);

        transactions.getColumnModel().getColumn(3).setMinWidth(columnWidth);
        transactions.getColumnModel().getColumn(3).setMaxWidth(columnWidth);

        DefaultTableCellRenderer rightAlignRenderer = new DefaultTableCellRenderer();
        rightAlignRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        transactions.getColumnModel().getColumn(3).setCellRenderer(rightAlignRenderer);
    }
}
