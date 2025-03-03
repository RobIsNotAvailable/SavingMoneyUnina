package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.smu.MainController;
import com.smu.model.Report;
import com.smu.model.User;
import com.smu.view.ReportPanel;
import com.smu.view.UiUtil;
import com.smu.view.UiUtil.TriangleButton;

public class ReportController extends DefaultController
{
    ReportPanel view;

    public ReportController(MainController main, ReportPanel view, User user) 
    {
        super(main, view, user);

        this.view = view;

        initializeCustomListeners(new CardListener(), new ReportCardChangeListener(getRighttButton()), new ReportCardChangeListener(getLeftButton()));

        UiUtil.addListener(view.getShowButton(), new showListener());
        view.getShowButton().doClick();
        initializeDetails();
    }

    private class showListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            initializeDetails();
        }
    } 

    private class ReportCardChangeListener extends CardChangerListener
    {
        ReportCardChangeListener(TriangleButton button) 
        {
            super(button);
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            super.actionPerformed(e);
            initializeDetails();
        }
    }

    private void initializeDetails()
    {
        try 
        {
            Report report = PaymentCardList.get(cardIndex).getReport(view.getDateValue());
            view.showReport(report);
        } 
        catch (Exception e) 
        {
            view.showErrorMessage(e.getMessage());
        }
    }

    @Override
    public void refresh()
    {
        super.refresh();
        initializeDetails();  
    }
}
