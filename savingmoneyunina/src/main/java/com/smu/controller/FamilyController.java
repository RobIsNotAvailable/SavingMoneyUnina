package com.smu.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.smu.MainController;
import com.smu.model.Family;
import com.smu.model.User;
import com.smu.view.FamilyPanel;
import com.smu.view.UiUtil;

public class FamilyController extends DefaultController
{
    private FamilyPanel view;
    private Family family;

    public FamilyController(MainController main, FamilyPanel view, User user) 
    {
        super(main, view, user);

        this.view = view;
        this.family = user.getFamily();
        cardManager = null;
        
        UiUtil.addListener(view.getShowButton(), new ShowListener());

        view.update(family);
    }

    @Override
    public void refresh()
    {
        view.update(family);
    }

    private class ShowListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            view.update(family);
        }
    } 
}
