package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.smu.MainController;
import com.smu.dao.ReportDAO;
import com.smu.model.Report;
import com.smu.model.User;
import com.smu.view.ReportPanel;
import com.smu.view.UiUtil;

public class ReportController extends DefaultController
{
    ReportPanel view;

    public ReportController(MainController main, ReportPanel view, User user) 
    {
        super(main, view, user);

        this.view = view;

        initializeDefaultListeners();

        UiUtil.addListener(view.getShowButton(), new showListener());
        view.getShowButton().doClick();
    }

    private class showListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try 
            {
                Report report = ReportDAO.get(PaymentCardList.get(cardIndex), view.getDateValue());
                view.showReport(report);
            } 
            catch (Exception e1) 
            {
                e1.printStackTrace();
            }
        }
    }
}
