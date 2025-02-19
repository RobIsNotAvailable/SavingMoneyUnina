package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.smu.MainController;
import com.smu.model.PaymentCard;
import com.smu.model.User;
import com.smu.view.HomePanel;
import com.smu.view.RightTriangleButton;

public class HomeController 
{
    private ArrayList <PaymentCard> PaymentCardList;
    private int cardIndex = 0;

    private MainController main;
    private HomePanel view;

    public HomeController(MainController main, HomePanel view, User user) 
    {
        PaymentCardList = new ArrayList<PaymentCard>(user.getCards());
        this.main = main;
        this.view = view;
    
        this.view.addListener(view.rightTriangleButton, new ChangeCardListener(view.rightTriangleButton));
    }

    private class ChangeCardListener implements ActionListener 
    {
        JButton button;

        ChangeCardListener(JButton button)
        {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(button instanceof RightTriangleButton)
                cardIndex++;
            else    
                cardIndex--;

            cardIndex %= PaymentCardList.size(); 

            updateButton();
            updateDetails();
        }
    }

    public void updateButton()
    {
        String cardType = getCorrespondingCard();
        ImageIcon cardImage = new ImageIcon(HomeController.class.getResource("/"+cardType));
        view.centerButton.setIcon(cardImage);
    }

    public void updateDetails()
    {
        PaymentCard card = PaymentCardList.get(cardIndex);
        LocalDate now = LocalDate.now();

        view.updateDetails(card.getTotalMonthlyIncome(now), card.getTotalMonthlyExpense(now), card.getBalance());
    }

    private String getCorrespondingCard() 
    {
        if(PaymentCardList.get(cardIndex).getCardNumber().startsWith("1"))
            return "postepay.png";

        if(PaymentCardList.get(cardIndex).getCardNumber().startsWith("2"))
            return "hype.png";

        return "default.png";
    }

}
