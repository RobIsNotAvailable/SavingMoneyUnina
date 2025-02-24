package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import com.smu.model.PaymentCard;
import com.smu.model.TransactionFilter;
import com.smu.model.Category;
import com.smu.model.User;
import com.smu.view.HomePanel;
import com.smu.view.UiUtil;
import com.smu.view.UiUtil.*;

public class HomeController 
{
    private ArrayList<PaymentCard> PaymentCardList;
    public static int cardIndex = 0;

    private HomePanel view;

    public HomeController(HomePanel view, User user) 
    {
        PaymentCardList = new ArrayList<PaymentCard>(user.getCards());
        this.view = view;

        UiUtil.addListener(view.getRightTriangleButton(), new CardChangerListener(view.getRightTriangleButton()));
        UiUtil.addListener(view.getLeftTriangleButton(), new CardChangerListener(view.getLeftTriangleButton()));
        UiUtil.addListener(view.getCardButton(), new CardListener());
        UiUtil.addListener(view.getFilterButton(), new FilterListener());

        updateButton();
        updateDetails();
        setFilterBoxes(user);
        updateTable();
    }

    private class CardChangerListener implements ActionListener 
    {
        TriangleButton.Direction direction;

        CardChangerListener(TriangleButton button)
        {
            this.direction = button.getDirection();
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int length = PaymentCardList.size();
            if(direction == TriangleButton.Direction.RIGHT)
                cardIndex++;
            else    
                cardIndex--;

            cardIndex = (cardIndex + length) % length; 

            updateButton();
            updateDetails();
            updateTable();
        }
    }

    private class CardListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            PaymentCard card = PaymentCardList.get(cardIndex);
            view.displayCardDetails(card.getCardNumber(), card.getPin(), card.getExpirationDate().format(DateTimeFormatter.ofPattern("MM/yy")), card.getCvv());
        }
    }

    private class FilterListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            updateTable();
        }
    }

    public void updateButton()
    {
        String cardType = getCorrespondingCard();
        ImageIcon cardImage = new ImageIcon(new ImageIcon(HomeController.class.getResource("/" + cardType + ".png")).getImage().getScaledInstance(450, 280, java.awt.Image.SCALE_SMOOTH));
        view.getCardButton().setIcon(cardImage);
    }

    public void updateDetails()
    {
        PaymentCard card = PaymentCardList.get(cardIndex);
        LocalDate now = LocalDate.now();

        view.updateDetails(card.getTotalMonthlyIncome(now), card.getTotalMonthlyExpense(now), card.getBalance());
    }

    private String getCorrespondingCard() 
    {
        if(PaymentCardList.get(cardIndex).getCardNumber().startsWith("1234"))
            return "pastapay";

        if(PaymentCardList.get(cardIndex).getCardNumber().startsWith("5678"))
            return "viza";

        if(PaymentCardList.get(cardIndex).getCardNumber().startsWith("1111"))
            return "americanespresso";

        return "smucard";
    }

    private void updateTable()
    {
        try 
        {
            TransactionFilter filter = new TransactionFilter(view.getInitialDateValue(), view.getFinalDateValue(), view.getFilterDirectionValue(), view.getFilterCategoryValue());
            view.showTransactions(filter.filter(PaymentCardList.get(cardIndex).getTransactions()));
            System.out.println("filter is: " + view.getInitialDateValue() + " " + view.getFinalDateValue() + " " + view.getFilterDirectionValue() + " " + view.getFilterCategoryValue());
        }
        catch (Exception e) 
        {
            view.showErrorMessage("Date is not valid");
        }

    }

    private void setFilterBoxes(User user)
    {
        JComboBox<Category> categories = view.getFilterCategory();

        categories.addItem(new Category("All categories", null));

        for (Category c : user.getCategories())
        {
            categories.addItem(c);
        }

        JComboBox<String> directions = view.getFilterDirection();
        directions.addItem("All directions");
        directions.addItem("Income");
        directions.addItem("Expense");
    }
}
