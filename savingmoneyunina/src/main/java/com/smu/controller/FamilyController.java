package com.smu.controller;

import com.smu.MainController;
import com.smu.model.User;
import com.smu.view.FamilyPanel;

public class FamilyController extends DefaultController
{

    public FamilyController(MainController main, FamilyPanel view, User user) 
    {
        super(main, view, user);

        cardManager = null;

        view.showUsers(user.getFamily().getMembers());
        view.showFamilyDetails(user.getFamily());
    }

    @Override
    public void refresh()
    {
        
    }
}
