package com.smu;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.smu.controller.LoginController;
import com.smu.model.User;
import com.smu.view.LoginPanel;
import com.smu.view.HomePanel;
import com.smu.view.MainFrame;

public class MainController 
{
    private MainFrame mainFrame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainController() 
    {
        mainFrame = new MainFrame();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.setOpaque(false);

        // LoginPanel loginPanel = new LoginPanel();

        // new LoginController(this, loginPanel);

        // mainPanel.add(loginPanel, "Login"); 

        // USE TO DIRECTLY ACCESS HOME 
        HomePanel hp = new HomePanel(new User("alice", "Password123")); 
        mainPanel.add(hp, "Home");
        mainFrame.add(mainPanel);
    }

    public void showScreen(String screenName) 
    {
        cardLayout.show(mainPanel, screenName);
    }

    public void loadScreens(User user)
    {
        // add further panels here
        HomePanel homePanel = new HomePanel(user);
        mainPanel.add(homePanel, "Home");
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(MainController::new);
    }
}