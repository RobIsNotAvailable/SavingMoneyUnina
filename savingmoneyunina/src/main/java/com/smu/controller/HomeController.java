package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import java.util.List;
import com.smu.model.Transaction;

import com.smu.model.TransactionFilter;
import com.smu.model.Category;
import com.smu.model.User;
import com.smu.view.CardManager;
import com.smu.view.HomePanel;
import com.smu.view.UiUtil;
import com.smu.view.UiUtil.TriangleButton;

public class HomeController extends CardManagerController
{
    private HomePanel view;

    public HomeController(CardManager cardManager, HomePanel view, User user) 
    {
        super(cardManager, user);
        this.view = view;

        UiUtil.addListener(view.getFilterButton(), new FilterListener());
        UiUtil.addListener(view.getClearFilterButton(), new ClearFilterListener());
        UiUtil.addListener(view.getAllTransactionButton(), new AllTransactionsListener());

        updateButton();
        updateDetails();
        setFilterBoxes(user);
        initializeTable();
    }

    private class FilterListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            updateTable();
            view.getAllTransactionButton().setVisible(false);
        }
    }

    private class ClearFilterListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            view.clearErrorMessage();
            
            view.getFilterInitialDate().setValue(null);
            view.getFilterFinalDate().setValue(null);
            view.getFilterDirection().setSelectedIndex(0);
            view.getFilterCategory().setSelectedIndex(0);

            view.getAllTransactionButton().setVisible(true);
            initializeTable();
        }
    }

    private class AllTransactionsListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            updateTable();
            view.getAllTransactionButton().setVisible(false);
        }
    }

    private class CustomCardChangerListener extends CardChangerListener
    {
        public CustomCardChangerListener(TriangleButton button)
        {
            super(button);
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            super.actionPerformed(e);
            initializeTable();
        }
    }
    private void updateTable()
    {
        view.clearErrorMessage();
        try 
        {
            TransactionFilter filter = new TransactionFilter(view.getInitialDateValue(), view.getFinalDateValue(), view.getFilterDirectionValue(), view.getFilterCategoryValue());
            view.showTransactions(filter.filter(PaymentCardList.get(cardIndex).getTransactions()));
        }
        catch (Exception e) 
        {
            view.showErrorMessage("Date is not valid");
        }
    }

    private void initializeTable()
    {
        List<Transaction> list = PaymentCardList.get(cardIndex).getTransactions();
        List<Transaction> firstTenTransactions = list.stream().limit(10).toList();

        view.showTransactions(firstTenTransactions);
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
