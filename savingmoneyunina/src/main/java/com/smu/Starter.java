package com.smu;

import javax.swing.SwingUtilities;

import com.smu.controller.LoginController;
import com.smu.model.User;
import com.smu.view.LoginPanel;
import com.smu.view.MainFrame;

public class Starter 
{
    private Starter()
    {
        new User(null, null).verify();
        MainFrame frame = new MainFrame();
        LoginPanel login = new LoginPanel();
        frame.add(login);
        LoginController controller = new LoginController(login);

    }
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(Starter::new);
    }
}