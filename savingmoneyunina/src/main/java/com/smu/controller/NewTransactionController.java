package com.smu.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.swing.JButton;

import com.smu.MainController;
import com.smu.model.CurrencyConverter;
import com.smu.model.PaymentCard;
import com.smu.model.Transaction;
import com.smu.model.User;
import com.smu.model.CurrencyConverter.Currency;
import com.smu.view.NewTransactionPanel;
import com.smu.view.UiUtil;

import com.smu.model.Transaction.Direction;

public class NewTransactionController extends DefaultController
{
    NewTransactionPanel view;
    public int directionCounter = 0;
    public int currencyCounter = 0;

    public NewTransactionController(MainController main, NewTransactionPanel view, User user) 
    {
        super(main, view, user);
        this.view = view;

        initializeDefaultListeners();

        UiUtil.addListener(view.getDirectionButton(), new DirectionListener());
        UiUtil.addListener(view.getCurrencyButton(), new CurrencyListener());
        UiUtil.addListener(view.getInsertButton(), new InsertListener());

        UiUtil.addKeyBinding(view.getInsertButton(), "ENTER");
    }

    private class DirectionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            directionCounter = (directionCounter + 1) % 2;
            changeDirectionButton();
        }
    }

    private class CurrencyListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            currencyCounter = (currencyCounter + 1) % 2;
            changeCurrencyButton();
        }
    }

    private class InsertListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            try 
            {
                BigDecimal amount = view.getAmountValue();
                Currency currency = view.getCurrencyValue();
                String description = view.getDescriptionValue();
                LocalDate now = LocalDate.now();
                Direction direction = view.getDirection();
                PaymentCard card = PaymentCardList.get(cardIndex);

                if (currency == Currency.USD)
                    amount = CurrencyConverter.convertUsdToEur(amount);

                if (direction == Direction.EXPENSE && amount.compareTo(card.getBalance()) > 0)
                    throw new Exception("This card doesn't have enough money");
                
                System.out.println("Eseguo: (" + amount + " " + description + " " + now + " " + direction + " " + card.getCardNumber() + ")");
                card.executeTransaction(new Transaction(amount, description, now, direction, card));
                updateCardDetails();
            } 
            catch (Exception exception) 
            {
                view.showErrorMessage(exception.getMessage());
            }
        }
    }


    private void changeDirectionButton()
    {
        JButton directionButton = view.getDirectionButton();
        JButton currencyButton = view.getCurrencyButton();

        if (directionCounter == 0) 
        {
            directionButton.setText("-");
            directionButton.setForeground(Color.WHITE);
            currencyButton.setForeground(Color.WHITE);
            view.getAmountField().setForeground(Color.WHITE);
        }
        else
        {
            directionButton.setText("+");
            directionButton.setForeground(UiUtil.CAPPUCCINO);
            currencyButton.setForeground(UiUtil.CAPPUCCINO);
            view.getAmountField().setForeground(UiUtil.CAPPUCCINO);
        }
    }

    private void changeCurrencyButton()
    {
        JButton button = view.getCurrencyButton();

        if (currencyCounter == 0) 
            button.setText("€");
        else
            button.setText("$");
    }

}
