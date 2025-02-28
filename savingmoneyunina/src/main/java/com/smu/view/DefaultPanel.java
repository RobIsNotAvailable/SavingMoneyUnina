package com.smu.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class DefaultPanel extends JPanel
{
    private Navbar navbar;
    protected CardManager cardManager;
    
    public DefaultPanel()
    {
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        this.navbar = new Navbar();
        this.add(navbar, BorderLayout.NORTH);

        this.cardManager = new CardManager();
        this.add(cardManager, BorderLayout.CENTER);
    }

    public Navbar getNavbar() { return navbar; }

    public CardManager getCardManager() { return cardManager; }
}
