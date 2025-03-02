package com.smu.controller;

import com.smu.MainController;
import com.smu.model.User;
import com.smu.view.ReportPanel;

public class ReportController extends DefaultController
{
    ReportPanel view;

    public ReportController(MainController main, ReportPanel view, User user) 
    {
        super(main, view, user);

        this.view = view;

        initializeDefaultListeners();
    }
}
