package com.example.testgui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UpdateAppMenuForm extends JFrame
{
    private Image mainLogo;
    private Image newsLogo;
    private Image blogLogo;
    private Image contactLogo;
    private Image workLogo;

    public UpdateAppMenuForm()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,700);
        setResizable(false);

        //Set up Work logo
        JButton btnWork = new JButton("WORK");
        btnWork.setHorizontalTextPosition(JButton.CENTER);
        btnWork.setVerticalTextPosition(JButton.CENTER);
        btnWork.setBounds(216,410, 162, 232);
        Image workImage = new ImageIcon("resources//main_worklogo.jpg").getImage()
                .getScaledInstance(btnWork.getWidth(), btnWork.getHeight(), Image.SCALE_DEFAULT);
        btnWork.setIcon(new ImageIcon(workImage));
        btnWork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon image = imageUtils.imageChooser();
                if (image != null)
                {
                    Image resizedImage = image.getImage().getScaledInstance(btnWork.getWidth(),btnWork.getHeight()
                            , Image.SCALE_DEFAULT);
                    btnWork.setIcon(new ImageIcon(resizedImage));
                }
            }
        });
        add(btnWork);

        //Set up Blog Logo
        JButton btnBlog = new JButton("BLOG");
        btnBlog.setVerticalTextPosition(JButton.CENTER);
        btnBlog.setHorizontalTextPosition(JButton.CENTER);
        btnBlog.setBounds(216,242, 162,155);
        Image blogImage = new ImageIcon("resources//main_bloglogo.jpg").getImage()
                .getScaledInstance(btnBlog.getWidth(),btnBlog.getHeight(), Image.SCALE_DEFAULT);
        btnBlog.setIcon(new ImageIcon(blogImage));
        btnBlog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ImageIcon image = imageUtils.imageChooser();
                if (image != null)
                {
                    Image resizedImage = image.getImage().getScaledInstance(btnBlog.getWidth(),btnBlog.getHeight()
                            , Image.SCALE_DEFAULT);
                    btnBlog.setIcon(new ImageIcon(resizedImage));
                }
            }
        });
        add(btnBlog);

        //Set up Contact Logo
        JButton btnContact = new JButton("CONTACT");
        btnContact.setVerticalTextPosition(JButton.CENTER);
        btnContact.setHorizontalTextPosition(JButton.CENTER);
        btnContact.setBounds(21, 505, 181, 137);
        Image contactImage = new ImageIcon("resources//main_contactlogo.jpg").getImage()
                .getScaledInstance(btnContact.getWidth(),btnContact.getWidth(),Image.SCALE_DEFAULT);
        btnContact.setIcon(new ImageIcon(contactImage));
        btnContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon image = imageUtils.imageChooser();
                if (image != null)
                {
                    Image resizedImage = image.getImage().getScaledInstance(btnContact.getWidth(),btnContact.getHeight()
                            , Image.SCALE_DEFAULT);
                    btnContact.setIcon(new ImageIcon(resizedImage));
                }

            }
        });
        add(btnContact);



        //set up News logo
        JButton btnNews = new JButton("NEWS");
        btnNews.setHorizontalTextPosition(JButton.CENTER);
        btnNews.setVerticalTextPosition(JButton.CENTER);
        btnNews.setBounds(21,242,180,253);
        Image newsImage = new ImageIcon("resources//main_newslogo.jpg").getImage()
                .getScaledInstance(btnNews.getWidth(),btnNews.getHeight(),Image.SCALE_DEFAULT);
        btnNews.setIcon(new ImageIcon(newsImage));
        btnNews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon image = imageUtils.imageChooser();

                if (image != null)
                {
                    Image resizedImage = image.getImage().getScaledInstance(btnNews.getWidth(),btnNews.getHeight()
                            , Image.SCALE_DEFAULT);
                    btnNews.setIcon(new ImageIcon(resizedImage));
                }
            }
        });
        add(btnNews);


        // Show template
        JLabel phoneTemplate = new JLabel();
        phoneTemplate.setBounds(10,10,380,640);
        Image template = new ImageIcon("resources\\MainPageTemplate.png").
                getImage().getScaledInstance(phoneTemplate.getWidth(),phoneTemplate.getHeight(), Image.SCALE_DEFAULT);
        phoneTemplate.setIcon(new ImageIcon(template));
        add(phoneTemplate);


        //Main Logo Button
        JButton btnLogo = new JButton();
        btnLogo.setHorizontalTextPosition(JButton.CENTER);
        btnLogo.setVerticalTextPosition(JButton.CENTER);
        btnLogo.setBounds(21,17,360,213);
        template = new ImageIcon("resources\\main_mainlogo.jpg").
                getImage().getScaledInstance(btnLogo.getWidth(),btnLogo.getHeight(), Image.SCALE_DEFAULT);
        btnLogo.setIcon(new ImageIcon(template));

        btnLogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon image = imageUtils.imageChooser();

                if (image != null)
                {
                    Image resizedImage = image.getImage().getScaledInstance(btnLogo.getWidth(),btnLogo.getHeight()
                            , Image.SCALE_DEFAULT);
                    btnLogo.setIcon(new ImageIcon(resizedImage));
                }
            }
        });

        add(btnLogo);





        setTitle("Update Application Main Menu");
        getContentPane().setBackground(new Color(135,122,253));

        setLayout(null);
        setVisible(true);

    }


}
