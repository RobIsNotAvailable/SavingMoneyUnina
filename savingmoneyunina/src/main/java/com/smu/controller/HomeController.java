package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import java.util.List;
import com.smu.model.Transaction;

import com.smu.model.TransactionFilter;
import com.smu.MainController;
import com.smu.model.Category;
import com.smu.model.User;

import com.smu.view.HomePanel;
import com.smu.view.UiUtil;
import com.smu.view.UiUtil.TriangleButton;

public class HomeController extends DefaultController
{
    private HomePanel view;

    public HomeController(MainController main, HomePanel view, User user) 
    {
        super(main, view, user);
        this.view = view;

        initializeListeners(new HomeCardChangerListener(getRightButton()), new HomeCardChangerListener(getLeftButton()));
        
        UiUtil.addListener(view.getFilterButton(), new FilterListener());
        UiUtil.addListener(view.getClearFilterButton(), new ClearFilterListener());
        UiUtil.addListener(view.getAllTransactionsButton(), new AllTransactionsListener());

        setFilterBoxes(user);
        initializeTable();
    }

    @Override
    public void refresh()
    {
        super.refresh();
        initializeTable();
    }

    private class FilterListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            updateTable();
            view.getAllTransactionsButton().setVisible(false);
        }
    }

    private class ClearFilterListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            clearFilters();
            initializeTable();
        }
    }

    private class AllTransactionsListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            updateTable();
            view.getAllTransactionsButton().setVisible(false);
        }
    }

    private class HomeCardChangerListener extends CardChangerListener
    {
        public HomeCardChangerListener(TriangleButton button)
        {
            super(button);
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            super.actionPerformed(e);

            if(areFilters())
                updateTable();
            else
                initializeTable();
        }
    }
    
    private void updateTable()
    {
        view.resetMessage();
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
        List<Transaction> transactions = PaymentCardList.get(cardIndex).getTransactions();

        if (transactions.size() > 10)
        {
            transactions = transactions.stream().limit(10).toList();
            view.getAllTransactionsButton().setVisible(true);
        }
        else
        {
            view.getAllTransactionsButton().setVisible(false);
        }
            
        view.showTransactions(transactions);
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

    private void clearFilters() 
    {
        view.resetMessage();
        
        view.getFilterInitialDate().setValue(null);
        view.getFilterFinalDate().setValue(null);
        view.getFilterDirection().setSelectedIndex(0);
        view.getFilterCategory().setSelectedIndex(0);
    }

    private boolean areFilters()
    {
        return  view.getFilterInitialDate().getValue() != null || 
                view.getFilterFinalDate().getValue() != null || 
                view.getFilterDirection().getSelectedIndex() != 0 || 
                view.getFilterCategory().getSelectedIndex() != 0;
    }
}
