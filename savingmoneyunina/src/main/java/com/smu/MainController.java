package com.smu;

import java.awt.CardLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.smu.controller.FamilyController;
import com.smu.controller.HomeController;
import com.smu.controller.LoginController;
import com.smu.controller.NewTransactionController;
import com.smu.controller.ReportController;
import com.smu.dao.PaymentCardDAO;
import com.smu.model.PaymentCard;
import com.smu.model.Transaction;
import com.smu.model.User;
import com.smu.view.FamilyPanel;
import com.smu.view.HomePanel;
import com.smu.view.LoginPanel;
import com.smu.view.MainFrame;
import com.smu.view.NewTransactionPanel;
import com.smu.view.ReportPanel;

public class MainController 
{
    private MainFrame mainFrame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private HomeController homeController;
    private NewTransactionController newTransactionController;
    private FamilyController familyController;
    private ReportController reportController;

    public MainController() 
    {
        mainFrame = new MainFrame();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.setOpaque(false);

        
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
        homeController = new HomeController(this, homePanel, user);

        NewTransactionPanel newTransactionPanel = new NewTransactionPanel();
        newTransactionController = new NewTransactionController(this, newTransactionPanel, user);

        FamilyPanel familyPanel = new FamilyPanel();
        familyController = new FamilyController(this, familyPanel, user);

        ReportPanel reportPanel = new ReportPanel();
        reportController = new ReportController(this, reportPanel, user);

        mainPanel.add(homePanel, "Home");
        mainPanel.add(newTransactionPanel, "New transaction");
        mainPanel.add(familyPanel, "Family");
        mainPanel.add(reportPanel, "Reports");
    }

    public static void main(String[] args) 
    { 
        SwingUtilities.invokeLater(MainController::new);
    }

    public HomeController getHomeController() { return homeController; }

    public NewTransactionController getNewTransactionController() { return newTransactionController;}

    public FamilyController getFamilyController() { return familyController;}

    public ReportController getReportController() { return reportController;}

