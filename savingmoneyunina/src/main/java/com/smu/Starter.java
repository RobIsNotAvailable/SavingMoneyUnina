package com.smu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.smu.dao.PaymentCardDAO;
import com.smu.model.Category;
import com.smu.model.Family;
import com.smu.model.PaymentCard;
import com.smu.model.Transaction;
import com.smu.model.User;

public class Starter 
{
    //control insert if matches
    //insert transaction should be functioning 

    public static final String MENU_STRING = "1) Family Details\t2) Cards \t\t3) Transactions \n\n4) Categories\t\t5) Reports\t\t6) Exit";
    
    public static void main( String[] args)
    {
        test();
    }

    public static void test()
    {
        PaymentCard card = PaymentCardDAO.get("1234567812345678");

        System.out.println("Insert the amount:");
        double amount = Double.parseDouble(System.console().readLine());

        System.out.println("Insert the description:");
        String description = System.console().readLine();

        System.out.println("Select the direction: \n1) Income\t\t 2) Expense");
        int direction = Integer.parseInt(System.console().readLine());
        Transaction.Direction dir = (direction == 1) ? Transaction.Direction.income : Transaction.Direction.expense;

        System.out.println("STAMPA BEFORE TRANSACTION");
        System.out.println(card.getReport(LocalDate.now()));

        Transaction t = new Transaction(BigDecimal.valueOf(amount), description, LocalDate.now(), dir, card);
        card.executeTransaction(t);

        System.out.println("STAMPA AFTER TRANSACTION");
        System.out.println(card.getReport(LocalDate.now()));
    }

    public static void menu()
    {
        System.out.println("Welcome to Saving Money Unina!");

        User user = null;

        // boolean login = false;
        // while (!login)
        // {
        //     System.out.println("Insert your username:");
        //     String username = System.console().readLine();

        //     System.out.println("Insert your password:");
        //     String password = new String(System.console().readLine()); //readPassword() renderebbe invisibile l'input ma insomma

        //     user = new User(username, password);

        //     if (user.verify())
        //         login = true;
        //     else
        //         System.out.println("Invalid credentials, please try again.");
        // }

        user = new User("alice", "Password123");

        System.out.println(MENU_STRING);

        int choice = Integer.parseInt(System.console().readLine());

        while (choice != 6)
        {
            switch (choice)
            {
                case 1:
                    User member = null;
                    System.out.println("Select a family member to see their details (0 to go back):");
                    try
                    {
                        member = memberSelector(user.getFamily());
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                        break;
                    }
                    
                    if (member.getUsername().equals(user.getUsername()))
                    {
                        System.out.println("Use the appropriate menus to see your details >:(");
                    }
                    else
                    {
                        System.out.println("Would you like to see the transactions or a specific report?\n\n 1) Transactions\t2) Report\t\t3) Back");
                        int choice2 = Integer.parseInt(System.console().readLine());
                        if (choice2 == 1)
                        {
                            transactionsPrinter(member);
                        }
                        else if (choice2 == 2)
                        {
                            reportPrinter(member);
                        }
                    }
                    break;
                case 2:
                    List<PaymentCard> cards = user.getCards();
                    for (PaymentCard c : cards)
                    {
                        System.out.println(c);
                    }
                    break;
                case 3:
                    System.out.println("Would you like to see your transactions or insert one?\n\n 1) Show transactions\t2) Insert transaction\t3) Back");
                    int choice3 = Integer.parseInt(System.console().readLine());

                    if (choice3 == 1)
                    {
                        transactionsPrinter(user);
                    }
                    else if (choice3 == 2)
                    {
                        System.out.println("Select the card you used:");
                        PaymentCard card = null;
                        try
                        {
                            card = cardSelector(user);
                        }
                        catch (Exception e)
                        {
                            System.out.println(e.getMessage());
                            return;
                        }
                        if (card != null)
                        {
                            System.out.println("Insert the amount:");
                            double amount = Double.parseDouble(System.console().readLine());

                            System.out.println("Insert the description:");
                            String description = System.console().readLine();

                            System.out.println("Select the direction: \n1) Income\t\t 2) Expense");
                            int direction = Integer.parseInt(System.console().readLine());
                            Transaction.Direction dir = (direction == 1) ? Transaction.Direction.income : Transaction.Direction.expense;

                            Transaction t = new Transaction(BigDecimal.valueOf(amount), description, LocalDate.now(), dir, card);
                            
                            card.executeTransaction(t);
                        }
                        else
                        {
                            System.out.println("Card not found");
                        }
                    }
                    break;
                case 4:
                    System.out.println(user.getCategories());
                    break;
                case 5:
                    reportPrinter(user);
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }

            System.out.println(MENU_STRING);
            choice = Integer.parseInt(System.console().readLine());
        }
    }

