package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

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

        initializeListeners(new ReportCardChangerListener(getRightButton()), new ReportCardChangerListener(getLeftButton()));

        UiUtil.addListener(view.getShowButton(), new ShowListener());
        updateDetails();
    }

    private class ShowListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            updateDetails();
        }
    } 

    private class ReportCardChangerListener extends CardChangerListener
    {
        ReportCardChangerListener(TriangleButton button) 
        {
            super(button);
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            super.actionPerformed(e);
            updateDetails();
        }
    }

    private void updateDetails()
    {
        view.resetMessage();

        try 
        {
            LocalDate reportDate = view.getDateValue();
            LocalDate firstValidDate = PaymentCardList.get(cardIndex).getFirstReportDate();            
            Report report = PaymentCardList.get(cardIndex).getReport(reportDate);

            if (reportDate.isBefore(firstValidDate)) 
                view.showErrorMessage("The selected month predates the card's registration");
            if (reportDate.isAfter(LocalDate.now()))
                view.showErrorMessage("We can't read the future yet");
            
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
        updateDetails();  
    }
}
