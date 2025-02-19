package com.smu.view;
import javax.swing.*;
import java.awt.*;

public class RightTriangleButton extends JButton 
{
    private Color triangleColor = new Color(0, 123, 255);  // Customize color if needed
    HomePanel homePanel;

    public RightTriangleButton(HomePanel panel) 
    {
        this.homePanel = panel;

        setContentAreaFilled(false);  // Disable default button background
        setFocusPainted(false);       // Disable focus painting
        setBorderPainted(false);      // Disable default border
        setFont(new Font("Arial", Font.BOLD, 16));
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

        // Triangle pointing RIGHT
        int[] xPoints = { 0, width, 0 };
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
        int[] xPoints = { 0, width, 0 };
        int[] yPoints = { 0, height / 2, height };
        Polygon triangle = new Polygon(xPoints, yPoints, 3);

        // Return true if the clicked point is inside the triangle
        return triangle.contains(x, y);
    }
}
