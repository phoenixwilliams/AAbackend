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

public class MainMenu extends JFrame
{
    private JLabel loadingLabel;
    private JPanel background;
    private JPanel buttonPanel;

    private MainMenu m;

    public LoadingGUI loadingGUI;


    public MainMenu() {
        m = this;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,700);
        setResizable(false);
        setTitle("Profile Update Set-Up Main");

        buttonPanel = new JPanel();
        buttonPanel.setBounds(0,0,700,700);
        buttonPanel.setBackground(new Color(135,122,253));
        buttonPanel.setLayout(null);

        background = new JPanel();
        background.setBounds(0,0,700,700);
        background.setBackground(new Color(135,122,253));

        loadingLabel = new JLabel();
        loadingLabel.setBounds(200,150,300,300);
        loadingLabel.setVisible(false);


        //Title
        JLabel title = new JLabel("WELCOME TO YOUR PROFILE ENVIRONMENT");
        title.setFont(new Font("Serif", Font.BOLD, 29));
        title.setBounds(5,5,700,200);
        add(title);

        JButton contactUpdateButton = new JButton("Update Contact Details");
        contactUpdateButton.setFont(new Font("Serif", Font.BOLD, 18));
        contactUpdateButton.setForeground(new Color(255,255,255));
        contactUpdateButton.setBounds(195,410,300,50);
        contactUpdateButton.setBackground(new Color(0,0,0));
        contactUpdateButton.setFocusPainted(false);
        contactUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.setVisible(false);
                GetJson getJson = new GetJson("Contact");
                Thread jsonThread = new Thread(getJson);
                jsonThread.start();
            }
        });

        buttonPanel.add(contactUpdateButton);

        //Work Update Button
        JButton workUpdateButton = new JButton("Update Work Advertised");
        workUpdateButton.setFont(new Font("Serif", Font.BOLD, 18));
        workUpdateButton.setForeground(new Color(255,255,255));
        workUpdateButton.setBounds(195,350,300,50);
        workUpdateButton.setBackground(new Color(0,0,0));
        workUpdateButton.setFocusPainted(false);
        workUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPanel.setVisible(false);

                GetJson getJson = new GetJson("Work");
                Thread jsonThread = new Thread(getJson);
                jsonThread.start();
            }
        });

        buttonPanel.add(workUpdateButton);

        //MainMenuFormButton
        JButton mainMenuUpdateButton = new JButton("Profile Main Menu");
        mainMenuUpdateButton.setFont(new Font("Serif", Font.BOLD,18));
        mainMenuUpdateButton.setForeground(new Color(255,255,255));
        mainMenuUpdateButton.setBounds(250, 250,200,50);
        mainMenuUpdateButton.setBackground(new Color(0,0,0));
        mainMenuUpdateButton.setFocusPainted(false);

        mainMenuUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                buttonPanel.setVisible(false);

                GetJson getJson = new GetJson("MainMenu");
                Thread jsonThread = new Thread(getJson);
                jsonThread.start();




            }
        });

        buttonPanel.add(mainMenuUpdateButton);

        add(buttonPanel);
        add(loadingLabel);
        add(background);
        setLayout(null);
        setVisible(true);

    }


    class GetJson implements Runnable
    {
        JSONObject data;
        String menu;
        GetJson(String menu)
        {
            this.menu = menu;
        }

        @Override
        public void run()
        {
            //Loading GUI
            loadingLabel.setVisible(true);
            loadingGUI = new LoadingGUI();
            Thread threadGUI = new Thread(loadingGUI);
            threadGUI.start();
            data = JsonHandler.getJsonAll();

            switch (menu){
                case "MainMenu":
                    new UpdateAppMenuForm(m, data);
                    break;

                case "Work":
                    new WorkUpdateForm(m,null,data);
                    break;

                case "Contact":
                    new UpdateContactForm(m,null,data);
            }


        }
    }

    class LoadingGUI implements Runnable
    {
        private final AtomicBoolean running = new AtomicBoolean(false);

        public void stop()
        {
            running.set(false);
        }

        @Override
        public void run() {
            BufferedImage img;
            double i = 0;
            try {
                running.set(true);
                while (running.get()) {
                    img = ImageIO.read(new File("resources//loadingcircle.png"));
                    img = imageUtils.rotateImage(img, i * Math.PI);
                    Image loadingImage = new ImageIcon(img).getImage().getScaledInstance(loadingLabel.getWidth(),
                            loadingLabel.getHeight(), Image.SCALE_DEFAULT);
                    loadingLabel.setIcon(new ImageIcon(loadingImage));
                    //Thread.sleep(500);
                    i += 0.01;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }






}