    //used for populating the db only for first time running of the application
    public static void populate()
    {
        /* ************************************************************JEFF FIRST CARD****************************************************************** */
        PaymentCard card;
        card = PaymentCardDAO.get("1003789453846743");

        card.executeTransaction(new Transaction(BigDecimal.valueOf(2100), "Received salary as my monthly income.", LocalDate.parse("2024-02-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(250), "Bought headphones to enjoy high-quality sound.", LocalDate.parse("2024-02-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(80), "Had lunch during a quick break.", LocalDate.parse("2024-02-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1700), "Received payment for furniture sold.", LocalDate.parse("2024-03-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(130), "Purchased groceries to replenish the pantry.", LocalDate.parse("2024-03-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(400), "Bought a tablet for both work and leisure.", LocalDate.parse("2024-03-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(90), "Had dinner with friends at a cozy restaurant.", LocalDate.parse("2024-03-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1600), "Received payment for a bike sale.", LocalDate.parse("2024-04-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(140), "Purchased groceries for daily needs.", LocalDate.parse("2024-04-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(350), "Bought a camera to capture special moments.", LocalDate.parse("2024-04-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(110), "Had lunch at a local eatery.", LocalDate.parse("2024-04-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1500), "Received vacation funds to plan my holiday.", LocalDate.parse("2024-05-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2400), "Received salary as my monthly income.", LocalDate.parse("2024-05-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(300), "Bought a smartwatch to monitor my fitness.", LocalDate.parse("2024-05-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(120), "Enjoyed dinner at an elegant restaurant.", LocalDate.parse("2024-05-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1400), "Received income from concert ticket sales.", LocalDate.parse("2024-06-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(170), "Purchased groceries to restock my fridge.", LocalDate.parse("2024-06-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(450), "Bought a gaming console for home entertainment.", LocalDate.parse("2024-06-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(130), "Had a quick lunch at a nearby cafe.", LocalDate.parse("2024-06-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1300), "Received a gym membership credit.", LocalDate.parse("2024-07-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(180), "Purchased clothing from a trendy boutique.", LocalDate.parse("2024-07-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2600), "Received payment for freelance work.", LocalDate.parse("2024-07-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(500), "Spent money on new furniture for my apartment.", LocalDate.parse("2024-07-15"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1200), "Received a performance bonus at work.", LocalDate.parse("2024-08-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(190), "Purchased books to expand my personal library.", LocalDate.parse("2024-08-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(550), "Spent on home decor to refresh my living space.", LocalDate.parse("2024-08-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(150), "Bought concert tickets for an upcoming show.", LocalDate.parse("2024-08-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1100), "Received investment returns as dividend payments.", LocalDate.parse("2024-09-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2800), "Received project payment for recent work.", LocalDate.parse("2024-09-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(600), "Bought appliances to upgrade my home.", LocalDate.parse("2024-09-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(160), "Purchased theater tickets for a live performance.", LocalDate.parse("2024-09-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(1000), "Received dividends from my investments.", LocalDate.parse("2024-10-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(210), "Purchased sports equipment for my fitness routine.", LocalDate.parse("2024-10-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(650), "Bought kitchenware to upgrade my cooking space.", LocalDate.parse("2024-10-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(170), "Indulged in a spa treatment for relaxation.", LocalDate.parse("2024-10-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(900), "Received rental income from my property.", LocalDate.parse("2024-11-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(220), "Spent money on thoughtful gifts for loved ones.", LocalDate.parse("2024-11-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(700), "Purchased garden supplies to improve my garden.", LocalDate.parse("2024-11-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(180), "Bought museum tickets for a cultural outing.", LocalDate.parse("2024-11-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(800), "Received royalties from published works.", LocalDate.parse("2025-01-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3100), "Earned a consulting fee from a major client.", LocalDate.parse("2025-01-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(750), "Invested in home improvement projects.", LocalDate.parse("2025-01-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(190), "Purchased zoo tickets for a family outing.", LocalDate.parse("2025-01-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(700), "Received cashback rewards on recent purchases.", LocalDate.parse("2025-02-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(240), "Bought office supplies to replenish the stock.", LocalDate.parse("2025-02-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3200), "Received a project bonus for excellent work.", LocalDate.parse("2025-02-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(200), "Purchased theme park tickets for a fun day out.", LocalDate.parse("2025-02-20"), Transaction.Direction.EXPENSE, card));

        card.executeTransaction(new Transaction(BigDecimal.valueOf(5000), "Received investment returns from stock market.", LocalDate.parse("2025-03-25"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3000), "Received dividends from mutual funds.", LocalDate.parse("2025-03-25"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(2000), "Invested in new startup company.", LocalDate.parse("2025-03-25"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(1500), "Purchased additional shares in existing portfolio.", LocalDate.parse("2025-03-25"), Transaction.Direction.EXPENSE, card));

        /***************************************************************************JEFF SECOND CARD******************************************************************** */

        card = PaymentCardDAO.get("2402740937654901");
        card.executeTransaction(new Transaction(BigDecimal.valueOf(210), "Purchased groceries for the week.", LocalDate.parse("2024-04-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3200), "Received salary as my monthly income.", LocalDate.parse("2024-04-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(450), "Bought a new laptop for work.", LocalDate.parse("2024-05-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(220), "Purchased groceries for daily needs.", LocalDate.parse("2024-06-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3300), "Received salary as my monthly income.", LocalDate.parse("2024-06-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(500), "Bought a new phone.", LocalDate.parse("2024-07-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(230), "Purchased groceries for the week.", LocalDate.parse("2024-08-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3400), "Received salary as my monthly income.", LocalDate.parse("2024-08-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(550), "Bought a new TV.", LocalDate.parse("2024-09-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(240), "Purchased groceries for daily needs.", LocalDate.parse("2024-10-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3500), "Received salary as my monthly income.", LocalDate.parse("2024-10-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(600), "Bought a new tablet.", LocalDate.parse("2024-11-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(250), "Purchased groceries for the week.", LocalDate.parse("2024-12-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3600), "Received salary as my monthly income.", LocalDate.parse("2024-12-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(650), "Bought a new camera.", LocalDate.parse("2025-01-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(260), "Purchased groceries for daily needs.", LocalDate.parse("2025-02-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3700), "Received salary as my monthly income.", LocalDate.parse("2025-02-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(700), "Bought a new smartwatch.", LocalDate.parse("2025-03-15"), Transaction.Direction.EXPENSE, card));
    
        //***************************************************************************JEFF THIRS CARD******************************************************************** */
        card = PaymentCardDAO.get("3498734875349033");
        card.executeTransaction(new Transaction(BigDecimal.valueOf(210), "Purchased groceries for the week.", LocalDate.parse("2024-04-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3200), "Received salary as my monthly income.", LocalDate.parse("2024-04-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(450), "Bought a new laptop for work.", LocalDate.parse("2024-05-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(220), "Purchased groceries for daily needs.", LocalDate.parse("2024-06-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3300), "Received salary as my monthly income.", LocalDate.parse("2024-06-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(500), "Bought a new phone.", LocalDate.parse("2024-07-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(230), "Purchased groceries for the week.", LocalDate.parse("2024-08-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3400), "Received salary as my monthly income.", LocalDate.parse("2024-08-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(550), "Bought a new TV.", LocalDate.parse("2024-09-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(240), "Purchased groceries for daily needs.", LocalDate.parse("2024-10-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3500), "Received salary as my monthly income.", LocalDate.parse("2024-10-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(600), "Bought a new tablet.", LocalDate.parse("2024-11-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(250), "Purchased groceries for the week.", LocalDate.parse("2024-12-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3600), "Received salary as my monthly income.", LocalDate.parse("2024-12-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(650), "Bought a new camera.", LocalDate.parse("2025-01-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(260), "Purchased groceries for daily needs.", LocalDate.parse("2025-02-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(3700), "Received salary as my monthly income.", LocalDate.parse("2025-02-10"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(700), "Bought a new smartwatch.", LocalDate.parse("2025-03-15"), Transaction.Direction.EXPENSE, card));
    


        //3498567345687564 frankiewastaken
        card = PaymentCardDAO.get("3498567345687564");

        card.executeTransaction(new Transaction(BigDecimal.valueOf(50), "Watched a movie at the local cinema.", LocalDate.parse("2024-04-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(100), "Bought new clothes for the summer.", LocalDate.parse("2024-05-20"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(75), "Had dinner at a fancy restaurant.", LocalDate.parse("2024-06-10"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(120), "Purchased groceries for the week.", LocalDate.parse("2024-07-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(60), "Bought a new book.", LocalDate.parse("2024-08-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(90), "Went to a concert.", LocalDate.parse("2024-09-25"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(110), "Bought a new pair of shoes.", LocalDate.parse("2024-10-10"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(80), "Watched a movie at the local cinema.", LocalDate.parse("2024-11-20"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(130), "Purchased Christmas gifts.", LocalDate.parse("2024-12-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(70), "Had a New Year's Eve dinner.", LocalDate.parse("2025-01-01"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(50), "Bought Valentine's Day flowers.", LocalDate.parse("2025-02-14"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(100), "Went shopping for new clothes.", LocalDate.parse("2025-03-10"), Transaction.Direction.EXPENSE, card));

        
        //1003394875377732 riga_tony
        card = PaymentCardDAO.get("1003394875377732");

        
        card.executeTransaction(new Transaction(BigDecimal.valueOf(50), "Watched a movie at the local cinema.", LocalDate.parse("2024-04-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(200), "Received a bonus at work.", LocalDate.parse("2024-05-20"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(75), "Had dinner at a fancy restaurant.", LocalDate.parse("2024-06-10"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(120), "Purchased groceries for the week.", LocalDate.parse("2024-07-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(60), "Bought a new book.", LocalDate.parse("2024-08-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(300), "Received payment for freelance work.", LocalDate.parse("2024-09-25"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(110), "Bought a new pair of shoes.", LocalDate.parse("2024-10-10"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(80), "Watched a movie at the local cinema.", LocalDate.parse("2024-11-20"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(130), "Purchased Christmas gifts.", LocalDate.parse("2024-12-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(250), "Received a holiday bonus.", LocalDate.parse("2025-01-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(50), "Bought Valentine's Day flowers.", LocalDate.parse("2025-02-14"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(400), "Received a tax refund.", LocalDate.parse("2025-03-10"), Transaction.Direction.INCOME, card));


        //2402432187654321 bobyyyy
        card = PaymentCardDAO.get("2402432187654321");

        card.executeTransaction(new Transaction(BigDecimal.valueOf(50), "Watched a movie at the local cinema.", LocalDate.parse("2024-04-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(200), "Received a bonus at work.", LocalDate.parse("2024-05-20"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(75), "Had dinner at a fancy restaurant.", LocalDate.parse("2024-06-10"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(120), "Purchased groceries for the week.", LocalDate.parse("2024-07-05"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(60), "Bought a new book.", LocalDate.parse("2024-08-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(300), "Received payment for freelance work.", LocalDate.parse("2024-09-25"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(110), "Bought a new pair of shoes.", LocalDate.parse("2024-10-10"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(80), "Watched a movie at the local cinema.", LocalDate.parse("2024-11-20"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(130), "Purchased Christmas gifts.", LocalDate.parse("2024-12-15"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(250), "Received a holiday bonus.", LocalDate.parse("2025-01-01"), Transaction.Direction.INCOME, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(50), "Bought Valentine's Day flowers.", LocalDate.parse("2025-02-14"), Transaction.Direction.EXPENSE, card));
        card.executeTransaction(new Transaction(BigDecimal.valueOf(400), "Received a tax refund.", LocalDate.parse("2025-03-10"), Transaction.Direction.INCOME, card));}
}









 