package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Navbar extends JPanel
{
    private JPanel wrapperPanel;
    
    private JButton homeButton;
    private JButton familyButton;
    private JButton newTransactionButton;
    private JButton reportButton;
    private JButton logoutButton;

    public Navbar()
    {
        wrapperPanel = new JPanel(new BorderLayout());
        setBackground(UiUtil.DARK_CAPPUCCINO);

        setLayout(new GridLayout(1, 0, 20, 0));

        homeButton = UiUtil.createStyledButton("");
        homeButton.setMargin(new Insets(0, 15, 0, 15));
        homeButton.add(new UiUtil.LogoLabel(0.2));
        
        newTransactionButton = UiUtil.createStyledButton("New Transaction");
        reportButton = UiUtil.createStyledButton("Reports");
        familyButton = UiUtil.createStyledButton("Family");
        logoutButton = UiUtil.createStyledButton("");

        UiUtil.addKeyBinding(logoutButton, "ESCAPE");

        familyButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/family.png")).getImage().getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH)));
        familyButton.setHorizontalTextPosition(JButton.LEFT);

        reportButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/report.png")).getImage().getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH)));
        reportButton.setHorizontalTextPosition(JButton.LEFT);
        
        newTransactionButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/transaction.png")).getImage().getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH)));
        newTransactionButton.setHorizontalTextPosition(JButton.LEFT);

        logoutButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/log out.png")).getImage().getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH)));
        logoutButton.setHorizontalTextPosition(JButton.LEFT);

        add(homeButton);
        add(new UiUtil.BlankPanel(new Dimension(50, 1)));
        add(newTransactionButton);
        add(familyButton);
        add(reportButton);
        add(new UiUtil.BlankPanel(new Dimension(50, 1)));
        add(logoutButton);

        wrapperPanel.add(this, BorderLayout.CENTER);
    }

    public JButton getHomeButton() { return homeButton; }

    public JButton getFamilyButton() { return familyButton; }

    public JButton getNewTransactionButton() { return newTransactionButton; }

    public JButton getreportButton() { return reportButton; }

    public JButton getLogoutButton() { return logoutButton; }
}
