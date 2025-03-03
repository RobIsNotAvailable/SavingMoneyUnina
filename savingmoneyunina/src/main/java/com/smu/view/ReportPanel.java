package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Label;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import com.smu.model.Report;
import com.smu.view.UiUtil.*;


public class ReportPanel extends DefaultPanel
{
    private JFormattedTextField date;
    private JButton showButton;
    private JPanel dataPanel;
    private JLabel startingBalance;
    private JLabel finalBalance;

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

        createDetailsLabel("Balance");

        this.add(dataPanel,BorderLayout.EAST);
    }

    public void createDetailsLabel(String labelName)
    {
        JPanel balancePanel = new JPanel(new FlowLayout());
        balancePanel.setOpaque(false);
        balancePanel.setPreferredSize(new Dimension(900,300));

        JLabel panelName = UiUtil.createStyledLabel(labelName);
        panelName.setPreferredSize(new Dimension(200,50));
        balancePanel.add(panelName);
        balancePanel.add(new BlankPanel(new Dimension(700,1)));

        JPanel coloredLine = new JPanel();
        coloredLine.setBackground(UiUtil.CAPPUCCINO);
        coloredLine.setPreferredSize(new Dimension(700, 5));
        balancePanel.add(coloredLine);

        startingBalance = UiUtil.createStyledLabel(" ");
        startingBalance.setPreferredSize(new Dimension(400,50));

        finalBalance = UiUtil.createStyledLabel(" ");
        finalBalance.setPreferredSize(new Dimension(400,50));

        balancePanel.add(startingBalance);
        balancePanel.add(finalBalance);

        dataPanel.add(balancePanel);
    }

    public void showReport(Report report)
    {
        startingBalance.setText("starting " + report.getInitialBalance());
        finalBalance.setText("final " + report.getFinalBalance());


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
