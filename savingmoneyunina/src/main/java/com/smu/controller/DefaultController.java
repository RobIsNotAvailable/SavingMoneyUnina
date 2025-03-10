package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.smu.MainController;
import com.smu.model.PaymentCard;
import com.smu.model.User;
import com.smu.view.CardManager;
import com.smu.view.DefaultPanel;
import com.smu.view.UiUtil;
import com.smu.view.UiUtil.TriangleButton;

public class DefaultController
{
    protected User user;
    protected CardManager cardManager;
    protected ArrayList<PaymentCard> PaymentCardList;
    protected static int cardIndex = 0;

    public DefaultController(MainController main, DefaultPanel view, User user) 
    {
        this.user = user;

        PaymentCardList = new ArrayList<PaymentCard>(user.getCards());

        this.cardManager = view.getCardManager();

        new NavbarController(main, view.getNavbar());

        updateCardImage();
        updateCardDetails();
        UiUtil.addListener(cardManager.getEyeButton(), new eyeListener());
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

            updateCardImage();
            updateCardDetails();
        }
    }

    protected class CardListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            PaymentCard card = PaymentCardList.get(cardIndex);
            cardManager.displayCardDetails(card.getCardNumber(), card.getExpirationDate().format(DateTimeFormatter.ofPattern("MM/yy")));
        }
    }

    protected class eyeListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String isHidden = cardManager.getPinLabel().getText();
            PaymentCard card = PaymentCardList.get(cardIndex);

            if (isHidden.contains("***"))
                cardManager.showSensitiveData(card.getPin(), card.getCvv());
            else
                cardManager.hideSensitiveData();
        }
    }


    private void updateCardImage()
    {
        String cardType = getCorrespondingCard();
        ImageIcon cardImage = new ImageIcon(new ImageIcon(HomeController.class.getResource("/" + cardType + ".png")).getImage().getScaledInstance(450, 280, java.awt.Image.SCALE_SMOOTH));
        cardManager.getCardButton().setIcon(cardImage);
    }

    private void updateCardDetails()
    {
        PaymentCard card = PaymentCardList.get(cardIndex);
        LocalDate now = LocalDate.now();

        cardManager.updateDetails(card.getTotalMonthlyIncome(now), card.getTotalMonthlyExpense(now), card.getBalance());
    }

    public void updateCard()
    {
        updateCardDetails();
        updateCardImage();
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
        if(PaymentCardList.get(cardIndex).getCardNumber().startsWith("1003"))
            return "pastapay";

        if(PaymentCardList.get(cardIndex).getCardNumber().startsWith("3498"))
            return "viza";

        if(PaymentCardList.get(cardIndex).getCardNumber().startsWith("2402"))
            return "americanespresso";

        return "smucard";
    }

    public void refresh() 
    {
        PaymentCardList = new ArrayList<PaymentCard>(user.getCards());
        updateCard();
    }

    protected TriangleButton getLeftButton() { return cardManager.getLeftTriangleButton(); }

    protected TriangleButton getRighttButton() { return cardManager.getRightTriangleButton(); }
}
