package com.smu;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.smu.controller.HomeController;
import com.smu.controller.LoginController;
import com.smu.controller.NavbarController;
import com.smu.model.User;
import com.smu.view.HomePanel;
import com.smu.view.LoginPanel;
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


        /* ********************************* EASY ACCESS TO HOME, USED FOR TESTING *********************************** */
        HomePanel homePanel = new HomePanel();
        new HomeController(this, homePanel, new User("alice", "Password123"));
        new NavbarController(this, homePanel.getNavbar());
        mainPanel.add(homePanel, "Home");
        /* ********************************* EASY ACCESS TO HOME, USED FOR TESTING *********************************** */

        LoginPanel loginPanel = new LoginPanel();

        new LoginController(this, loginPanel);

        mainPanel.add(loginPanel, "Login"); 
        
        mainFrame.add(mainPanel);
    }

    public void showScreen(String screenName) 
    {
        cardLayout.show(mainPanel, screenName);
    }

    public void loadScreens(User user)
    {
        // add further panels here
        HomePanel homePanel = new HomePanel();
        new HomeController(this, homePanel, user);
        new NavbarController(this, homePanel.getNavbar());
        mainPanel.add(homePanel, "Home");
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(MainController::new);
    }
}