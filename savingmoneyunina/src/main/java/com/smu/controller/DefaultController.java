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
        UiUtil.addListener(cardManager.getEyeButton(), new EyeListener());
        UiUtil.addListener(cardManager.getCardButton(), new CardListener());
    }

    protected class CardChangerListener implements ActionListener 
    {
        private TriangleButton.Direction direction;

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

    protected class EyeListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Boolean isHidden = cardManager.getPinLabel().getText().contains("***");
            PaymentCard card = PaymentCardList.get(cardIndex);

            if (isHidden)
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

    protected void initializeListeners(CardChangerListener rightButtonListener, CardChangerListener leftButtonListener)
    {
        UiUtil.addListener(cardManager.getRightTriangleButton(), rightButtonListener);
        UiUtil.addListener(cardManager.getLeftTriangleButton(), leftButtonListener);
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

    public TriangleButton getLeftButton() { return cardManager.getLeftTriangleButton(); }

    public TriangleButton getRightButton() { return cardManager.getRightTriangleButton(); }
}
