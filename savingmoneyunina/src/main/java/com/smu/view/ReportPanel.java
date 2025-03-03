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
    private JFormattedTextField date;
    private JButton showButton;
    private JPanel dataPanel;

    private JLabel startingBalance;
    private JLabel finalBalance;
    private JLabel balanceDifference;

    private JLabel minIncome;
    private JLabel maxIncome;
    private JLabel avgIncome;

    private JLabel minExpense;
    private JLabel maxExpense;
    private JLabel avgExpense;


    public ReportPanel()
    {
        cardManager.removeFinanceDetails();
        this.add(cardManager,BorderLayout.WEST);


        dataPanel = new JPanel(new FlowLayout());
        dataPanel.setOpaque(false);
        dataPanel.setPreferredSize(new Dimension(900,1000));

        date = new JFormattedTextField();

        try
        {
            MaskFormatter formatter = new MaskFormatter("##/####");
            formatter.setPlaceholderCharacter('-');
            date = new JFormattedTextField(formatter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        UiUtil.styleComponent(date);
        date.setFont(new Font("Arial", Font.PLAIN, 18));
        date.setColumns(7);
        date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/uuuu")));

        dataPanel.add(new BlankPanel(new Dimension(1,100)));
        dataPanel.add(UiUtil.createStyledLabel("Select a date"));
        dataPanel.add(date);

        dataPanel.add(new BlankPanel(new Dimension(100,1)));

        showButton = UiUtil.createStyledButton("Show");
        showButton.setContentAreaFilled(true);
        showButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        showButton.setMargin(new Insets(3, 35, 3, 35));
        UiUtil.addKeyBinding(showButton, "ENTER");
        dataPanel.add(showButton);

        createBalanceLabel();
        createIncomeLabel();
        createExpenseLabel();

        this.add(dataPanel,BorderLayout.EAST);
    }

    private void createBalanceLabel()
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
        
        startingBalance = UiUtil.createStyledLabel(" ");
        startingBalance.setPreferredSize(new Dimension(290,50));

        finalBalance = UiUtil.createStyledLabel(" ");
        finalBalance.setPreferredSize(new Dimension(290,50));

        balanceDifference = UiUtil.createStyledLabel(" ");
        balanceDifference.setPreferredSize(new Dimension(290,50));

        balancePanel.add(startingBalance);
        balancePanel.add(finalBalance);
        balancePanel.add(balanceDifference);

        dataPanel.add(balancePanel);
    }

    private void createIncomeLabel()
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
        
        maxIncome = UiUtil.createStyledLabel(" ");
        maxIncome.setPreferredSize(new Dimension(290,50));

        minIncome = UiUtil.createStyledLabel(" ");
        minIncome.setPreferredSize(new Dimension(290,50));

        avgIncome = UiUtil.createStyledLabel(" ");
        avgIncome.setPreferredSize(new Dimension(290,50));

        incomePanel.add(maxIncome);
        incomePanel.add(minIncome);
        incomePanel.add(avgIncome);

        dataPanel.add(incomePanel);
    }
    
    private void createExpenseLabel()
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
        
        maxIncome = UiUtil.createStyledLabel(" ");
        maxIncome.setPreferredSize(new Dimension(290,50));

        minIncome = UiUtil.createStyledLabel(" ");
        minIncome.setPreferredSize(new Dimension(290,50));

        avgIncome = UiUtil.createStyledLabel(" ");
        avgIncome.setPreferredSize(new Dimension(290,50));

        expensePanel.add(maxIncome);
        expensePanel.add(minIncome);
        expensePanel.add(avgIncome);

        dataPanel.add(expensePanel);
    }

    public void showReport(Report report)
    {
        String incomeColor = "rgb(" + UiUtil.CAPPUCCINO.getRed() + ", " + UiUtil.CAPPUCCINO.getGreen() + ", " + UiUtil.CAPPUCCINO.getBlue() + ")";
        String expenseColor = "rgb(255,255, 255)";


        /*****************BALANCE *************************/
        BigDecimal initial = report.getInitialBalance();
        BigDecimal ending = report.getFinalBalance();
        BigDecimal difference = ending.subtract(initial);

        startingBalance.setText("<html><font color='white'>Initial: </font><font color='" + incomeColor + "'>" + initial + "€</font></html>");
        finalBalance.setText("<html><font color='white'>Final: </font><font color='" + incomeColor + "'>" + ending + "€</font></html>");

        if(difference.compareTo(BigDecimal.ZERO) == 1)
            balanceDifference.setText("<html><font color='white'>Difference: </font><font color='" + incomeColor + "'>" + difference + "€</font></html>");
        else
            balanceDifference.setText("<html><font color='white'>Difference: </font><font color='" + expenseColor + "'>" + difference + "€</font></html>");




        /*****************income *************************/
        IncomeDetails incomeDetails = report.getIncomeDetails();
        
        maxIncome.setText("<html><font color='white'>Max: </font><font color='" + incomeColor + "'>"  + incomeDetails.getMaxIncome() + "€</font></html>");
        minIncome.setText("<html><font color='white'>Min: </font><font color='" + incomeColor + "'>"  + incomeDetails.getMinIncome() + "€</font></html>");
        avgIncome.setText("<html><font color='white'>Avg: </font><font color='" + incomeColor + "'>"  + incomeDetails.getAvgIncome() + "€</font></html>");



        /*****************expense *************************/
        // ExpenseDetails expenseDetails = report.getExpenseDetails();

        // maxExpense.setText("<html><font color='white'>Max: </font><font color='" + expenseColor + "'>"  + expenseDetails.getMaxExpense() + "€</font></html>");
        // minExpense.setText("<html><font color='white'>Min: </font><font color='" + expenseColor + "'>"  + expenseDetails.getMinExpense() + "€</font></html>");
        // avgExpense.setText("<html><font color='white'>Avg: </font><font color='" + expenseColor + "'>"  + expenseDetails.getAvgExpense() + "€</font></html>");

        System.out.println("STAMPA DENTRO REPORT PANEL " + report.getExpenseDetails());

        dataPanel.revalidate();
        dataPanel.repaint();
    }

    /****************************************************GETTERS******************** */
    public LocalDate getDateValue() throws Exception 
    {
        String text = date.getText();

        if (text.equals("--/----")) 
            return null;
        
        text = "01/" + text;

        return LocalDate.parse(text, DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT));
    }

    public JButton getShowButton(){ return showButton; }
}
