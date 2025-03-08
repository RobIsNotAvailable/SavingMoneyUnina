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

    private JLabel startingBalanceLabel = UiUtil.createStyledLabel(" ");
    private JLabel finalBalanceLabel = UiUtil.createStyledLabel(" ");
    private JLabel balanceDifferenceLabel = UiUtil.createStyledLabel(" ");

    private JLabel minIncomeLabel = UiUtil.createStyledLabel(" ");
    private JLabel maxIncomeLabel = UiUtil.createStyledLabel(" ");
    private JLabel avgIncomeLabel = UiUtil.createStyledLabel(" ");

    private JLabel minExpenseLabel = UiUtil.createStyledLabel(" ");
    private JLabel maxExpenseLabel = UiUtil.createStyledLabel(" ");
    private JLabel avgExpenseLabel = UiUtil.createStyledLabel(" ");

    private JLabel reportName = UiUtil.createStyledLabel(" ");

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
        dataPanel.add(UiUtil.createStyledLabel("Month: "));
        dataPanel.add(dateField);

        dataPanel.add(new BlankPanel(new Dimension(100,1)));

        showButton = UiUtil.createStyledButton("Show");
        showButton.setContentAreaFilled(true);
        showButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        showButton.setMargin(new Insets(3, 35, 3, 35));
        UiUtil.addKeyBinding(showButton, "ENTER");
        dataPanel.add(showButton);

        messageLabel = UiUtil.createStyledLabel(" ");
        messageLabel.setPreferredSize(new Dimension(850, 50));
        dataPanel.add(messageLabel);

        reportName.setPreferredSize(new Dimension(850, 50));
        reportName.setFont(new Font("Arial", Font.BOLD, 30));
        dataPanel.add(reportName);

        addReportSection("Balance", startingBalanceLabel, finalBalanceLabel, balanceDifferenceLabel);
        addReportSection("Income", maxIncomeLabel, minIncomeLabel, avgIncomeLabel);
        addReportSection("Expense", maxExpenseLabel, minExpenseLabel, avgExpenseLabel);
        
        this.add(dataPanel, BorderLayout.EAST);
    }

    private void addReportSection(String title, JLabel label1, JLabel label2, JLabel label3)
    {
        JPanel sectionPanel = new JPanel(new FlowLayout());
        sectionPanel.setOpaque(false);
        sectionPanel.setPreferredSize(new Dimension(900, 200));

        JLabel panelName = UiUtil.createStyledLabel(title);
        panelName.setPreferredSize(new Dimension(850, 50)); 
        panelName.setHorizontalAlignment(JLabel.LEFT); 
        panelName.setFont(new Font("Arial", Font.BOLD, 30));

        sectionPanel.add(panelName);
        sectionPanel.add(new BlankPanel(new Dimension(700, 1)));

        JPanel coloredLine = new JPanel();
        coloredLine.setBackground(UiUtil.CAPPUCCINO);
        coloredLine.setPreferredSize(new Dimension(850, 5));
        sectionPanel.add(coloredLine);

        label1.setPreferredSize(new Dimension(290, 50));
        label2.setPreferredSize(new Dimension(290, 50));
        label3.setPreferredSize(new Dimension(290, 50));

        sectionPanel.add(label1);
        sectionPanel.add(label2);
        sectionPanel.add(label3);

        dataPanel.add(sectionPanel);
    }

    public void showReport(Report report)
    {
        /*****************BALANCE *************************/
        BigDecimal initial = report.getInitialBalance();
        BigDecimal ending = report.getFinalBalance();
        BigDecimal difference = report.getMonthlyDifference();

        startingBalanceLabel.setText(String.format("<html><font color='white'>Initial: </font><font color='%s'>%.2f€</font></html>", UiUtil.CAPPUCCINO_RGB, initial));
        finalBalanceLabel.setText(String.format("<html><font color='white'>Final: </font><font color='%s'>%.2f€</font></html>", UiUtil.CAPPUCCINO_RGB, ending));
        balanceDifferenceLabel.setText(String.format("<html><font color='white'>Difference: </font><font color='%s'>%.2f€</font></html>", (difference.compareTo(BigDecimal.ZERO) > 0 ? UiUtil.CAPPUCCINO_RGB : UiUtil.WHITE_RGB), difference));

        /*****************income *************************/
        IncomeDetails incomeDetails = report.getIncomeDetails();
        
        maxIncomeLabel.setText(String.format("<html><font color='white'>Highest: </font><font color='%s'>%.2f€</font></html>", UiUtil.CAPPUCCINO_RGB, incomeDetails.getMaxIncome()));
        minIncomeLabel.setText(String.format("<html><font color='white'>Lowest: </font><font color='%s'>%.2f€</font></html>", UiUtil.CAPPUCCINO_RGB, incomeDetails.getMinIncome()));
        avgIncomeLabel.setText(String.format("<html><font color='white'>Average: </font><font color='%s'>%.2f€</font></html>", UiUtil.CAPPUCCINO_RGB, incomeDetails.getAvgIncome()));

        /*****************expense *************************/
        ExpenseDetails expenseDetails = report.getExpenseDetails();

        maxExpenseLabel.setText(String.format("<html><font color='white'>Highest: </font><font color='%s'>%.2f€</font></html>", UiUtil.WHITE_RGB, expenseDetails.getMaxExpense()));
        minExpenseLabel.setText(String.format("<html><font color='white'>Lowest: </font><font color='%s'>%.2f€</font></html>", UiUtil.WHITE_RGB, expenseDetails.getMinExpense()));
        avgExpenseLabel.setText(String.format("<html><font color='white'>Average: </font><font color='%s'>%.2f€</font></html>", UiUtil.WHITE_RGB, expenseDetails.getAvgExpense()));
    
        setReportName();
    }
    
    private void setReportName()
    {
        try 
        {
            String month = getDateValue().getMonth().toString();
            int year = getDateValue().getYear();

            reportName.setText(month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase() + " " + year + " Report");
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public LocalDate getDateValue() throws Exception 
    {
        String text = dateField.getText();
        text = "01/" + text;

        try 
        {
            return LocalDate.parse(text, DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT));
        } 
        catch (Exception e) 
        {
            throw new Exception("Date is not valid");
        }
    }

    public JButton getShowButton(){ return showButton; }


}
