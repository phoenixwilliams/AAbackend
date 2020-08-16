package com.example.testgui;

import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class UpdateAppMenuForm extends JFrame
{
    public AtomicBoolean loaded = new AtomicBoolean(false);

    private MainMenu m;

    private String mainLogo;
    private String newsLogo;
    private String blogLogo;
    private String contactLogo;
    private String workLogo;

    private JButton btnMainLogo;
    private JButton btnNews;
    private JButton btnContact;
    private JButton btnBlog;
    private JButton btnWork;
    private JButton btnUpdate;

    protected JSONObject jsonObject;
    private JSONObject mainJsonObject;



    public UpdateAppMenuForm(MainMenu m,JSONObject jsonObject)
    {
        this.m = m;
        this.jsonObject = jsonObject;
        this.mainJsonObject = (JSONObject)this.jsonObject.get("main_page");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,700);
        setResizable(false);


        //Set-Up Update Button
        btnUpdate = new JButton("UPDATE");
        btnUpdate.setBackground(new Color(150,0,34));
        btnUpdate.setBounds(400,10,275,120);
        btnUpdate.setFont(new Font("Calbri", Font.BOLD,18));
        btnUpdate.setForeground(new Color(255,255,255));
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                System.out.println(mainLogo);
                System.out.println(newsLogo);
                System.out.println(blogLogo);
                System.out.println(contactLogo);
                System.out.println(workLogo);
                 */
                System.out.println(mainJsonObject.toString());
            }
        });
        add(btnUpdate);


        //Set up Work logo
        btnWork = new JButton("WORK");
        btnWork.setHorizontalTextPosition(JButton.CENTER);
        btnWork.setVerticalTextPosition(JButton.CENTER);
        btnWork.setBounds(216,410, 162, 232);
        btnWork.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon image;
                try {
                    File file = imageUtils.imageChooser();
                    image = new ImageIcon(ImageIO.read(file));
                    if (image != null)
                    {
                        workLogo = file.getParent();
                        Image resizedImage = image.getImage().getScaledInstance(btnWork.getWidth(),btnWork.getHeight()
                                , Image.SCALE_DEFAULT);
                        btnWork.setIcon(new ImageIcon(resizedImage));
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        add(btnWork);

        //Set up Blog Logo
        btnBlog = new JButton("BLOG");
        btnBlog.setVerticalTextPosition(JButton.CENTER);
        btnBlog.setHorizontalTextPosition(JButton.CENTER);
        btnBlog.setBounds(216,242, 162,155);
        btnBlog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ImageIcon image;
                try {
                    File file = imageUtils.imageChooser();
                    image = new ImageIcon(ImageIO.read(file));
                    if (image != null)
                    {
                        blogLogo = file.getParent();
                        Image resizedImage = image.getImage().getScaledInstance(btnMainLogo.getWidth(),
                                btnMainLogo.getHeight(),Image.SCALE_DEFAULT);
                        btnBlog.setIcon(new ImageIcon(resizedImage));
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        add(btnBlog);

        //Set up Contact Logo
        btnContact = new JButton("CONTACT");
        btnContact.setVerticalTextPosition(JButton.CENTER);
        btnContact.setHorizontalTextPosition(JButton.CENTER);
        btnContact.setBounds(21, 505, 181, 137);
        btnContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon image;
                try {
                    File file = imageUtils.imageChooser();
                    image = new ImageIcon(ImageIO.read(file));
                    if (image != null)
                    {
                        contactLogo = file.getParent();
                        Image resizedImage = image.getImage().getScaledInstance(btnContact.getWidth(),
                                btnContact.getHeight(),Image.SCALE_DEFAULT);
                        btnContact.setIcon(new ImageIcon(resizedImage));
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        add(btnContact);



        //set up News logo
        btnNews = new JButton("NEWS");
        btnNews.setHorizontalTextPosition(JButton.CENTER);
        btnNews.setVerticalTextPosition(JButton.CENTER);
        btnNews.setBounds(21,242,180,253);
        btnNews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon image;
                try {
                    File file = imageUtils.imageChooser();
                    image = new ImageIcon(ImageIO.read(file));
                    if (image != null)
                    {
                        newsLogo = file.getParent();
                        Image resizedImage = image.getImage().getScaledInstance(btnNews.getWidth(),btnNews.getHeight()
                                , Image.SCALE_DEFAULT);
                        btnNews.setIcon(new ImageIcon(resizedImage));
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
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
        btnMainLogo = new JButton();
        btnMainLogo.setHorizontalTextPosition(JButton.CENTER);
        btnMainLogo.setVerticalTextPosition(JButton.CENTER);
        btnMainLogo.setBounds(21,17,360,213);
        btnMainLogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon image;
                try {
                    File file = imageUtils.imageChooser();
                    image = new ImageIcon(ImageIO.read(file));
                    if (image != null)
                    {
                        mainLogo = file.getParent();
                        Image resizedImage = image.getImage().getScaledInstance(btnMainLogo.getWidth(),
                                btnMainLogo.getHeight(),Image.SCALE_DEFAULT);
                        btnMainLogo.setIcon(new ImageIcon(resizedImage));
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        add(btnMainLogo);

        setTitle("Update Application Main Menu");
        getContentPane().setBackground(new Color(135,122,253));
        setLayout(null);

        SetUpImages setUpImages = new SetUpImages();
        Thread setUpImagesThread = new Thread(setUpImages);
        setUpImagesThread.start();


    }

    class SetUpImages implements Runnable
    {

        @Override
        public void run()
        {
            try {
                ImageIcon icon = JsonHandler.getMainMenuImage(mainJsonObject,"main");
                mainLogo = JsonHandler.getMainMenuImageLink(mainJsonObject,"main");
                Image sizedIcon = icon.getImage().getScaledInstance(btnMainLogo.getWidth(),btnMainLogo.getHeight(),Image.SCALE_DEFAULT);
                btnMainLogo.setIcon(new ImageIcon(sizedIcon));

                icon = JsonHandler.getMainMenuImage(mainJsonObject, "news");
                newsLogo = JsonHandler.getMainMenuImageLink(mainJsonObject, "news");;
                sizedIcon = icon.getImage().getScaledInstance(btnNews.getWidth(),btnNews.getHeight(),Image.SCALE_DEFAULT);
                btnNews.setIcon(new ImageIcon(sizedIcon));

                icon = JsonHandler.getMainMenuImage(mainJsonObject, "contact");
                contactLogo = JsonHandler.getMainMenuImageLink(mainJsonObject, "contact");
                sizedIcon = icon.getImage().getScaledInstance(btnContact.getWidth(),btnContact.getHeight(),Image.SCALE_DEFAULT);
                btnContact.setIcon(new ImageIcon(sizedIcon));

                icon = JsonHandler.getMainMenuImage(mainJsonObject, "blog");
                blogLogo = JsonHandler.getMainMenuImageLink(mainJsonObject, "blog");
                sizedIcon = icon.getImage().getScaledInstance(btnBlog.getWidth(),btnBlog.getHeight(),Image.SCALE_DEFAULT);
                btnBlog.setIcon(new ImageIcon(sizedIcon));

                icon = JsonHandler.getMainMenuImage(mainJsonObject, "work");
                workLogo = JsonHandler.getMainMenuImageLink(mainJsonObject, "blog");
                sizedIcon = icon.getImage().getScaledInstance(btnWork.getWidth(),btnWork.getHeight(),Image.SCALE_DEFAULT);
                btnWork.setIcon(new ImageIcon(sizedIcon));

            } catch (IOException e) {
                e.printStackTrace();
            }
            loaded.set(true);
            setVisible(true);
            m.loadingGUI.stop();
            m.setVisible(false);
        }
    }


}
