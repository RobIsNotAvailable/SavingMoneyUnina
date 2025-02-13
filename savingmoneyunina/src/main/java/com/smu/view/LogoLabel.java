package com.smu.view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LogoLabel extends JLabel
{
    public LogoLabel(double scale)
    {
        this.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/logo.png")).getImage().getScaledInstance((int) Math.round(480 * scale), (int) Math.round(270 * scale), java.awt.Image.SCALE_SMOOTH)));
    }
}
