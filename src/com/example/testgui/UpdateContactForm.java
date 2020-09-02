package com.example.testgui;

import com.sun.tools.javac.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class UpdateContactForm extends JFrame
{
    private JPanel listPanel;
    private ArrayList workArrayList;
    private JList workList;
    private JPanel updateContactPanel;
    private JLabel contactLabel;
    private JLabel contactDetailLabel;
    private HintTextField contactTextField;
    private HintTextField contactDetailTextField;
    private JButton addContactButton;
    private JButton updateContactButton;
    private JButton deleteContactButton;
    private JButton backButton;
    private JPanel loadingGUIPanel;
    private JLabel loadingLabel;

    private JSONObject allJsonObject;
    private JSONArray contactJsonArray;

    private String[] contactsArray;
    private String[] contactDetailsArray;

    private MainMenu m;
    private AddContactForm a;
    private UpdateContactForm u;

    LoadingGUI loadingGUI;
    Thread loadingGUIthread;

    private int currentSelectedContact;

    public UpdateContactForm(MainMenu m, AddContactForm a,JSONObject allJsonObject)
    {
        this.allJsonObject = allJsonObject;
        this.contactJsonArray = (JSONArray) allJsonObject.get("contact");
        this.m = m;
        this.a = a;
        this.currentSelectedContact = -1;
        this.u = this;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,500);
        setResizable(false);
        setTitle("Update Contact Details");
        getContentPane().setBackground(new Color(135,122,253));
        setLayout(null);

        updateContactPanel = new JPanel(null);
        updateContactPanel.setBounds(0,0,700,500);
        updateContactPanel.setBackground(new Color(135,122,253));

        loadingGUIPanel = new JPanel(null);
        loadingGUIPanel.setBounds(0,0,700,500);
        loadingGUIPanel.setBackground(new Color(135,122,253));

        loadingLabel = new JLabel();
        loadingLabel.setBounds(200,100,300,300);
        loadingGUIPanel.add(loadingLabel);

        listPanel = new JPanel(new BorderLayout());
        workArrayList = new ArrayList<>();
        for (int index = 0; index < contactJsonArray.toArray().length; index++) {
            workArrayList.add("Contact " + index);
        }
        workList = new JList<String>((String[]) workArrayList.toArray(new String[workArrayList.size()]));
        JScrollPane workListPane = new JScrollPane();
        workListPane.setViewportView(workList);
        workList.setLayoutOrientation(JList.VERTICAL);
        listPanel.add(workListPane);
        listPanel.setBounds(20, 20, 250, 355);
        workList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting())
                {
                    int index = workList.getSelectedIndex();
                    contactTextField.setText(contactsArray[index]);
                    contactDetailTextField.setText(contactDetailsArray[index]);
                    currentSelectedContact = index;
                }
            }
        });

        updateContactPanel.add(listPanel);

        contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(300,20,150,50);
        contactLabel.setFont(new Font("Serif", Font.BOLD, 29));
        updateContactPanel.add(contactLabel);

        contactTextField = new HintTextField("Enter Contact Type E.g. Mobile, Email etc");
        contactTextField.setBounds(300,80,300,30);
        updateContactPanel.add(contactTextField);

        contactDetailLabel = new JLabel("Contact Details:");
        contactDetailLabel.setBounds(300,120,300,50);
        contactDetailLabel.setFont(new Font("Serif",Font.BOLD,29));
        updateContactPanel.add(contactDetailLabel);

        contactDetailTextField = new HintTextField("Enter Contact Details E.g. abc@gmail.com");
        contactDetailTextField.setBounds(300,180,300,30);
        updateContactPanel.add(contactDetailTextField);

        addContactButton = new JButton("Add New Contact");
        addContactButton.setBounds(20,395,150,50);
        addContactButton.setBackground(Color.green);
        addContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                new AddContactForm(contactJsonArray.toArray().length, allJsonObject);
            }
        });
        updateContactPanel.add(addContactButton);

        updateContactButton = new JButton("Save Change");
        updateContactButton.setBounds(375,220,150,50);
        updateContactButton.setBackground(new Color(255,153, 0));
        updateContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(currentSelectedContact>-1)
                {
                    loadingGUI = new LoadingGUI();
                    loadingGUIthread = new Thread(loadingGUI);
                    loadingGUIthread.start();
                    loadingGUIPanel.setVisible(true);

                    UpdateContact updateContact = new UpdateContact(contactTextField.getText(),
                            contactDetailTextField.getText());
                    Thread updateThread = new Thread(updateContact);
                    updateThread.start();
                }
            }
        });
        updateContactPanel.add(updateContactButton);

        deleteContactButton = new JButton("Delete Contact");
        deleteContactButton.setBounds(275,395,150,50);
        deleteContactButton.setBackground(Color.RED);
        deleteContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new DeleteContactForm(u);
            }
        });
        updateContactPanel.add(deleteContactButton);

        backButton = new JButton("Cancel");
        backButton.setBounds(530,395,150,50);
        backButton.setBackground(Color.magenta);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MainMenu();
            }
        });
        updateContactPanel.add(backButton);

        add(updateContactPanel);
        add(loadingGUIPanel);
        SetUp setUp = new SetUp();
        Thread thread = new Thread(setUp);
        thread.start();
    }

    public void deleteProcedure()
    {
        loadingGUI = new LoadingGUI();
        loadingGUIthread = new Thread(loadingGUI);
        loadingGUIthread.start();
        loadingGUIPanel.setVisible(true);

        DeleteContact deleteContact = new DeleteContact(currentSelectedContact);
        Thread deleteThread = new Thread(deleteContact);
        deleteThread.start();
    }

    class SetUp implements Runnable
    {
        @Override
        public void run()
        {
            contactsArray = JsonHandler.getAllContacts(contactJsonArray);
            contactDetailsArray = JsonHandler.getAllContactDetails(contactJsonArray);

            if(m!=null)
            {
                m.loadingGUI.stop();
                m.setVisible(false);
            }

            if(a!=null)
            {
                a.loadingGUI.stop();
                a.setVisible(false);
            }

            if (contactsArray.length >0 && contactDetailsArray.length == contactsArray.length) {
                workList.setSelectedIndex(0);
                currentSelectedContact = 0;
            }


            setVisible(true);
        }
    }

    class UpdateContact implements Runnable
    {
        private String contact;
        private String detail;
        public UpdateContact(String contact, String detail)
        {
            this.contact = contact;
            this.detail = detail;
        }
        @Override
        public void run()
        {
            try {
                allJsonObject = JsonHandler.updateContact(allJsonObject,currentSelectedContact,
                        contact,detail);
                JsonHandler.updateJsonOnline(allJsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
            u.setVisible(false);
            loadingGUI.stop();
            new UpdateContactForm(null,null,JsonHandler.getJsonAll());
        }
    }

    class DeleteContact implements Runnable
    {
        private int contactID;
        public DeleteContact(int contactID)
        {
            this.contactID = contactID;
        }

        @Override
        public void run()
        {
            try {
                allJsonObject = JsonHandler.deleteContact(allJsonObject,contactID);
                JsonHandler.updateJsonOnline(allJsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
            loadingGUI.stop();
            u.setVisible(false);
            new UpdateContactForm(null,null,JsonHandler.getJsonAll());
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
            updateContactPanel.setVisible(false);
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
