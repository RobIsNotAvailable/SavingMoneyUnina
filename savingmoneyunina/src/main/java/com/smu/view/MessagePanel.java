package com.smu.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessagePanel extends JPanel
{
    protected JLabel messageLabel;

    public void showErrorMessage(String message)
    {
        messageLabel.setForeground(UiUtil.ERROR_RED);
        messageLabel.setText(message);
    }

    public void showSuccessMessage(String message)
    {
        messageLabel.setForeground(UiUtil.SUCCESS_GREEN);
        messageLabel.setText(message);
        
        UiUtil.delayExecution(4000, _ -> resetMessage());
    }

    public void resetMessage()
    {
        messageLabel.setText(" ");
    }
}
