package com.smu.controller;

import com.smu.MainController;
import com.smu.model.User;
import com.smu.view.NewTransactionPanel;

public class NewTransactionController extends CardManagerController
{
    NewTransactionPanel view;

    public NewTransactionController(MainController main, NewTransactionPanel view, User user) 
    {
        super(view.getCardManager(), user);

        new NavbarController(main, view.getNavbar());

        this.view=view;

        updateButton();
        updateDetails();
        initializeDefaultListeners();
    }
    
}
