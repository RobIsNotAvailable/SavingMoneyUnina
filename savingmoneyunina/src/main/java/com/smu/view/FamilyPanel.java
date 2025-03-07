package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

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
        addUsersPanel();
    }

    private void addFamilyDetails()
    {
        final int PANEL_WIDTH = 800;

        JPanel familyDetails = new JPanel(new FlowLayout());
        familyDetails.setOpaque(false);
        familyDetails.setPreferredSize(new Dimension(PANEL_WIDTH, 1000));

        familyDetails.add(new BlankPanel(new Dimension(PANEL_WIDTH, 50)));

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

        familyDetails.add(UiUtil.createStyledLabel("Month: "));
        familyDetails.add(dateField);
        familyDetails.add(new BlankPanel(new Dimension(100,1)));

        showButton = UiUtil.createStyledButton("Show");
        showButton.setContentAreaFilled(true);
        showButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        showButton.setMargin(new Insets(3, 35, 3, 35));
        UiUtil.addKeyBinding(showButton, "ENTER");

        familyDetails.add(showButton);

        messageLabel = UiUtil.createStyledLabel(" ");
        messageLabel.setPreferredSize(new Dimension(850, 50));

        familyDetails.add(messageLabel);

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

    private void addUsersPanel()
    {
        usersPanel = new JPanel(new FlowLayout());
        usersPanel.setOpaque(false);
        usersPanel.setPreferredSize(new Dimension(900, 1000));

        TransparentScrollPanel scrollPanel = new TransparentScrollPanel(usersPanel, 900, 2000);

        this.add(scrollPanel, BorderLayout.EAST);
    }

    public void showUsers(List<User> users)
    {
        final int PANEL_WIDTH = 900;

        for(User user : users)
        {
            usersPanel.add(new BlankPanel(new Dimension(PANEL_WIDTH,100)));

            JPanel userPanel = new JPanel(new FlowLayout());
            userPanel.setOpaque(false);
            userPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 50));

            JLabel iconLabel = UiUtil.createStyledLabel(" ");
            iconLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/profile.png")).getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
            iconLabel.setPreferredSize(new Dimension(60,50));

            JLabel nameLabel = UiUtil.createStyledLabel(" ");
            nameLabel.setPreferredSize(new Dimension(PANEL_WIDTH-300,50));
            nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
            nameLabel.setText(user.getUsername());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 30));

            userPanel.add(iconLabel);
            userPanel.add(nameLabel);
            
            JPanel coloredLine = new JPanel();
            coloredLine.setBackground(UiUtil.CAPPUCCINO);
            coloredLine.setPreferredSize(new Dimension(PANEL_WIDTH-100, 5));
            

            JLabel userIncome = UiUtil.createStyledLabel("placeholder");
            JLabel userExpense = UiUtil.createStyledLabel("placeholder");
            JLabel userInitialBalance = UiUtil.createStyledLabel("placeholder");
            JLabel userFinalBalance = UiUtil.createStyledLabel("placeholder");

            userIncome.setPreferredSize(new Dimension((PANEL_WIDTH/2)-10, 50));
            userExpense.setPreferredSize(new Dimension((PANEL_WIDTH/2)-10, 50));
            userInitialBalance.setPreferredSize(new Dimension((PANEL_WIDTH/2)-10, 50));
            userFinalBalance.setPreferredSize(new Dimension((PANEL_WIDTH/2)-10, 50));

            usersPanel.add(userPanel);
            usersPanel.add(new BlankPanel(new Dimension(PANEL_WIDTH,20)));
            usersPanel.add(coloredLine);
            usersPanel.add(new BlankPanel(new Dimension(PANEL_WIDTH,20)));
            usersPanel.add(userIncome);
            usersPanel.add(userExpense);
            usersPanel.add(userInitialBalance);
            usersPanel.add(userFinalBalance);
        }
    }

    public void showFamilyDetails(Family family)
    {

        String incomeColor = UiUtil.CAPPUCCINO_RGB;
        String expenseColor = "rgb(255, 255, 255)";

        try 
        {
            LocalDate date = getDateValue();
            
            if (date.isAfter(LocalDate.now()))
                showErrorMessage("We can't read the future yet");

            titleLabel.setText(family.getName() + "'s " + formatDate(date) + " Summary");
            initialBalanceLabel.setText(String.format("<html><font color='white'>Initial Balance: </font><font color='%s'>%.2f€</font></html>", incomeColor, family.getInitialBalance(date) ));
            finalBalanceLabel.setText(String.format("<html><font color='white'>Final Balance: </font><font color='%s'>%.2f€</font></html>", incomeColor, family.getFinalBalance(date)));
            incomeLabel.setText(String.format("<html><font color='white'>Income: </font><font color='%s'>%.2f€</font></html>", incomeColor, family.getMonthlyIncome(date)));
            expenseLabel.setText(String.format("<html><font color='white'>Expense: </font><font color='%s'>%.2f€</font></html>", expenseColor, family.getMonthlyExpense(date)));
        } 
        catch (Exception e)
        {
            showErrorMessage(e.getMessage());
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

    private String formatDate(LocalDate date)
    {
        try 
        {
            String month = date.getMonth().toString();
            int year = date.getYear();

            return month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase() + " " + year;
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public JButton getShowButton(){ return showButton; }
}
