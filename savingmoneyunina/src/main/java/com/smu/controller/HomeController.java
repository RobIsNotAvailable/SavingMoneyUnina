package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.smu.MainController;
import com.smu.model.PaymentCard;
import com.smu.model.User;
import com.smu.view.HomePanel;
import com.smu.view.UiUtil;
import com.smu.view.UiUtil.*;

public class HomeController 
{
    private ArrayList<PaymentCard> PaymentCardList;
    private int cardIndex = 0;

    private MainController main;
    private HomePanel view;

    public HomeController(MainController main, HomePanel view, User user) 
    {
        PaymentCardList = new ArrayList<PaymentCard>(user.getCards());
        this.main = main;
        this.view = view;
        
        UiUtil.addListener(view.getRightTriangleButton(), new CardChangerListener(view.getRightTriangleButton()));
        UiUtil.addListener(view.getLeftTriangleButton(), new CardChangerListener(view.getLeftTriangleButton()));
        UiUtil.addListener(view.getCardButton(), new CardListener());

        updateButton();
        updateDetails();
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
        }
    }

    private class CardListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("BUTTON PRESSED ");
        }
    }

    public void updateButton()
    {
        String cardType = getCorrespondingCard();
        ImageIcon cardImage = new ImageIcon(HomeController.class.getResource("/" + cardType + ".png"));
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
            return "postepay";

        if(PaymentCardList.get(cardIndex).getCardNumber().startsWith("5678"))
            return "hype";

        if(PaymentCardList.get(cardIndex).getCardNumber().startsWith("1111"))
            return "americanEspresso";

        return "default";
    }

}
