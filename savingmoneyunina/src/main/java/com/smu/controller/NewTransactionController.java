package com.smu.controller;

import com.smu.MainController;
import com.smu.model.User;
import com.smu.view.NewTransactionPanel;

public class NewTransactionController extends DefaultController
{
    NewTransactionPanel view;

    public NewTransactionController(MainController main, NewTransactionPanel view, User user) 
    {
        super(main, view, user);
        this.view = view;

        initializeDefaultListeners();
    }
    
}
