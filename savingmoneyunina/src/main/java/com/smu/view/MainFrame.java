package com.smu.view;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class MainFrame extends JFrame 
{
    public MainFrame()
    {
        setTitle("SavingMoneyUnina");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(1728,972);
        ImageIcon logo = new ImageIcon(MainFrame.class.getResource("/logo.png"));
        setIconImage(logo.getImage());
        getContentPane().setBackground(UiUtil.BACKGROUND_GRAY);

        Rectangle screen = getScreenSize();
        int x = ((int) screen.getMaxX() - getWidth()) / 2;
        int y = ((int) screen.getMaxY() - getHeight()) / 2;
        setLocation(x, y);
        setVisible(true);
    }

    private Rectangle getScreenSize()
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        return defaultScreen.getDefaultConfiguration().getBounds();
    }
}
