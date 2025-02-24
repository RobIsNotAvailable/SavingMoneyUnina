package com.smu;

import java.awt.CardLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.smu.controller.HomeController;
import com.smu.controller.LoginController;
import com.smu.controller.NavbarController;
import com.smu.dao.PaymentCardDAO;
import com.smu.model.PaymentCard;
import com.smu.model.Transaction;
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
        
        loadScreens(new User("alice", "Password123"));
        
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
        new HomeController(homePanel, user);
        new NavbarController(this, homePanel.getNavbar());
        mainPanel.add(homePanel, "Home");
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(MainController::new);
    }



    public static void populate()//used for populating the db 
    {
        PaymentCard card;
        /* ************************************************************ALICE FIRST CARD****************************************************************** */
        // card = PaymentCardDAO.get("1234567812345678");

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(120), "groceries", LocalDate.parse("2024-02-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2100), "salary", LocalDate.parse("2024-02-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(250), "headphones", LocalDate.parse("2024-02-15"), Transaction.Direction.EXPENSE, card, "electronics store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(80), "lunch", LocalDate.parse("2024-02-20"), Transaction.Direction.EXPENSE, card, "restaurant"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(1700), "furniture", LocalDate.parse("2024-03-01"), Transaction.Direction.INCOME, card, "furniture store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(130), "groceries", LocalDate.parse("2024-03-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2200), "salary", LocalDate.parse("2024-03-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(400), "tablet", LocalDate.parse("2024-03-15"), Transaction.Direction.EXPENSE, card, "electronics store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(90), "dinner", LocalDate.parse("2024-03-20"), Transaction.Direction.EXPENSE, card, "restaurant"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(1600), "bike", LocalDate.parse("2024-04-01"), Transaction.Direction.INCOME, card, "bike store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(140), "groceries", LocalDate.parse("2024-04-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2300), "salary", LocalDate.parse("2024-04-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(350), "camera", LocalDate.parse("2024-04-15"), Transaction.Direction.EXPENSE, card, "electronics store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(110), "lunch", LocalDate.parse("2024-04-20"), Transaction.Direction.EXPENSE, card, "restaurant"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(1500), "vacation", LocalDate.parse("2024-05-01"), Transaction.Direction.INCOME, card, "travel agency"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(160), "groceries", LocalDate.parse("2024-05-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2400), "salary", LocalDate.parse("2024-05-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(300), "smartwatch", LocalDate.parse("2024-05-15"), Transaction.Direction.EXPENSE, card, "electronics store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(120), "dinner", LocalDate.parse("2024-05-20"), Transaction.Direction.EXPENSE, card, "restaurant"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(1400), "concert tickets", LocalDate.parse("2024-06-01"), Transaction.Direction.INCOME, card, "ticketmaster"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(170), "groceries", LocalDate.parse("2024-06-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2500), "salary", LocalDate.parse("2024-06-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(450), "gaming console", LocalDate.parse("2024-06-15"), Transaction.Direction.EXPENSE, card, "electronics store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(130), "lunch", LocalDate.parse("2024-06-20"), Transaction.Direction.EXPENSE, card, "restaurant"));
        
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(1300), "gym membership", LocalDate.parse("2024-07-01"), Transaction.Direction.INCOME, card, "gym"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(180), "clothing", LocalDate.parse("2024-07-05"), Transaction.Direction.EXPENSE, card, "clothing store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2600), "freelance work", LocalDate.parse("2024-07-10"), Transaction.Direction.INCOME, card, "client"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(500), "furniture", LocalDate.parse("2024-07-15"), Transaction.Direction.EXPENSE, card, "furniture store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(140), "movie tickets", LocalDate.parse("2024-07-20"), Transaction.Direction.EXPENSE, card, "cinema"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(1200), "bonus", LocalDate.parse("2024-08-01"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(190), "books", LocalDate.parse("2024-08-05"), Transaction.Direction.EXPENSE, card, "bookstore"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2700), "consulting", LocalDate.parse("2024-08-10"), Transaction.Direction.INCOME, card, "client"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(550), "home decor", LocalDate.parse("2024-08-15"), Transaction.Direction.EXPENSE, card, "home store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(150), "concert tickets", LocalDate.parse("2024-08-20"), Transaction.Direction.EXPENSE, card, "ticketmaster"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(1100), "investment return", LocalDate.parse("2024-09-01"), Transaction.Direction.INCOME, card, "investment firm"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(200), "pet supplies", LocalDate.parse("2024-09-05"), Transaction.Direction.EXPENSE, card, "pet store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2800), "project payment", LocalDate.parse("2024-09-10"), Transaction.Direction.INCOME, card, "client"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(600), "appliances", LocalDate.parse("2024-09-15"), Transaction.Direction.EXPENSE, card, "appliance store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(160), "theater tickets", LocalDate.parse("2024-09-20"), Transaction.Direction.EXPENSE, card, "theater"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(1000), "dividends", LocalDate.parse("2024-10-01"), Transaction.Direction.INCOME, card, "investment firm"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(210), "sports equipment", LocalDate.parse("2024-10-05"), Transaction.Direction.EXPENSE, card, "sports store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2900), "contract work", LocalDate.parse("2024-10-10"), Transaction.Direction.INCOME, card, "client"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(650), "kitchenware", LocalDate.parse("2024-10-15"), Transaction.Direction.EXPENSE, card, "kitchen store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(170), "spa treatment", LocalDate.parse("2024-10-20"), Transaction.Direction.EXPENSE, card, "spa"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(900), "rental income", LocalDate.parse("2024-11-01"), Transaction.Direction.INCOME, card, "tenant"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(220), "gifts", LocalDate.parse("2024-11-05"), Transaction.Direction.EXPENSE, card, "gift shop"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(3000), "freelance project", LocalDate.parse("2024-11-10"), Transaction.Direction.INCOME, card, "client"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(700), "garden supplies", LocalDate.parse("2024-11-15"), Transaction.Direction.EXPENSE, card, "garden store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(180), "museum tickets", LocalDate.parse("2024-11-20"), Transaction.Direction.EXPENSE, card, "museum"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(800), "royalties", LocalDate.parse("2025-01-01"), Transaction.Direction.INCOME, card, "publisher"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(230), "electronics", LocalDate.parse("2025-01-05"), Transaction.Direction.EXPENSE, card, "electronics store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(3100), "consulting fee", LocalDate.parse("2025-01-10"), Transaction.Direction.INCOME, card, "client"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(750), "home improvement", LocalDate.parse("2025-01-15"), Transaction.Direction.EXPENSE, card, "home store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(190), "zoo tickets", LocalDate.parse("2025-01-20"), Transaction.Direction.EXPENSE, card, "zoo"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(700), "cashback", LocalDate.parse("2025-02-01"), Transaction.Direction.INCOME, card, "credit card"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(240), "office supplies", LocalDate.parse("2025-02-05"), Transaction.Direction.EXPENSE, card, "office store"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(3200), "project bonus", LocalDate.parse("2025-02-10"), Transaction.Direction.INCOME, card, "client"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(800), "car maintenance", LocalDate.parse("2025-02-15"), Transaction.Direction.EXPENSE, card, "auto shop"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(200), "theme park tickets", LocalDate.parse("2025-02-20"), Transaction.Direction.EXPENSE, card, "theme park"));
    
        /***************************************************************************ALICE SECOND CARD******************************************************************** */
    
        // card = PaymentCardDAO.get("3498734875349033");
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(100), "groceries", LocalDate.parse("2024-02-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2000), "salary", LocalDate.parse("2024-02-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(150), "shoes", LocalDate.parse("2024-02-15"), Transaction.Direction.EXPENSE, card, "shoe store"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(110), "groceries", LocalDate.parse("2024-03-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2100), "salary", LocalDate.parse("2024-03-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(250), "jacket", LocalDate.parse("2024-03-15"), Transaction.Direction.EXPENSE, card, "clothing store"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(120), "groceries", LocalDate.parse("2024-04-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2200), "salary", LocalDate.parse("2024-04-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(350), "phone", LocalDate.parse("2024-04-15"), Transaction.Direction.EXPENSE, card, "electronics store"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(130), "groceries", LocalDate.parse("2024-05-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2300), "salary", LocalDate.parse("2024-05-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(450), "laptop", LocalDate.parse("2024-05-15"), Transaction.Direction.EXPENSE, card, "electronics store"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(140), "groceries", LocalDate.parse("2024-06-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2400), "salary", LocalDate.parse("2024-06-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(550), "watch", LocalDate.parse("2024-06-15"), Transaction.Direction.EXPENSE, card, "jewelry store"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(150), "groceries", LocalDate.parse("2024-07-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2500), "salary", LocalDate.parse("2024-07-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(650), "bike", LocalDate.parse("2024-07-15"), Transaction.Direction.EXPENSE, card, "bike store"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(160), "groceries", LocalDate.parse("2024-08-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2600), "salary", LocalDate.parse("2024-08-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(750), "camera", LocalDate.parse("2024-08-15"), Transaction.Direction.EXPENSE, card, "electronics store"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(170), "groceries", LocalDate.parse("2024-09-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2700), "salary", LocalDate.parse("2024-09-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(850), "furniture", LocalDate.parse("2024-09-15"), Transaction.Direction.EXPENSE, card, "furniture store"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(180), "groceries", LocalDate.parse("2024-10-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2800), "salary", LocalDate.parse("2024-10-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(950), "TV", LocalDate.parse("2024-10-15"), Transaction.Direction.EXPENSE, card, "electronics store"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(190), "groceries", LocalDate.parse("2024-11-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(2900), "salary", LocalDate.parse("2024-11-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(1050), "sofa", LocalDate.parse("2024-11-15"), Transaction.Direction.EXPENSE, card, "furniture store"));

        // card.executeTransaction(new Transaction(BigDecimal.valueOf(200), "groceries", LocalDate.parse("2024-12-05"), Transaction.Direction.EXPENSE, card, "supermarket"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(3000), "salary", LocalDate.parse("2024-12-10"), Transaction.Direction.INCOME, card, "company"));
        // card.executeTransaction(new Transaction(BigDecimal.valueOf(1150), "holiday trip", LocalDate.parse("2024-12-15"), Transaction.Direction.EXPENSE, card, "travel agency"));
    
    
    
    
    
    }
}