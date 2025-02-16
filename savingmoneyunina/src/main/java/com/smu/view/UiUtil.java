package com.smu.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UiUtil 
{
    public static final Color BACKGROUND_GRAY = new Color(35, 35, 35);

    public static final Color DARK_CAPPUCCINO = new Color(150,130,115);

    public static final Color CAPPUCCINO = new Color(200,165,140);

    public static final Color ERROR_RED = new Color (224, 58, 58);

    public static final Color SUCCESS_GREEN = new Color (58, 224, 97);

    public static class LogoLabel extends JLabel
    {
        public LogoLabel(double scale)
        {
            setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/logo.png")).getImage().getScaledInstance((int) Math.round(480 * scale), (int) Math.round(270 * scale), java.awt.Image.SCALE_SMOOTH)));
        }
    }

    public static class BlankPanel extends JPanel
    {
        public BlankPanel(Dimension dimension)
        {
            setOpaque(false);
            setPreferredSize(dimension);
        }
    }
}
