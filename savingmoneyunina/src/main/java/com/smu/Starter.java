package com.smu;

import javax.swing.SwingUtilities;

import com.smu.view.LoginPanel;
import com.smu.view.MainFrame;

public class Starter 
{
    public static void main( String[] args)
    {
        SwingUtilities.invokeLater(Starter::startApp);
    }

    private static void startApp()
    {
        MainFrame frame = new MainFrame();
        LoginPanel panel = new LoginPanel();
        frame.add(panel);

    }
}