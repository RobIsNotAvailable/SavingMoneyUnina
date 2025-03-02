package com.smu.view;

import java.awt.BorderLayout;


public class ReportPanel extends DefaultPanel
{
    public ReportPanel()
    {
        cardManager.removeFinanceDetails();
        this.add(cardManager,BorderLayout.WEST);


    }
}
