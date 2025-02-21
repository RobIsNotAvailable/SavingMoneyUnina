package com.smu.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.smu.MainController;
import com.smu.model.PaymentCard;
import com.smu.model.User;
import com.smu.view.HomePanel;
import com.smu.view.MainFrame;
import com.smu.view.UiUtil;
import com.smu.view.UiUtil.*;

public class HomeController 
{
    private ArrayList<PaymentCard> PaymentCardList;
    public static int cardIndex = 0;

    private MainController main;
    private HomePanel view;
    private User user;

    public HomeController(MainController main, HomePanel view, User user) 
    {
        PaymentCardList = new ArrayList<PaymentCard>(user.getCards());
        this.main = main;
        this.view = view;
        this.user = user; 

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
            updateTable();
        }
    }

    private class CardListener implements ActionListener
    {
        private MainFrame frame;

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (frame != null && frame.isDisplayable()) 
                frame.dispose();

            frame = new MainFrame();
            frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            
            
            PaymentCard card = user.getCards().get(cardIndex); 

            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
            detailsPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
            detailsPanel.setOpaque(false);

            JLabel titLabel = UiUtil.createStyledLabel("Card Details"); 
            JLabel numberLabel = UiUtil.createStyledLabel("Number: " + card.getCardNumber()); 
            JLabel pinLabel = UiUtil.createStyledLabel("Pin: " + card.getPin()); 
            JLabel expiryLabel = UiUtil.createStyledLabel("Expiration Date: " + card.getExpirationDate().toString()); 
            JLabel cvvLabel = UiUtil.createStyledLabel("CVV: " + card.getCvv());

            detailsPanel.add(titLabel);
            detailsPanel.add(numberLabel);
            detailsPanel.add(pinLabel);
            detailsPanel.add(cvvLabel);
            detailsPanel.add(expiryLabel);

            frame.getContentPane().add(detailsPanel, BorderLayout.CENTER);
            
            // Pack and show the frame
            frame.pack();
            frame.setLocationRelativeTo(null); // centers the frame
            frame.setVisible(true);
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

    private void updateTable()
    {
        view.createTable(user.getCards().get(cardIndex).getTransactions());
    }
}
