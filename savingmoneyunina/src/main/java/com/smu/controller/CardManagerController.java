package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.smu.model.PaymentCard;
import com.smu.model.User;
import com.smu.view.CardManager;
import com.smu.view.UiUtil;
import com.smu.view.UiUtil.TriangleButton;

public class CardManagerController
{
    protected CardManager cardManager;
    protected ArrayList<PaymentCard> PaymentCardList;
    protected int cardIndex = 0;

    public CardManagerController(CardManager view, User user) 
    {
        PaymentCardList = new ArrayList<PaymentCard>(user.getCards());

        this.cardManager = view;
    }

    protected class CardChangerListener implements ActionListener 
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

    protected class CardListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            PaymentCard card = PaymentCardList.get(cardIndex);
            cardManager.displayCardDetails(card.getCardNumber(), card.getPin(), card.getExpirationDate().format(DateTimeFormatter.ofPattern("MM/yy")), card.getCvv());
        }
    }

    public void updateButton()
    {
        String cardType = getCorrespondingCard();
        ImageIcon cardImage = new ImageIcon(new ImageIcon(HomeController.class.getResource("/" + cardType + ".png")).getImage().getScaledInstance(450, 280, java.awt.Image.SCALE_SMOOTH));
        cardManager.getCardButton().setIcon(cardImage);
    }

    public void updateDetails()
    {
        PaymentCard card = PaymentCardList.get(cardIndex);
        LocalDate now = LocalDate.now();

        cardManager.updateDetails(card.getTotalMonthlyIncome(now), card.getTotalMonthlyExpense(now), card.getBalance());
    }

    public void initializeDefaultListeners()
    {
        UiUtil.addListener(cardManager.getRightTriangleButton(), new CardChangerListener(cardManager.getRightTriangleButton()));
        UiUtil.addListener(cardManager.getLeftTriangleButton(), new CardChangerListener(cardManager.getLeftTriangleButton()));
        UiUtil.addListener(cardManager.getCardButton(), new CardListener());
    }

    public void initializeCustomListeners(CardListener cardListener, CardChangerListener rightButtonListener, CardChangerListener leftButtonListener)
    {
        UiUtil.addListener(cardManager.getRightTriangleButton(), rightButtonListener);
        UiUtil.addListener(cardManager.getLeftTriangleButton(), leftButtonListener);
        UiUtil.addListener(cardManager.getCardButton(), cardListener);
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
}
