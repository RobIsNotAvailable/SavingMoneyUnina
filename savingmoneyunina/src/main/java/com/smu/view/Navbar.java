package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.smu.view.UiUtil;

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
        familyButton = UiUtil.createStyledButton("New Transaction");
        newTransactionButton = UiUtil.createStyledButton("Reports");
        reportButton = UiUtil.createStyledButton("Family");
        logoutButton = UiUtil.createStyledButton("Log out");

        UiUtil.addKeyBinding(logoutButton, "ESCAPE");

        add(homeButton);
        add(new UiUtil.BlankPanel(new Dimension(50, 1)));
        add(familyButton);
        add(newTransactionButton);
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