    private static void transactionsPrinter(User u) 
    {
        System.out.println("Select a card to see its transactions:");
        PaymentCard c = null;
        try
        {
            c = cardSelector(u);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }
        LocalDate beginDate = null;
        LocalDate endDate = null;

        System.out.println("Filter transactions by time? \n\n 1) Select a time frame\t\t2) No");
        int choice = Integer.parseInt(System.console().readLine());
        if (choice == 1)
        {
            System.out.println("Enter two dates for the time frame (yyyy-mm-dd):");
            beginDate = LocalDate.parse(System.console().readLine());
            endDate = LocalDate.parse(System.console().readLine());
        }
        else if (choice == 2)
        {
            beginDate = LocalDate.of(2000,1, 1);
            endDate = LocalDate.now();
        }

        System.out.println("Filter transactions by category? \n\n 1) Select a category\t2) No");
        choice = Integer.parseInt(System.console().readLine());
        Category category = null;
        if (choice == 1)
        {
            try
            {
                category = categorySelector(u);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                return;
            }
        }

        System.out.println("Show expenses, incomes or both? \n\n 1) Expenses\t\t2) Incomes\t\t3) Both");
        choice = Integer.parseInt(System.console().readLine());
        if (category == null)
        {
            if (choice == 1)
            {
                for (Transaction t : c.getTransactions(beginDate, endDate, Transaction.Direction.expense))
                {
                    System.out.println(t);
                }
            }
            else if (choice == 2)
            {
                for (Transaction t : c.getTransactions(beginDate, endDate, Transaction.Direction.income))
                {
                    System.out.println(t);
                }
            }
            else
            {
                for (Transaction t : c.getTransactions(beginDate, endDate))
                {
                    System.out.println(t);
                }
            }
        }
        else
        {
            if (choice == 1)
            {
                for (Transaction t : c.getTransactions(beginDate, endDate, category, Transaction.Direction.expense))
                {
                    System.out.println(t);
                }
            }
            else if (choice == 2)
            {
                for (Transaction t : c.getTransactions(beginDate, endDate, category, Transaction.Direction.income))
                {
                    System.out.println(t);
                }
            }
            else
            {
                for (Transaction t : c.getTransactions(beginDate, endDate, category))
                {
                    System.out.println(t);
                }
            }
        }
    }

    private static PaymentCard cardSelector(User u) throws Exception
    {
        ArrayList<PaymentCard> cards = new ArrayList<PaymentCard>(u.getCards());
        int i = 1;
        for (PaymentCard c : cards)
        {
            System.out.println(i++ + ") " + c.getCardNumber());
        }
        i = Integer.parseInt(System.console().readLine());

        if (i > cards.size())
        {
            throw new Exception("Invalid card selection");
        }

        return cards.get(i-1);
    }

    private static Category categorySelector(User u) throws Exception
    {
        ArrayList<Category> categories = new ArrayList<Category>(u.getCategories());
        int i = 1;
        for (Category c : categories)
        {
            System.out.println(i++ + ") " + c.getName());
        }
        i = Integer.parseInt(System.console().readLine());

        if (i > categories.size())
        {
            throw new Exception("Invalid category selection");
        }

        return categories.get(i-1);
    }

    private static User memberSelector(Family f) throws Exception
    {
        ArrayList<User> members = new ArrayList<User>(f.getMembers());
        int i = 1;
        for (User u : members)
        {
            System.out.println(i++ + ") " + u.getUsername());
        }
        i = Integer.parseInt(System.console().readLine());

        if (i > members.size())
        {
            throw new Exception("Invalid member selection");
        }
        else if (i == 0)
        {
            throw new Exception("");
        }


        return members.get(i-1);
    } 

    private static void reportPrinter(User u)
    {
        System.out.println("Select a card to see its report:");
        PaymentCard c = null;
        try
        {
            c = cardSelector(u);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Enter a date (yyyy-mm-dd):");
        LocalDate date = LocalDate.parse(System.console().readLine());

        System.out.println(c.getReport(date));
    }
}