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

public class AddContactForm extends JFrame
{
    private int contactAmount;
    private JPanel addContactPanel;
    private JLabel contactLabel;
    private JLabel contactDetailLabel;
    private HintTextField contactTextField;
    private HintTextField contactDetailTextField;
    private JButton addContactButton;
    private JButton cancelButton;

    private JPanel loadingGUIPanel;
    private JLabel loadingLabel;

    LoadingGUI loadingGUI;
    Thread loadingGUIthread;

    private JSONObject allJsonObject;
    private AddContactForm a;

    public AddContactForm(int contactAmount, JSONObject allJsonObject)
    {
        this.contactAmount = contactAmount;
        this.allJsonObject = allJsonObject;
        this.a = this;

        loadingGUIPanel = new JPanel(null);
        loadingGUIPanel.setSize(500,500);
        loadingGUIPanel.setBackground(new Color(135,122,253));

        loadingLabel = new JLabel();
        loadingLabel.setBounds(75,75,300,300);
        loadingGUIPanel.add(loadingLabel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);
        setResizable(false);
        setTitle("Update Contact Details");
        setLayout(null);

        addContactPanel = new JPanel(null);
        addContactPanel.setBounds(0,0,500,500);
        addContactPanel.setBackground(new Color(135,122,253));

        contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(175,20,150,50);
        contactLabel.setFont(new Font("Serif", Font.BOLD, 29));
        addContactPanel.add(contactLabel);

        contactTextField = new HintTextField("Enter Contact Type E.g. Mobile, Email etc");
        contactTextField.setBounds(90, 80,300,30);
        addContactPanel.add(contactTextField);

        contactDetailLabel = new JLabel("Contact Details:");
        contactDetailLabel.setBounds(135,150,300,50);
        contactDetailLabel.setFont(new Font("Serif",Font.BOLD,29));
        addContactPanel.add(contactDetailLabel);

        contactDetailTextField = new HintTextField("Enter Contact Detail E.g. abcz@gmail.com");
        contactDetailTextField.setBounds(90,210,300,30);
        addContactPanel.add(contactDetailTextField);

        addContactButton = new JButton("Add Contact");
        addContactButton.setBounds(70,370,150,50);
        addContactButton.setBackground(Color.green);
        addContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String testContact = contactTextField.getText().replaceAll("\\s+","");
                String testContactDetail = contactDetailTextField.getText().replaceAll("\\s+","");

                if (!(testContact.equals("") && testContactDetail.equals("")))
                {
                    addContactPanel.setVisible(false);
                    loadingGUI = new LoadingGUI();
                    loadingGUIthread = new Thread(loadingGUI);
                    loadingGUIthread.start();

                    UploadContact uploadContact = new UploadContact(contactTextField.getText(),
                            contactDetailTextField.getText());
                    Thread uploadThread = new Thread(uploadContact);
                    uploadThread.start();
                }
            }
        });
        addContactPanel.add(addContactButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(250,370,150,50);
        cancelButton.setBackground(Color.red);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContactPanel.setVisible(false);
                loadingGUI = new LoadingGUI();
                loadingGUIthread = new Thread(loadingGUI);
                loadingGUIthread.start();
                new UpdateContactForm(null,a,allJsonObject);
            }
        });
        addContactPanel.add(cancelButton);

        add(addContactPanel);
        add(loadingGUIPanel);
        setVisible(true);
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
            System.out.println("Starting GUI");
            BufferedImage img;
            double i = 0;
            loadingGUIPanel.setVisible(true);
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

    class UploadContact implements Runnable
    {
        private String contact;
        private String detail;

        public UploadContact(String contact, String detail)
        {
            this.contact = contact;
            this.detail = detail;
        }

        @Override
        public void run()
        {
            try {
                JsonHandler.updateJsonOnline(allJsonObject);
                allJsonObject = JsonHandler.addContact(allJsonObject,contact,detail);
                JsonHandler.updateJsonOnline(allJsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
            new UpdateContactForm(null,a,JsonHandler.getJsonAll());
        }
    }

}
