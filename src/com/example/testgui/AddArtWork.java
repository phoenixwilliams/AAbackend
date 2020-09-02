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

public class AddArtWork extends JFrame
{
    private JButton  workHolder;
    private HintTextArea workDescription;
    private HintTextField workPrice;
    private JButton addWorkButton;
    private JPanel addWorkPanel;
    private JButton backButton;
    private JLabel loadingLabel;
    private JPanel loadingLabelPanel;

    public LoadingGUI loadingGUI;
    private JSONObject mainJson;
    private AddArtWork m;
    private String serverPath;

    private String workImagePath;


    public AddArtWork(JSONObject mainJson, String newImagePath)
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0,0,500, 700);
        setResizable(false);
        this.mainJson = mainJson;
        this.m = this;
        this.serverPath = newImagePath;

        addWorkPanel = new JPanel(null);
        addWorkPanel.setSize(500,700);
        addWorkPanel.setBackground(new Color(135,122,253));

        loadingLabelPanel = new JPanel(null);
        loadingLabelPanel.setSize(500,700);
        loadingLabelPanel.setBackground(new Color(135,122,253));


        workHolder = new JButton("Click and Select Image");
        workHolder.setBounds(20,20,440,300);
        workHolder.setBackground(new Color(255,255,255));
        workHolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage image;
                try {
                    File file = imageUtils.imageChooser();
                    image = ImageIO.read(file);
                    if (image != null)
                    {
                        workImagePath = file.getAbsolutePath();
                        ImageIcon resizedImage = imageUtils.proportionalResizeImage(image,
                                workHolder.getHeight(),workHolder.getWidth());
                        workHolder.setIcon(resizedImage);
                        workHolder.setHorizontalAlignment(SwingConstants.CENTER);
                        workHolder.setText("");


                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        addWorkPanel.add(workHolder);

        JLabel priceLabel = new JLabel("Price");
        priceLabel.setBounds(20, 350,50,30);
        addWorkPanel.add(priceLabel);

        workPrice = new HintTextField("Enter Price Here");
        workPrice.setBounds(20, 385,440,30);
        addWorkPanel.add(workPrice);


        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setBounds(20, 420, 100,30);
        addWorkPanel.add(descriptionLabel);

        workDescription = new HintTextArea("Enter Description Here");
        workDescription.setBounds(20,455,440,100);
        workDescription.setLineWrap(true);
        addWorkPanel.add(workDescription);

        addWorkButton = new JButton("Add");
        addWorkButton.setBounds(20,570,150,75);
        addWorkButton.setBackground(Color.GREEN);
        addWorkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String priceTest = workPrice.getText().replaceAll("\\s+","");
                if (workImagePath != null && priceTest!="")
                {
                    loadingGUI = new LoadingGUI();
                    Thread threadGUI = new Thread(loadingGUI);
                    threadGUI.start();

                    AddWorkOnline addWorkOnline = new AddWorkOnline(workImagePath,workDescription.getText(),
                                                                                workPrice.getText());
                    Thread addWorkOnlineThread = new Thread(addWorkOnline);
                    addWorkOnlineThread.start();
                    addWorkPanel.setVisible(false);
                }


            }
        });
        addWorkPanel.add(addWorkButton);

        backButton = new JButton("Cancel");
        backButton.setBounds(200,570,150,75);
        backButton.setBackground(Color.ORANGE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addWorkPanel.setVisible(false);
                BackToWorkUpdate backToWorkUpdate = new BackToWorkUpdate(true);
                Thread jsonThread = new Thread(backToWorkUpdate);
                jsonThread.start();
            }
        });
        addWorkPanel.add(backButton);

        loadingLabel = new JLabel();
        loadingLabel.setBounds(100,150,300,300);
        loadingLabelPanel.add(loadingLabel);
        loadingLabelPanel.setVisible(false);

        add(loadingLabelPanel);
        add(addWorkPanel);
        setVisible(true);
    }

    class AddWorkOnline implements Runnable
    {
        private String imagePath;
        private String description;
        private String price;

        public AddWorkOnline(String imagePath, String description, String price)
        {
            this.imagePath = imagePath;
            this.description = description;
            this.price = price;
        }

        @Override
        public void run()
        {
            try {
                String imageLink = ResourceServerHandler.StoreLocalImage(imagePath,serverPath);
                mainJson = JsonHandler.addElementWorkJsonArray(mainJson,imageLink,description,price);
                JsonHandler.updateJsonOnline(mainJson);
            } catch (IOException e) {
                e.printStackTrace();
            }

            BackToWorkUpdate backToWorkUpdate = new BackToWorkUpdate(false);
            Thread jsonThread = new Thread(backToWorkUpdate);
            jsonThread.start();
        }
    }

    class BackToWorkUpdate implements Runnable
    {
        private boolean loadingGUIvisible;
        public BackToWorkUpdate(boolean loadingGUIvisible)
        {
            this.loadingGUIvisible = loadingGUIvisible;
        }

        @Override
        public void run()
        {
            //Loading GUI
            if(this.loadingGUIvisible) {
                loadingGUI = new LoadingGUI();
                Thread threadGUI = new Thread(loadingGUI);
                threadGUI.start();
            }
            new WorkUpdateForm(null,m,JsonHandler.getJsonAll());

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
            loadingLabelPanel.setVisible(true);
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
