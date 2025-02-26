package com.smu;

import java.awt.CardLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.smu.controller.HomeController;
import com.smu.controller.LoginController;
import com.smu.controller.NewTransactionController;
import com.smu.dao.PaymentCardDAO;
import com.smu.model.PaymentCard;
import com.smu.model.Transaction;
import com.smu.model.User;
import com.smu.view.HomePanel;
import com.smu.view.LoginPanel;
import com.smu.view.MainFrame;
import com.smu.view.NewTransactionPanel;

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
        HomePanel homePanel = new HomePanel();
        new HomeController(this, homePanel, user);

        NewTransactionPanel ntp = new NewTransactionPanel();
        new NewTransactionController(this, ntp, user);

        mainPanel.add(homePanel, "Home");
        mainPanel.add(ntp, "New transaction");
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(MainController::new);
    }


    //used for populating the db only for first time running of the application
    public static void populate()
    {
        /* ************************************************************ALICE FIRST CARD****************************************************************** */
        PaymentCard card;
        card = PaymentCardDAO.get("1234567812345678");

        card.executeTransaction(new Transaction(BigDecimal.valueOf(120), "Purchased groceries for the week.", LocalDate.parse("2024-02-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2100), "Received salary as my monthly income.", LocalDate.parse("2024-02-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(250), "Bought headphones to enjoy high-quality sound.", LocalDate.parse("2024-02-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(80), "Had lunch during a quick break.", LocalDate.parse("2024-02-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1700), "Received payment for furniture sold.", LocalDate.parse("2024-03-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(130), "Purchased groceries to replenish the pantry.", LocalDate.parse("2024-03-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2200), "Received salary as my monthly income.", LocalDate.parse("2024-03-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(400), "Bought a tablet for both work and leisure.", LocalDate.parse("2024-03-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(90), "Had dinner with friends at a cozy restaurant.", LocalDate.parse("2024-03-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1600), "Received payment for a bike sale.", LocalDate.parse("2024-04-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(140), "Purchased groceries for daily needs.", LocalDate.parse("2024-04-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2300), "Received salary as my monthly income.", LocalDate.parse("2024-04-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(350), "Bought a camera to capture special moments.", LocalDate.parse("2024-04-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(110), "Had lunch at a local eatery.", LocalDate.parse("2024-04-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1500), "Received vacation funds to plan my holiday.", LocalDate.parse("2024-05-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(160), "Purchased groceries for the week ahead.", LocalDate.parse("2024-05-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2400), "Received salary as my monthly income.", LocalDate.parse("2024-05-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(300), "Bought a smartwatch to monitor my fitness.", LocalDate.parse("2024-05-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(120), "Enjoyed dinner at an elegant restaurant.", LocalDate.parse("2024-05-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1400), "Received income from concert ticket sales.", LocalDate.parse("2024-06-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(170), "Purchased groceries to restock my fridge.", LocalDate.parse("2024-06-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2500), "Received salary as my monthly income.", LocalDate.parse("2024-06-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(450), "Bought a gaming console for home entertainment.", LocalDate.parse("2024-06-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(130), "Had a quick lunch at a nearby cafe.", LocalDate.parse("2024-06-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1300), "Received a gym membership credit.", LocalDate.parse("2024-07-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(180), "Purchased clothing from a trendy boutique.", LocalDate.parse("2024-07-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2600), "Received payment for freelance work.", LocalDate.parse("2024-07-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(500), "Spent money on new furniture for my apartment.", LocalDate.parse("2024-07-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(140), "Bought movie tickets for an evening at the cinema.", LocalDate.parse("2024-07-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1200), "Received a performance bonus at work.", LocalDate.parse("2024-08-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(190), "Purchased books to expand my personal library.", LocalDate.parse("2024-08-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2700), "Earned income from consulting services.", LocalDate.parse("2024-08-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(550), "Spent on home decor to refresh my living space.", LocalDate.parse("2024-08-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(150), "Bought concert tickets for an upcoming show.", LocalDate.parse("2024-08-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1100), "Received investment returns as dividend payments.", LocalDate.parse("2024-09-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(200), "Purchased pet supplies for my beloved pet.", LocalDate.parse("2024-09-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2800), "Received project payment for recent work.", LocalDate.parse("2024-09-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(600), "Bought appliances to upgrade my home.", LocalDate.parse("2024-09-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(160), "Purchased theater tickets for a live performance.", LocalDate.parse("2024-09-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1000), "Received dividends from my investments.", LocalDate.parse("2024-10-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(210), "Purchased sports equipment for my fitness routine.", LocalDate.parse("2024-10-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2900), "Received payment for contract work completed.", LocalDate.parse("2024-10-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(650), "Bought kitchenware to upgrade my cooking space.", LocalDate.parse("2024-10-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(170), "Indulged in a spa treatment for relaxation.", LocalDate.parse("2024-10-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(900), "Received rental income from my property.", LocalDate.parse("2024-11-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(220), "Spent money on thoughtful gifts for loved ones.", LocalDate.parse("2024-11-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3000), "Received payment for a freelance project.", LocalDate.parse("2024-11-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(700), "Purchased garden supplies to improve my garden.", LocalDate.parse("2024-11-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(180), "Bought museum tickets for a cultural outing.", LocalDate.parse("2024-11-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(800), "Received royalties from published works.", LocalDate.parse("2025-01-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(230), "Spent on electronics to upgrade my devices.", LocalDate.parse("2025-01-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3100), "Earned a consulting fee from a major client.", LocalDate.parse("2025-01-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(750), "Invested in home improvement projects.", LocalDate.parse("2025-01-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(190), "Purchased zoo tickets for a family outing.", LocalDate.parse("2025-01-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(700), "Received cashback rewards on recent purchases.", LocalDate.parse("2025-02-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(240), "Bought office supplies to replenish the stock.", LocalDate.parse("2025-02-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3200), "Received a project bonus for excellent work.", LocalDate.parse("2025-02-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(800), "Spent on car maintenance for regular servicing.", LocalDate.parse("2025-02-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(200), "Purchased theme park tickets for a fun day out.", LocalDate.parse("2025-02-20"), Transaction.Direction.EXPENSE, card));

        /***************************************************************************ALICE SECOND CARD******************************************************************** */

        card = PaymentCardDAO.get("3498734875349033");
        card.executeTransaction(new Transaction(BigDecimal.valueOf(100), "Purchased groceries for the week.", LocalDate.parse("2024-02-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2000), "Received salary as my monthly income.", LocalDate.parse("2024-02-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(150), "Bought shoes for everyday comfort.", LocalDate.parse("2024-02-15"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(110), "Purchased groceries to keep the pantry stocked.", LocalDate.parse("2024-03-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2100), "Received salary as my monthly income.", LocalDate.parse("2024-03-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(250), "Bought a jacket to stay warm.", LocalDate.parse("2024-03-15"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(120), "Purchased groceries for daily meals.", LocalDate.parse("2024-04-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2200), "Received salary as my monthly income.", LocalDate.parse("2024-04-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(350), "Bought a phone for better connectivity.", LocalDate.parse("2024-04-15"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(130), "Purchased groceries to satisfy daily needs.", LocalDate.parse("2024-05-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2300), "Received salary as my monthly income.", LocalDate.parse("2024-05-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(450), "Bought a laptop for work and play.", LocalDate.parse("2024-05-15"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(140), "Purchased groceries as part of daily shopping.", LocalDate.parse("2024-06-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2400), "Received salary as my monthly income.", LocalDate.parse("2024-06-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(550), "Bought a watch to keep track of time.", LocalDate.parse("2024-06-15"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(150), "Purchased groceries for daily cooking.", LocalDate.parse("2024-07-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2500), "Received salary as my monthly income.", LocalDate.parse("2024-07-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(650), "Bought a bike for convenient commuting.", LocalDate.parse("2024-07-15"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(160), "Purchased groceries to fill the fridge.", LocalDate.parse("2024-08-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2600), "Received salary as my monthly income.", LocalDate.parse("2024-08-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(750), "Bought a camera to capture life's moments.", LocalDate.parse("2024-08-15"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(170), "Purchased groceries for everyday use.", LocalDate.parse("2024-09-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2700), "Received salary as my monthly income.", LocalDate.parse("2024-09-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(850), "Spent on furniture to enhance my home.", LocalDate.parse("2024-09-15"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(180), "Purchased groceries as part of routine shopping.", LocalDate.parse("2024-10-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2800), "Received salary as my monthly income.", LocalDate.parse("2024-10-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(950), "Bought a TV for family entertainment.", LocalDate.parse("2024-10-15"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(190), "Purchased groceries to keep my kitchen stocked.", LocalDate.parse("2024-11-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2900), "Received salary as my monthly income.", LocalDate.parse("2024-11-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(1050), "Spent on a sofa to add comfort to my living room.", LocalDate.parse("2024-11-15"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(200), "Purchased groceries for everyday consumption.", LocalDate.parse("2024-12-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3000), "Received salary as my monthly income.", LocalDate.parse("2024-12-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(1150), "Spent on a holiday trip to create lasting memories.", LocalDate.parse("2024-12-15"), Transaction.Direction.EXPENSE, card));
    }
}









 