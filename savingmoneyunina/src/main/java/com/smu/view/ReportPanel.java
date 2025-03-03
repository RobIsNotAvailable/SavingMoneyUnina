package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import com.smu.model.ExpenseDetails;
import com.smu.model.IncomeDetails;
import com.smu.model.Report;
import com.smu.view.UiUtil.*;


public class ReportPanel extends DefaultPanel
{
    private JFormattedTextField dateField;
    private JButton showButton;
    private JPanel dataPanel;

    private JLabel startingBalanceLabel;
    private JLabel finalBalanceLabel;
    private JLabel balanceDifferenceLabel;

    private JLabel minIncomeLabel;
    private JLabel maxIncomeLabel;
    private JLabel avgIncomeLabel;

    private JLabel minExpenseLabel;
    private JLabel maxExpenseLabel;
    private JLabel avgExpenseLabel;


    public ReportPanel()
    {
        this.add(cardManager,BorderLayout.WEST);


        dataPanel = new JPanel(new FlowLayout());
        dataPanel.setOpaque(false);
        dataPanel.setPreferredSize(new Dimension(900,1000));

        try
        {
            MaskFormatter formatter = new MaskFormatter("##/####");
            formatter.setPlaceholderCharacter('-');
            dateField = new JFormattedTextField(formatter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        UiUtil.styleComponent(dateField);
        dateField.setFont(new Font("Arial", Font.PLAIN, 18));
        dateField.setColumns(7);
        dateField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/uuuu")));

        dataPanel.add(new BlankPanel(new Dimension(1,100)));
        dataPanel.add(UiUtil.createStyledLabel("Date: "));
        dataPanel.add(dateField);

        dataPanel.add(new BlankPanel(new Dimension(100,1)));

        showButton = UiUtil.createStyledButton("Show");
        showButton.setContentAreaFilled(true);
        showButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        showButton.setMargin(new Insets(3, 35, 3, 35));
        UiUtil.addKeyBinding(showButton, "ENTER");
        dataPanel.add(showButton);

        addBalanceLabel();
        addIncomeLabel();
        addExpenseLabel();

        this.add(dataPanel,BorderLayout.EAST);
    }

    private void addBalanceLabel()
    {
        JPanel balancePanel = new JPanel(new FlowLayout());
        balancePanel.setOpaque(false);
        balancePanel.setPreferredSize(new Dimension(900,250));

        JLabel panelName = UiUtil.createStyledLabel("Balance");
        panelName.setPreferredSize(new Dimension(200,50));
        balancePanel.add(panelName);
        balancePanel.add(new BlankPanel(new Dimension(700,1)));

        JPanel coloredLine = new JPanel();
        coloredLine.setBackground(UiUtil.CAPPUCCINO);
        coloredLine.setPreferredSize(new Dimension(850, 5));
        balancePanel.add(coloredLine);
        
        startingBalanceLabel = UiUtil.createStyledLabel(" ");
        startingBalanceLabel.setPreferredSize(new Dimension(290,50));

        finalBalanceLabel = UiUtil.createStyledLabel(" ");
        finalBalanceLabel.setPreferredSize(new Dimension(290,50));

        balanceDifferenceLabel = UiUtil.createStyledLabel(" ");
        balanceDifferenceLabel.setPreferredSize(new Dimension(290,50));

        balancePanel.add(startingBalanceLabel);
        balancePanel.add(finalBalanceLabel);
        balancePanel.add(balanceDifferenceLabel);

        dataPanel.add(balancePanel);
    }

    private void addIncomeLabel()
    {
        JPanel incomePanel = new JPanel(new FlowLayout());
        incomePanel.setOpaque(false);
        incomePanel.setPreferredSize(new Dimension(900,250));

        JLabel panelName = UiUtil.createStyledLabel("Income");
        panelName.setPreferredSize(new Dimension(200,50));
        incomePanel.add(panelName);
        incomePanel.add(new BlankPanel(new Dimension(700,1)));

        JPanel coloredLine = new JPanel();
        coloredLine.setBackground(UiUtil.CAPPUCCINO);
        coloredLine.setPreferredSize(new Dimension(850, 5));
        incomePanel.add(coloredLine);
        
        maxIncomeLabel = UiUtil.createStyledLabel(" ");
        maxIncomeLabel.setPreferredSize(new Dimension(290,50));

        minIncomeLabel = UiUtil.createStyledLabel(" ");
        minIncomeLabel.setPreferredSize(new Dimension(290,50));

        avgIncomeLabel = UiUtil.createStyledLabel(" ");
        avgIncomeLabel.setPreferredSize(new Dimension(290,50));

        incomePanel.add(maxIncomeLabel);
        incomePanel.add(minIncomeLabel);
        incomePanel.add(avgIncomeLabel);

        dataPanel.add(incomePanel);
    }
    
    private void addExpenseLabel()
    {
        JPanel expensePanel = new JPanel(new FlowLayout());
        expensePanel.setOpaque(false);
        expensePanel.setPreferredSize(new Dimension(900,250));

        JLabel panelName = UiUtil.createStyledLabel("Expense");
        panelName.setPreferredSize(new Dimension(200,50));
        expensePanel.add(panelName);
        expensePanel.add(new BlankPanel(new Dimension(700,1)));

        JPanel coloredLine = new JPanel();
        coloredLine.setBackground(UiUtil.CAPPUCCINO);
        coloredLine.setPreferredSize(new Dimension(850, 5));
        expensePanel.add(coloredLine);
        
        maxExpenseLabel = UiUtil.createStyledLabel(" ");
        maxExpenseLabel.setPreferredSize(new Dimension(290,50));

        minExpenseLabel = UiUtil.createStyledLabel(" ");
        minExpenseLabel.setPreferredSize(new Dimension(290,50));

        avgExpenseLabel = UiUtil.createStyledLabel(" ");
        avgExpenseLabel.setPreferredSize(new Dimension(290,50));

        expensePanel.add(maxExpenseLabel);
        expensePanel.add(minExpenseLabel);
        expensePanel.add(avgExpenseLabel);

        dataPanel.add(expensePanel);
    }

    public void showReport(Report report)
    {
        String incomeColor = "rgb(" + UiUtil.CAPPUCCINO.getRed() + ", " + UiUtil.CAPPUCCINO.getGreen() + ", " + UiUtil.CAPPUCCINO.getBlue() + ")";
        String expenseColor = "rgb(255,255, 255)";


        /*****************BALANCE *************************/
        BigDecimal initial = report.getInitialBalance();
        BigDecimal ending = report.getFinalBalance();
        BigDecimal difference = report.getMonthlyDifference();

        startingBalanceLabel.setText(String.format("<html><font color='white'>Initial: </font><font color='%s'>%.2f€</font></html>", incomeColor, initial));
        finalBalanceLabel.setText(String.format("<html><font color='white'>Final: </font><font color='%s'>%.2f€</font></html>", incomeColor, ending));
        balanceDifferenceLabel.setText(String.format("<html><font color='white'>Difference: </font><font color='%s'>%.2f€</font></html>", (difference.compareTo(BigDecimal.ZERO) > 0 ? incomeColor : expenseColor), difference));

        /*****************income *************************/
        IncomeDetails incomeDetails = report.getIncomeDetails();
        
        maxIncomeLabel.setText(String.format("<html><font color='white'>Max: </font><font color='%s'>%.2f€</font></html>", incomeColor, incomeDetails.getMaxIncome()));
        minIncomeLabel.setText(String.format("<html><font color='white'>Min: </font><font color='%s'>%.2f€</font></html>", incomeColor, incomeDetails.getMinIncome()));
        avgIncomeLabel.setText(String.format("<html><font color='white'>Avg: </font><font color='%s'>%.2f€</font></html>", incomeColor, incomeDetails.getAvgIncome()));

        /*****************expense *************************/
        ExpenseDetails expenseDetails = report.getExpenseDetails();

        maxExpenseLabel.setText(String.format("<html><font color='white'>Max: </font><font color='%s'>%.2f€</font></html>", expenseColor, expenseDetails.getMaxExpense()));
        minExpenseLabel.setText(String.format("<html><font color='white'>Min: </font><font color='%s'>%.2f€</font></html>", expenseColor, expenseDetails.getMinExpense()));
        avgExpenseLabel.setText(String.format("<html><font color='white'>Avg: </font><font color='%s'>%.2f€</font></html>", expenseColor, expenseDetails.getAvgExpense()));
    }
    
    public LocalDate getDateValue() throws Exception 
    {
        String text = dateField.getText();

        if (text.equals("--/----")) 
            return null;
        
        text = "01/" + text;

        return LocalDate.parse(text, DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT));
    }

    public JButton getShowButton(){ return showButton; }
}
