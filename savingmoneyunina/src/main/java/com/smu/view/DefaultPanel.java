package com.smu.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DefaultPanel extends JPanel
{
    private Navbar navbar;
    protected CardManager cardManager;
    protected JLabel messageLabel;
    
    public DefaultPanel()
    {
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        this.navbar = new Navbar();
        this.add(navbar, BorderLayout.NORTH);

        this.cardManager = new CardManager();
        this.add(cardManager, BorderLayout.CENTER);
    }

    public void showErrorMessage(String message)
    {
        messageLabel.setForeground(UiUtil.ERROR_RED);
        messageLabel.setText(message);
    }

    public void showSuccessMessage(String message)
    {
        messageLabel.setForeground(UiUtil.SUCCESS_GREEN);
        messageLabel.setText(message);
        
        UiUtil.delayExecution(4000, _ -> resetMessage());
    }

    public void resetMessage()
    {
        messageLabel.setText(" ");
    }

    public Navbar getNavbar() { return navbar; }

    public CardManager getCardManager() { return cardManager; }
}
