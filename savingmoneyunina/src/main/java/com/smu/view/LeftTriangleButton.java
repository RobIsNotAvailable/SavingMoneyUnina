package com.smu.view;

import javax.swing.*;

import java.awt.*;

public class LeftTriangleButton extends JButton  
{  
    private Color triangleColor = new Color(0, 123, 255);  // Customize color if needed  
    HomePanel homePanel;

    public LeftTriangleButton(HomePanel panel)  
    {  
        super();  
        setContentAreaFilled(false);  // Disable default button background  
        setFocusPainted(false);       // Disable focus painting  
        setBorderPainted(false);      // Disable default border  

        this.homePanel = panel;
    }  

    @Override  
    protected void paintComponent(Graphics g)  
    {  
        super.paintComponent(g);  
        Graphics2D g2d = (Graphics2D) g;  
        int width = getWidth();  
        int height = getHeight();  

        // Set anti-aliasing for smoother triangle edges  
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  

        // Triangle pointing LEFT  
        int[] xPoints = { width, 0, width };  
        int[] yPoints = { 0, height / 2, height };  
        Polygon triangle = new Polygon(xPoints, yPoints, 3);  

        g2d.setColor(triangleColor);  
        g2d.fill(triangle);  

        // Optionally draw the text over the triangle  
        g2d.setColor(Color.WHITE); // Text color  
        g2d.drawString(getText(), width / 3, height / 2);  
    }  

    @Override  
    public boolean contains(int x, int y)  
    {  
        // Define the triangle shape  
        int width = getWidth();  
        int height = getHeight();  
        int[] xPoints = { width, 0, width };  
        int[] yPoints = { 0, height / 2, height };  
        Polygon triangle = new Polygon(xPoints, yPoints, 3);  

        // Return true if the clicked point is inside the triangle  
        return triangle.contains(x, y);  
    }  
}  

