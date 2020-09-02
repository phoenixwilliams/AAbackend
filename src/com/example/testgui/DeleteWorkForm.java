package com.example.testgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteWorkForm extends JFrame
{
    private JButton yesButton;
    private JButton cancelButton;
    private JPanel panel;
    private JLabel question;


    public DeleteWorkForm(WorkUpdateForm workUpdateForm, int currentWork)
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 100);
        setResizable(false);
        this.setBackground(new Color(135,122,253));

        panel = new JPanel(null);
        panel.setBackground(new Color(135,122,253));
        panel.setBounds(0,0,450,100);

        question = new JLabel();
        question.setText("Are you sure you would like to delete this contact from your profile?");
        question.setBounds(0,0,430,50);
        question.setVerticalAlignment(SwingConstants.TOP);
        question.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(question);

        yesButton = new JButton("Yes");
        yesButton.setBounds(100,30,100,30);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                workUpdateForm.deleteProcedure(currentWork);
            }
        });
        panel.add(yesButton);

        cancelButton = new JButton("No");
        cancelButton.setBounds(220,30,100,30);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        panel.add(cancelButton);

        add(panel);
        setLayout(null);
        setVisible(true);


    }

}
