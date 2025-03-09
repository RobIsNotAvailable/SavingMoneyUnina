package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import com.smu.view.UiUtil.*;
import com.smu.model.Family;
import com.smu.model.User;

public class FamilyPanel extends DefaultPanel
{

    private JFormattedTextField dateField;
    private JButton showButton;

    private JLabel titleLabel;
    private JLabel initialBalanceLabel;
    private JLabel finalBalanceLabel;
    private JLabel incomeLabel;
    private JLabel expenseLabel;

    private JPanel usersPanel;

    public FamilyPanel()
    {
        remove(cardManager);

        addFamilyDetails();
        setUpUserPanel();
    }

    private void addFamilyDetails()
    {
        final int PANEL_WIDTH = 800;

        JPanel familyDetails = new JPanel(new FlowLayout());
        familyDetails.setOpaque(false);
        familyDetails.setPreferredSize(new Dimension(PANEL_WIDTH, 1000));

        familyDetails.add(new BlankPanel(new Dimension(PANEL_WIDTH, 50)));

        familyDetails.add(new BlankPanel(new Dimension(PANEL_WIDTH, 25)));
        JLabel iconLabel = UiUtil.createStyledLabel(" ");
        iconLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/family.png")).getImage().getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH)));
        iconLabel.setPreferredSize(new Dimension(PANEL_WIDTH,300));

        familyDetails.add(iconLabel);

        familyDetails.add(new BlankPanel(new Dimension(PANEL_WIDTH, 25)));
        titleLabel = UiUtil.createStyledLabel(" ");
        titleLabel.setPreferredSize(new Dimension(PANEL_WIDTH, 50));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));

        familyDetails.add(titleLabel);
       
        JPanel coloredLine = new JPanel();
        coloredLine.setBackground(UiUtil.CAPPUCCINO);
        coloredLine.setPreferredSize(new Dimension(PANEL_WIDTH-70, 5));

        familyDetails.add(coloredLine);

        initialBalanceLabel = UiUtil.createStyledLabel(" ");
        initialBalanceLabel.setPreferredSize(new Dimension(350, 100));
        familyDetails.add(initialBalanceLabel);

        finalBalanceLabel = UiUtil.createStyledLabel(" ");
        finalBalanceLabel.setPreferredSize(new Dimension(350, 100));
        familyDetails.add(finalBalanceLabel);

        incomeLabel = UiUtil.createStyledLabel(" ");
        incomeLabel.setPreferredSize(new Dimension(350, 100));
        familyDetails.add(incomeLabel);

        expenseLabel = UiUtil.createStyledLabel(" ");
        expenseLabel.setPreferredSize(new Dimension(350, 100));
        familyDetails.add(expenseLabel);

        this.add(familyDetails, BorderLayout.WEST);
    }

    private void setUpUserPanel()
    {
        usersPanel = new JPanel(new FlowLayout());
        usersPanel.setOpaque(false);
        usersPanel.setPreferredSize(new Dimension(900, 1000));

        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(900, 140));

        headerPanel.add(new BlankPanel(new Dimension(900, 28)));
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

        headerPanel.add(new BlankPanel(new Dimension(1, 1)));
        headerPanel.add(UiUtil.createStyledLabel("Month: "));
        headerPanel.add(dateField);

        headerPanel.add(new BlankPanel(new Dimension(100,1)));

        showButton = UiUtil.createStyledButton("Show");
        showButton.setContentAreaFilled(true);
        showButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        showButton.setMargin(new Insets(3, 35, 3, 35));
        UiUtil.addKeyBinding(showButton, "ENTER");

        headerPanel.add(showButton);

        messageLabel = UiUtil.createStyledLabel(" ");
        messageLabel.setPreferredSize(new Dimension(850, 50));

        headerPanel.add(messageLabel);

        TransparentScrollPanel scrollPanel = new TransparentScrollPanel(usersPanel, 900, 2000);

        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.setPreferredSize(new Dimension(900, 1000));
        eastPanel.setOpaque(false);
    
        eastPanel.add(headerPanel, BorderLayout.NORTH);
        eastPanel.add(scrollPanel, BorderLayout.CENTER);

        this.add(eastPanel, BorderLayout.EAST);
    }

    private JPanel createUserPanel(User user, LocalDate date, int panelWidth)
    {
        JPanel userPanel = new JPanel(new FlowLayout());
        userPanel.setOpaque(false);
        userPanel.setPreferredSize(new Dimension(panelWidth, 310));

        JLabel iconLabel = UiUtil.createStyledLabel(" ");
        iconLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/profile.png")).getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
        iconLabel.setPreferredSize(new Dimension(60, 50));
        
        JLabel nameLabel = UiUtil.createStyledLabel(" ");
        nameLabel.setPreferredSize(new Dimension(panelWidth - 300, 50));
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nameLabel.setText(user.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 30));

        JPanel coloredLine = new JPanel();
        coloredLine.setBackground(UiUtil.CAPPUCCINO);
        coloredLine.setPreferredSize(new Dimension(panelWidth - 100, 5));

        JLabel initialBalanceLabel = UiUtil.createStyledLabel(" ");
        JLabel finalBalanceLabel = UiUtil.createStyledLabel(" ");
        JLabel incomeLabel = UiUtil.createStyledLabel(" ");
        JLabel expenseLabel = UiUtil.createStyledLabel(" ");

        initialBalanceLabel.setText(String.format("<html><font color='white'>Initial Balance: </font><font color='%s'>%.2f€</font></html>",UiUtil.CAPPUCCINO_RGB, user.getInitialBalance(date)));
        
        finalBalanceLabel.setText(String.format("<html><font color='white'>Final Balance: </font><font color='%s'>%.2f€</font></html>",UiUtil.CAPPUCCINO_RGB, user.getFinalBalance(date)));
        
        incomeLabel.setText(String.format("<html><font color='white'>Income: </font><font color='%s'>%.2f€</font></html>",UiUtil.CAPPUCCINO_RGB, user.getUserMonthlyIncome(date)));

        expenseLabel.setText(String.format("<html><font color='white'>Expense: </font><font color='%s'>%.2f€</font></html>",UiUtil.WHITE_RGB, user.getUserMonthlyExpense(date)));
    

        int labelWidth = (panelWidth / 2) - 10;
        initialBalanceLabel.setPreferredSize(new Dimension(labelWidth, 50));
        finalBalanceLabel.setPreferredSize(new Dimension(labelWidth, 50));
        incomeLabel.setPreferredSize(new Dimension(labelWidth, 50));
        expenseLabel.setPreferredSize(new Dimension(labelWidth, 50));

        userPanel.add(iconLabel);
        userPanel.add(nameLabel);
        userPanel.add(new BlankPanel(new Dimension(panelWidth, 10)));
        userPanel.add(coloredLine);
        userPanel.add(new BlankPanel(new Dimension(panelWidth, 20)));
        userPanel.add(initialBalanceLabel);
        userPanel.add(finalBalanceLabel);
        userPanel.add(incomeLabel);
        userPanel.add(expenseLabel);

        return userPanel;
    }

    public void showUsers(Family family)
    {
        final int PANEL_WIDTH = 900;
        usersPanel.removeAll();

        LocalDate date;
        try
        {
            date = getDateValue();
        }
        catch (Exception e)
        {
            showErrorMessage(e.getMessage());
            return;
        }

        for (User user : family.getMembers())
        {
            JPanel userPanel = createUserPanel(user, date, PANEL_WIDTH);
            usersPanel.add(userPanel);
        }

        usersPanel.setPreferredSize(new Dimension(PANEL_WIDTH, family.getMembers().size() * 330));
    }

    public void showFamilyDetails(Family family)
    {
        try 
        {
            LocalDate date = getDateValue();
            
            if (date.isAfter(LocalDate.now()))
                showErrorMessage("We can't read the future yet");

            titleLabel.setText(family.getName() + "'s " + formatDate(date) + " Summary");
            initialBalanceLabel.setText(String.format("<html><font color='white'>Initial Balance: </font><font color='%s'>%.2f€</font></html>", UiUtil.CAPPUCCINO_RGB, family.getInitialBalance(date) ));
            finalBalanceLabel.setText(String.format("<html><font color='white'>Final Balance: </font><font color='%s'>%.2f€</font></html>", UiUtil.CAPPUCCINO_RGB, family.getFinalBalance(date)));
            incomeLabel.setText(String.format("<html><font color='white'>Income: </font><font color='%s'>%.2f€</font></html>", UiUtil.CAPPUCCINO_RGB, family.getMonthlyIncome(date)));
            expenseLabel.setText(String.format("<html><font color='white'>Expense: </font><font color='%s'>%.2f€</font></html>", UiUtil.WHITE_RGB, family.getMonthlyExpense(date)));
        } 
        catch (Exception e)
        {
            showErrorMessage(e.getMessage());
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

    private String formatDate(LocalDate date)
    {
        String month = date.getMonth().toString();
        int year = date.getYear();

        return month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase() + " " + year;
    }

    public JButton getShowButton(){ return showButton; }
}
