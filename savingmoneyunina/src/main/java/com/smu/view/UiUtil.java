package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.JTableHeader;


public class UiUtil
{
    public static final Color BACKGROUND_GRAY = new Color(35, 35, 35);

    public static final Color DARK_CAPPUCCINO = new Color(107, 88, 75);

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

    public static class Navbar extends JPanel
    {
        private JPanel wrapperPanel;
        private JButton homeButton;
        private JButton familyButton;
        private JButton newTransactionButton;
        private JButton reportButton;
        private JButton logoutButton;

        public Navbar()
        {
            wrapperPanel = new JPanel(new BorderLayout());

            setBackground(DARK_CAPPUCCINO);

            setLayout(new GridLayout(1, 0, 20, 0));
            setOpaque(true);

            homeButton = createStyledButton("Home");
            familyButton = createStyledButton("New Transaction");
            newTransactionButton = createStyledButton("Reports");
            reportButton = createStyledButton("Family");
            logoutButton = createStyledButton("Log out");

            add(homeButton);
            add(new BlankPanel(new Dimension(50, 1)));
            add(familyButton);
            add(newTransactionButton);
            add(reportButton);
            add(new BlankPanel(new Dimension(50, 1)));
            add(logoutButton);

            wrapperPanel.add(this, BorderLayout.CENTER);
        }

        public JButton getHomeButton()
        {
            return homeButton;
        }

        public JButton getFamilyButton()
        {
            return familyButton;
        }

        public JButton getNewTransactionButton()
        {
            return newTransactionButton;
        }

        public JButton getreportButton()
        {
            return reportButton;
        }

        public JButton getLogoutButton()
        {
            return logoutButton;
        }
    }

    public static JButton createStyledButton(String text)
    {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 0, 0, 0)); 
        button.setForeground(Color.WHITE);
        
        button.setContentAreaFilled(false); 
        button.setBorderPainted(false);
        button.setFocusPainted(false); 
        button.setFocusable(false); 
        button.setMargin(new Insets(10, 15, 15, 15));
        button.setRolloverEnabled(false);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }

    public static JLabel createStyledLabel(String text)
    {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
    
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 20)); // Set desired font size

        // Create a fixed-sized panel to contain the label
        JPanel wrapper = new JPanel();
        wrapper.setPreferredSize(new Dimension(200, 50)); // Set fixed size
        wrapper.setMaximumSize(new Dimension(200, 50));
        wrapper.setOpaque(false);
        wrapper.add(label);

        return label;
    }

    public static void addListener(JButton button, ActionListener listener)
    {
        button.addActionListener(listener);
    }

    public static class TriangleButton extends JButton
    {
        public enum Direction
        {
            LEFT, RIGHT
        }

        private Polygon triangle;
        private Direction direction;

        public TriangleButton(Direction direction)
        {
            super();
            this.direction = direction;
            setContentAreaFilled(false);  // Disable default button background
            setFocusPainted(false);       // Disable focus painting
            setBorderPainted(false);      // Disable default border
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // anti aliasing (no jagged edges)

            int[] xPoints;
            int[] yPoints;

            if (direction == Direction.LEFT)
            {
                xPoints = new int[]{width, 0, width};
                yPoints = new int[]{0, height / 2, height};
            }
            else
            {
                xPoints = new int[]{0, width, 0};
                yPoints = new int[]{0, height / 2, height};
            }

            triangle = new Polygon(xPoints, yPoints, 3);

            g2d.setColor(UiUtil.CAPPUCCINO);
            g2d.fill(triangle);
        }

        @Override
        public boolean contains(int x, int y)
        {
            int width = getWidth();
            int height = getHeight();
            int[] xPoints;
            int[] yPoints;

            if (direction == Direction.LEFT)
            {
                xPoints = new int[]{width, 0, width};
                yPoints = new int[]{0, height / 2, height};
            }
            else
            {
                xPoints = new int[]{0, width, 0};
                yPoints = new int[]{0, height / 2, height};
            }

            triangle = new Polygon(xPoints, yPoints, 3);

            // Return true if the clicked point is inside the triangle
            return triangle.contains(x, y);
        }

        public Direction getDirection()
        {
            return direction;
        }
    }

    public static class TransparentTable extends JTable
    {
        public TransparentTable(Object[][] data, String[] ColumnNames)
        {
            super(data, ColumnNames);

            setFillsViewportHeight(true);  
            setOpaque(false);  
            setBackground(new Color(0, 0, 0, 0));  
            setForeground(Color.WHITE);  
            setFont(getFont().deriveFont(20f));  
            setRowHeight(30);  
            setShowGrid(false);  
            setEnabled(false);

            JTableHeader header = getTableHeader();
            header.setBackground(UiUtil.DARK_CAPPUCCINO);
            header.setPreferredSize(new Dimension(10, 10)); 
            header.setEnabled(false);
            header.setBorder(BorderFactory.createLineBorder(UiUtil.DARK_CAPPUCCINO)); 
            
            header.setUI(new BasicTableHeaderUI() //custom header to remove separation between columns in the header
            {
                @Override
                public void paint(Graphics g, JComponent c) {
                    
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setColor(UiUtil.DARK_CAPPUCCINO);
                    g2d.fillRect(0, 0, c.getWidth(), c.getHeight());
                }
            });

            setTableHeader(header);
        }
    }

    public static class TransparentScrollPanel extends JScrollPane 
    {
        public TransparentScrollPanel(Component component, int width, int height) 
        {
            super(component);  

            setPreferredSize(new Dimension(width, height));  
            setOpaque(false);  
            getViewport().setOpaque(false);  
            setBorder(BorderFactory.createEmptyBorder());  

            getVerticalScrollBar().setOpaque(false);
            getHorizontalScrollBar().setOpaque(false);
    
            getVerticalScrollBar().setUI(new BasicScrollBarUI() 
            {
                @Override
                protected void configureScrollBarColors() 
                {
                    this.thumbColor = UiUtil.CAPPUCCINO;
                    this.trackColor = new Color(0,0,0,0);
                }

                @Override
                protected JButton createDecreaseButton(int orientation) 
                {
                    JButton btn = new JButton();
                    btn.setPreferredSize(new Dimension(0, 0));
                    btn.setVisible(false);
                    return btn;
                }

                @Override
                protected JButton createIncreaseButton(int orientation) 
                {
                    JButton btn = new JButton();
                    btn.setPreferredSize(new Dimension(0, 0));
                    btn.setVisible(false);
                    return btn;
                }

                @Override //changin shape of the scroll wheel
                protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) 
                {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(thumbColor);
                    g2d.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                    g2d.dispose();
                }
            });

            getVerticalScrollBar().setPreferredSize(new Dimension(5, 0)); 
        }
    } 
}
