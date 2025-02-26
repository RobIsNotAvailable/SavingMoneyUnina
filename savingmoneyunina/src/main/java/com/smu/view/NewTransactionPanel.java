package com.smu.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class NewTransactionPanel extends JPanel
{
    Navbar navbar;
    CardManager cardManager;

    public NewTransactionPanel()
    {
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        this.navbar = new Navbar();
        this.cardManager = new CardManager();

        this.add(navbar,BorderLayout.NORTH);
        this.add(cardManager,BorderLayout.WEST);
    }

    /***************************GETTERS******************************* */
    public Navbar getNavbar() { return navbar; }
    public CardManager getCardManager() { return cardManager; }
}
