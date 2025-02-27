package com.smu.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.Font;

import javax.swing.JButton;

import com.smu.MainController;
import com.smu.model.Transaction;
import com.smu.model.User;
import com.smu.view.NewTransactionPanel;
import com.smu.view.UiUtil;

import com.smu.model.Transaction.Direction;

public class NewTransactionController extends DefaultController
{
    NewTransactionPanel view;
    public static int directionCounter = 0;
    public static int currencyCounter = 0;


    public NewTransactionController(MainController main, NewTransactionPanel view, User user) 
    {
        super(main, view, user);
        this.view = view;

        initializeDefaultListeners();
        setDirectionButton();

        UiUtil.addListener(view.getDirectionButton(), new DirectionListener());
        UiUtil.addListener(view.getCurrencyButton(), new CurrencyListener());

    }

    private class DirectionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            directionCounter++;
            setDirectionButton();
        }
    }

    private class CurrencyListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            currencyCounter++;
            setCurrencyButton();
        }
    }

    private class InsertListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            BigDecimal amount = BigDecimal.valueOf(view.getAmount());

            try 
            {
                Direction direction = view.getDirection();
            } 
            catch (Exception e1) 
            {
                view.showErrorMessage(e1.getMessage());
            }

            
        }
        
    }


    private void setDirectionButton()
    {
        JButton button = view.getDirectionButton();

        if (directionCounter % 2 == 0) 
        {
            button.setText("+");
            button.setFont(new Font("Courier New", Font.BOLD, 50));
            button.setForeground(UiUtil.CAPPUCCINO);
            view.getAmountButton().setForeground(UiUtil.CAPPUCCINO);
        }
        else
        {
            button.setText("-");
            button.setFont(new Font("Courier New", Font.BOLD, 50));
            button.setForeground(Color.WHITE);
            view.getAmountButton().setForeground(Color.WHITE);
        }
    }

    private void setCurrencyButton()
    {
        JButton button = view.getCurrencyButton();

        if (currencyCounter % 2 == 0) 
            button.setText("EUR");
        else
            button.setText("USD");
    }

}
