package com.example.testgui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simple2 extends JFrame
{
    JFrame f;
    public Simple2()
    {
        JButton b = new JButton("Click");
        final JTextField tf = new JTextField();
        tf.setBounds(50,50,250,20);
        b.setBounds(130,100,100,40);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tf.setText("Welcome to Wales App Control Hub");
            }
        });
        add(tf);
        add(b);
        setSize(400,500);
        setLayout(null);
        setVisible(true);
    }
}
