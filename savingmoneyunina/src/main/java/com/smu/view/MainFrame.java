package com.smu.view;

import javax.swing.*;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;


public class MainFrame extends JFrame 
{
    public MainFrame()
    {
        this.setTitle("SavingMoneyUnina");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setSize(1440,810);
        ImageIcon logo = new ImageIcon(MainFrame.class.getResource("/logo.png"));
        this.setIconImage(logo.getImage());
        this.getContentPane().setBackground(new java.awt.Color(35, 35, 35));

        Rectangle screen = getScreenSize();
        int x = ((int) screen.getMaxX() - this.getWidth()) / 2;
        int y = ((int) screen.getMaxY() - this.getHeight()) / 2;
        this.setLocation(x, y);
        this.setVisible(true);
    }

    private Rectangle getScreenSize()
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        return defaultScreen.getDefaultConfiguration().getBounds();
    }
}
