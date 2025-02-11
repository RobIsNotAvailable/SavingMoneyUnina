package com.smu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.smu.dao.PaymentCardDAO;
import com.smu.dao.ReportDAO;
import com.smu.dao.IncomeDetailsDAO;
import com.smu.dao.TransactionDAO;
import com.smu.model.Category;
import com.smu.model.Family;
import com.smu.model.IncomeDetails;
import com.smu.model.PaymentCard;
import com.smu.model.Report;
import com.smu.model.Transaction;
import com.smu.model.User;

public class Starter 
{
    public static void main( String[] args)
    {
        test();
    }

    public static void test()
    {
        PaymentCard card = PaymentCardDAO.get("1234567812345678");

        Report r = ReportDAO.get(card, LocalDate.parse("2025-02-01"));
        System.out.println(r.toString());
    }

    public static void menu()
    {
        System.out.println("Welcome to Saving Money Unina!");

        boolean login = false;
        User user = null;
        
        while (!login)
        {
            System.out.println("Insert your username:");
            String username = System.console().readLine();

            System.out.println("Insert your password:");
            String password = new String(System.console().readLine()); //readPassword() renderebbe invisibile l'input ma insomma

            user = new User(username, password);

            if (user.verify())
                login = true;
            else
                System.out.println("Invalid credentials, please try again.");
        }

        System.out.println("1) Family Details  4) Categories \n\n 2) Cards 5) Reports \n\n 3) Transactions  6) Exit");

        int choice = Integer.parseInt(System.console().readLine());

        while (choice != 6)
        {
            switch (choice)
            {
                case 1:
                    Family family = user.getFamily();
                    System.out.println(family.toString());
                    break;
                case 2:
                    List<PaymentCard> cards = user.getCards();
                    for (PaymentCard card : cards)
                    {
                        System.out.println(card.toString());
                    }
                    break;
                case 3:
                    List<PaymentCard> cardz = user.getCards();
                    for (PaymentCard card : cardz)
                    {
                        List<Transaction> transactions = card.getTransactions();
                        for (Transaction transaction : transactions)
                        {
                            System.out.println(transaction.toString());
                        }
                    }
                    break;
                case 4:
                    List<Category> categories = user.getCategories();
                    for (Category category : categories)
                    {
                        System.out.println(category.toString());
                    }
                    break;
                case 5:
                    System.out.println("choose a month and a card");
                    System.out.println("insert the card number:");

                    String cardNumber = System.console().readLine();
                    PaymentCard card = PaymentCardDAO.get(cardNumber);

                    System.out.println("insert month(YYYY-MM-DD)");
                    LocalDate month = LocalDate.parse(System.console().readLine());

                    Report r = ReportDAO.get(card, month);
                    System.out.println(r.toString());

                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }

            System.out.println("1) Family Details  4) Categories \n\n 2) Cards 5) Reports \n\n 3) Transactions  6) Exit");

            choice = Integer.parseInt(System.console().readLine());
        }
    }
}