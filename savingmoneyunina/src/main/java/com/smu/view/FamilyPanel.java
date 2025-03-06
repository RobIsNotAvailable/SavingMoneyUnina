package com.smu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import com.smu.view.UiUtil.*;
import com.smu.model.Family;
import com.smu.model.User;

public class FamilyPanel extends DefaultPanel
{
    private JPanel usersTable;
    private JPanel famillyDetails;

    private JFormattedTextField dateField;
    private JButton showButton;

    public FamilyPanel()
    {


        usersTable = new JPanel(new GridLayout());
        usersTable.setOpaque(false);
        remove(cardManager);

        famillyDetails = new JPanel(new FlowLayout());
        famillyDetails.setOpaque(false);
        famillyDetails.setPreferredSize(new Dimension(800,1000));
    }

    public void showUsers(List<User> users)
    {
        

        this.add(usersTable, BorderLayout.CENTER);
    }

    public void showFamilyDetails(Family family)
    {

        final int PANEL_WIDTH = 800;
        try
        {
            MaskFormatter formatter = new MaskFormatter("##/####");
            formatter.setPlaceholderCharacter('-');
            dateField = new JFormattedTextField(formatter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        UiUtil.styleComponent(dateField);
        dateField.setFont(new Font("Arial", Font.PLAIN, 18));
        dateField.setColumns(7);
        dateField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/uuuu")));


        showButton = UiUtil.createStyledButton("Show");
        showButton.setContentAreaFilled(true);
        showButton.setBackground(UiUtil.DARK_CAPPUCCINO);
        showButton.setMargin(new Insets(3, 35, 3, 35));
        UiUtil.addKeyBinding(showButton, "ENTER");


        famillyDetails.add(new BlankPanel(new Dimension(PANEL_WIDTH,50)));
        famillyDetails.add(UiUtil.createStyledLabel("Month: "));
        famillyDetails.add(dateField);
        famillyDetails.add(new BlankPanel(new Dimension(100,1)));
        famillyDetails.add(showButton);
        
        JLabel iconLabel = UiUtil.createStyledLabel(" ");
        iconLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/family.png")).getImage().getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH)));
        iconLabel.setPreferredSize(new Dimension(PANEL_WIDTH,300));

        famillyDetails.add(new BlankPanel(new Dimension(PANEL_WIDTH,100)));
        famillyDetails.add(iconLabel);
        famillyDetails.add(new BlankPanel(new Dimension(PANEL_WIDTH,30)));        

        JPanel coloredLine = new JPanel();
        coloredLine.setBackground(UiUtil.CAPPUCCINO);
        coloredLine.setPreferredSize(new Dimension(PANEL_WIDTH-70, 5));
        famillyDetails.add(coloredLine);


        JLabel detailsLabel = UiUtil.createStyledLabel("informazione ");
        detailsLabel.setPreferredSize(new Dimension(350, 100));

        JLabel detailsLabel2 = UiUtil.createStyledLabel("informazione ");
        detailsLabel2.setPreferredSize(new Dimension(350, 100));

        JLabel detailsLabel3 = UiUtil.createStyledLabel("informazione ");
        detailsLabel3.setPreferredSize(new Dimension(350, 100));

        JLabel detailsLabel4 = UiUtil.createStyledLabel("informazione ");
        detailsLabel4.setPreferredSize(new Dimension(350, 100));

        famillyDetails.add(detailsLabel);
        famillyDetails.add(detailsLabel2);
        famillyDetails.add(detailsLabel3);
        famillyDetails.add(detailsLabel4);






        this.add(famillyDetails,BorderLayout.WEST);
    }
}
