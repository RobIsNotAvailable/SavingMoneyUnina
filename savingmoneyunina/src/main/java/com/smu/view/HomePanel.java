package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import com.smu.model.Category;
import com.smu.model.Transaction;
import com.smu.model.Transaction.Direction;
import com.smu.view.UiUtil.*;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

public class HomePanel extends DefaultPanel
{
    private JFormattedTextField filterInitialDate;
    private JFormattedTextField filterFinalDate;
    private JComboBox<String> filterDirection;
    private JComboBox<Category> filterCategory;
    private JButton filterButton;
    private JButton clearFilterButton;
    private JButton allTransactionsButton;

    private JTable transactions;

    public HomePanel()
    {   
        addTablePanel();
    }

    private void addTablePanel()
    {
        transactions = new TransparentTable(new Object[0][4], new String[] {"", "", "", ""});

        JPanel tablePanel = new JPanel(new BorderLayout());
        
        tablePanel.setPreferredSize(new Dimension(400, 350));
        tablePanel.setOpaque(false);

        tablePanel.add(new TransparentScrollPanel(transactions,400,200), BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 200));
        
        allTransactionsButton = UiUtil.createStyledButton("Load all");
        allTransactionsButton.setForeground(UiUtil.CAPPUCCINO);

        JPanel allTransactionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        allTransactionsPanel.setOpaque(false);
        allTransactionsPanel.add(new BlankPanel(new Dimension(190, 1)), FlowLayout.LEFT);
        allTransactionsPanel.add(allTransactionsButton);
        
        tablePanel.add(allTransactionsPanel, BorderLayout.SOUTH);
        tablePanel.add(new BlankPanel(new Dimension(200, 1)), BorderLayout.WEST);

        tablePanel.add(createFilterPanel(), BorderLayout.NORTH);

        this.add(tablePanel, BorderLayout.SOUTH);
    }

    private JPanel createFilterPanel() 
    {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 13, 10));
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

        filterInitialDate.setFont(new Font("Arial", Font.PLAIN, 18));
        UiUtil.styleComponent(filterInitialDate);
        filterInitialDate.setColumns(8);
        filterPanel.add(UiUtil.createStyledLabel("From:"));
        filterPanel.add(filterInitialDate);

        filterFinalDate.setFont(new Font("Arial", Font.PLAIN, 18));
        UiUtil.styleComponent(filterFinalDate);
        filterFinalDate.setColumns(8);
        filterPanel.add(UiUtil.createStyledLabel("To:"));
        filterPanel.add(filterFinalDate);

        filterPanel.add(new BlankPanel(new Dimension(30, 1)));

        filterDirection = new JComboBox<>();
        UiUtil.styleComponent(filterDirection);
        filterDirection.setFocusable(false);
        filterDirection.setFont(new Font("Arial", Font.PLAIN, 18));
        filterPanel.add(filterDirection);

        filterCategory = new JComboBox<>();
        UiUtil.styleComponent(filterCategory);
        filterCategory.setFocusable(false);
        filterCategory.setFont(new Font("Arial", Font.PLAIN, 18));
        filterPanel.add(filterCategory);

        filterPanel.add(new BlankPanel(new Dimension(35, 1)));

        filterButton = UiUtil.createStyledButton("Filter");
        filterButton.setContentAreaFilled(true);
        filterButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        filterButton.setMargin(new Insets(3, 35, 3, 35));
        UiUtil.addKeyBinding(filterButton, "ENTER");
        filterPanel.add(filterButton);

        clearFilterButton = UiUtil.createStyledButton("Reset");
        clearFilterButton.setContentAreaFilled(true);
        clearFilterButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        clearFilterButton.setMargin(new Insets(3, 35, 3, 35));
        UiUtil.addKeyBinding(clearFilterButton, "DELETE");
        filterPanel.add(clearFilterButton);

        filterPanel.add(new BlankPanel(new Dimension(30, 1)));

        messageLabel = UiUtil.createStyledLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        messageLabel.setForeground(UiUtil.ERROR_RED);
        filterPanel.add(messageLabel);
        return filterPanel;
    }
    
    public void showTransactions(List<Transaction> transactionList)
    {
        Object[][] data;

        if(transactionList.isEmpty())
        {
            data = new Object[1][1];
            data[0][0] = "No transactions found :(";
            transactions.setModel(new javax.swing.table.DefaultTableModel(data, new String[] {""}));

            DefaultTableCellRenderer centerAlignRenderer = new DefaultTableCellRenderer();
            centerAlignRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            transactions.getColumnModel().getColumn(0).setCellRenderer(centerAlignRenderer);

            return;
        }
        
        
        data = new Object[transactionList.size()][4];

        for (int i = 0; i < transactionList.size(); i++)
        {
            Transaction transaction = transactionList.get(i);
            data[i][0] = transaction.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            data[i][1] = transaction.getDescription();

            String categories = "";
            for (Category c : transaction.getCategories())
            {
                categories += c + " ";
            }
            
            data[i][2] = categories;

            String colorRGB = (transaction.getDirection() == Transaction.Direction.INCOME) ? UiUtil.CAPPUCCINO_RGB : "rgb(255, 255, 255)";
            String sign = (transaction.getDirection() == Transaction.Direction.INCOME) ? "+ " : "- ";
            data[i][3] = String.format("<html><font color='%s'>%s%.2f €    </font></html>", colorRGB, sign, transaction.getAmount());
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
    
    /***********************************************************GETTERS****************************************************** */
    
    public JFormattedTextField getFilterInitialDate() { return filterInitialDate; }

    public JFormattedTextField getFilterFinalDate() { return filterFinalDate; }

    public JComboBox<String> getFilterDirection() { return filterDirection; }

    public JComboBox<Category> getFilterCategory() { return filterCategory; }

    public JButton getFilterButton() { return filterButton; }

    public JButton getClearFilterButton() { return clearFilterButton; }

    public JButton getAllTransactionsButton() {return allTransactionsButton; }

    public LocalDate getInitialDateValue() throws Exception 
    {
        String text = filterInitialDate.getText();

        if (text.equals("--/--/----")) 
            return null;

        return LocalDate.parse(text, DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT));
    }

    public LocalDate getFinalDateValue() throws Exception 
    {
        String text = filterFinalDate.getText();
        
        if (text.equals("--/--/----")) 
            return null;
        
        return LocalDate.parse(text, DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT));
    }

    public Direction getFilterDirectionValue() 
    {
        String direction = (String) filterDirection.getSelectedItem();
        if (direction.equals("All directions")) 
            return null;
        else 
            return Direction.valueOf(direction.toUpperCase());
    }

    public Category getFilterCategoryValue() 
    {
        Category category = (Category) filterCategory.getSelectedItem();
        if (category.getName().equals("All categories")) 
            return null;
        else 
            return category;
    }
}
