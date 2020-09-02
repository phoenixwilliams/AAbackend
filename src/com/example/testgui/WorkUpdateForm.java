package com.example.testgui;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

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
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class WorkUpdateForm extends JFrame
{
    private JPanel listPanel;
    private ArrayList<String> workArrayList;
    private JScrollPane workListPane;
    private final JList<String> workList;
    private JButton workHolder;
    private JTextField workPrice;
    private JTextArea workDescription;
    private JButton updateButton;
    private JButton addWorkButton;
    private JPanel loadingGUIPanel;
    private JLabel loadingLabel;
    private JPanel updateWorkPanel;
    private JButton deleteWorkButton;
    private JButton backButton;

    LoadingGUI loadingGUI;
    Thread loadingGUIthread;

    private BufferedImage[] workImages;
    private String[] workImagesLinks;
    private String[] descriptions;
    private String[] prices;
    private String[] pricesBuffer;
    private int currentImageID;
    private String currentImagePath;
    private String imageOnlinePath = "getArt/NealeHowells/work/";
    private boolean isCurrImageURL = true;

    private WorkUpdateForm form;

    private MainMenu m;
    private AddArtWork a;
    private JSONObject allJsonObject;
    private JSONArray workJsonArray;
    private boolean fromMainMenu;
    public WorkUpdateForm(MainMenu m, AddArtWork a, JSONObject jsonObject)
    {
        this.m = m;
        this.a = a;
        this.allJsonObject = jsonObject;
        this.workJsonArray = (JSONArray) this.allJsonObject.get("work_page");
        this.form = this;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 700);
        setResizable(false);
        setTitle("Update Work Advertised");
        updateWorkPanel = new JPanel(null);
        updateWorkPanel.setSize(700,700);
        updateWorkPanel.setBackground(new Color(135,122,253));


        loadingGUIPanel = new JPanel(null);
        loadingGUIPanel.setSize(700,700);
        loadingGUIPanel.setBackground(new Color(135,122,253));

        loadingLabel = new JLabel();
        loadingLabel.setBounds(200,200,300,300);
        loadingGUIPanel.add(loadingLabel);

        listPanel = new JPanel(new BorderLayout());
        workArrayList = new ArrayList<>();
        for (int index = 0; index < workJsonArray.toArray().length; index++) {
            workArrayList.add("Work " + index);
        }
        workList = new JList<String>(workArrayList.toArray(new String[workArrayList.size()]));
        workListPane = new JScrollPane();
        workListPane.setViewportView(workList);
        workList.setLayoutOrientation(JList.VERTICAL);
        listPanel.add(workListPane);
        listPanel.setBounds(20, 20, 250, 355);
        workList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    int index = workList.getSelectedIndex();
                    ImageIcon work1 = imageUtils.proportionalResizeImage(workImages[index],
                            workHolder.getHeight(),workHolder.getWidth());
                    workHolder.setIcon(work1);
                    workDescription.setText(descriptions[index]);
                    workPrice.setText(prices[index]);
                    currentImageID = index;
                    isCurrImageURL = true;
                    currentImagePath = workImagesLinks[index];
                }
            }
        });
        updateWorkPanel.add(listPanel);

        workHolder = new JButton();
        workHolder.setBounds(300,20,350,300);
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
                        currentImagePath = file.getAbsolutePath();
                        ImageIcon resizedImage = imageUtils.proportionalResizeImage(image,
                                workHolder.getHeight(),workHolder.getWidth());
                        workHolder.setIcon(resizedImage);
                        workHolder.setHorizontalAlignment(SwingConstants.CENTER);
                        isCurrImageURL = false;
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        updateWorkPanel.add(workHolder);

        JLabel priceLabel = new JLabel();
        priceLabel.setText("Price");
        priceLabel.setBounds(300,330,50,30);
        updateWorkPanel.add(priceLabel);

        workPrice = new JTextField();
        workPrice.setBounds(300,360,350,30);
        updateWorkPanel.add(workPrice);

        JLabel descriptionLabel = new JLabel();
        descriptionLabel.setText("Description");
        descriptionLabel.setBounds(300,420,100,30);
        updateWorkPanel.add(descriptionLabel);

        workDescription = new JTextArea();
        workDescription.setBounds(300,460,350,100);
        workDescription.setLineWrap(true);
        updateWorkPanel.add(workDescription);

        updateButton = new JButton("Save Changes");
        updateButton.setBounds(300,590,150,50);
        updateButton.setBackground(new Color(255,153, 0));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                updateWorkPanel.setVisible(false);
                UpdateWorkOnline updateWorkOnline = new UpdateWorkOnline(currentImagePath,
                        workDescription.getText(),workPrice.getText(),currentImageID, isCurrImageURL);
                Thread updateWorkOnlineThread = new Thread(updateWorkOnline);
                updateWorkOnlineThread.start();
            }
        });
        updateWorkPanel.add(updateButton);

        addWorkButton = new JButton("Add Work");
        addWorkButton.setBounds(20,395,250,165);
        addWorkButton.setBackground(Color.green);
        addWorkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new AddArtWork(allJsonObject, imageOnlinePath+
                        workImages.length);
                setVisible(false);
            }
        });
        updateWorkPanel.add(addWorkButton);

        deleteWorkButton = new JButton("Delete work");
        deleteWorkButton.setBounds(470,590,150,50);
        deleteWorkButton.setBackground(Color.RED);
        deleteWorkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new DeleteWorkForm(form, currentImageID);
            }
        });
        updateWorkPanel.add(deleteWorkButton);

        backButton = new JButton("Back");
        backButton.setBounds(20,590,250,50);
        backButton.setBackground(Color.magenta);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
                setVisible(false);
            }
        });
        updateWorkPanel.add(backButton);


        add(updateWorkPanel);
        add(loadingGUIPanel);
        loadingGUIPanel.setVisible(false);



        SetUp setUp = new SetUp(workJsonArray);
        Thread setupThread = new Thread(setUp);
        setupThread.start();
        setLayout(null);
        setTitle("Work Update");

    }

    public void deleteProcedure(int currentWork)
    {
        DeleteProcedureClass deleteProcedureClass = new DeleteProcedureClass(currentWork);
        Thread deleteThread = new Thread(deleteProcedureClass);
        deleteThread.start();
    }

    class DeleteProcedureClass implements Runnable
    {
        private int currentID;

        public DeleteProcedureClass(int currentID)
        {
            this.currentID = currentID;
        }

        @Override
        public void run() {
            //TODO Delete current highlighted work
            updateWorkPanel.setVisible(false);
            LoadingGUI loadingGUI = new LoadingGUI();
            Thread loadingGUIthread = new Thread(loadingGUI);
            loadingGUIthread.start();

            JSONObject mainJson = JsonHandler.deleteWorkItem(allJsonObject,this.currentID);
            try {
                JsonHandler.updateJsonOnline(mainJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
            loadingGUI.stop();
            setVisible(false);
            new WorkUpdateForm(null,null,JsonHandler.getJsonAll());
        }
    }

    class SetUp implements Runnable
    {
        private JSONArray workArray;
        public SetUp(JSONArray workArray)
        {
            this.workArray = workArray;
        }

        @Override
        public void run()
        {
            try {
                workImages = JsonHandler.getWorkImages(workJsonArray);
                workImagesLinks = JsonHandler.getWorkImagesLinks(workJsonArray);
                descriptions = JsonHandler.getWorkDescriptions(workJsonArray);
                prices = JsonHandler.getWorkPrices(workJsonArray);
                pricesBuffer = new String[descriptions.length];

                for (int i=0;i<prices.length;i++)
                {
                    pricesBuffer[i] = descriptions[i];
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }

            if(m!=null)
            {
                m.loadingGUI.stop();
                m.setVisible(false);
            }
            if (a!=null)
            {
                a.loadingGUI.stop();
                a.setVisible(false);
            }
            if (loadingGUI !=null)
            {
                loadingGUI.stop();
            }

            if (workImages.length >0) {
                workList.setSelectedIndex(0);
            }
            setVisible(true);

        }
    }

    class UpdateWorkOnline implements Runnable
    {
        private String imagePath;
        private String description;
        private String price;
        private int workID;
        private boolean isURL;

        public UpdateWorkOnline(String imagePath, String description, String price, int workID,
                                boolean isURL)
        {
            this.imagePath = imagePath;
            this.description = description;
            this.price = price;
            this.workID = workID;
            this.isURL = isURL;
        }

        @Override
        public void run()
        {
            loadingGUI = new LoadingGUI();
            loadingGUIthread = new Thread(loadingGUI);
            loadingGUIthread.start();
            loadingGUIPanel.setVisible(true);
            try {
                String imageLink = ResourceServerHandler.StoreImage(isURL, imagePath,
                                                                    imageOnlinePath+workID);
                JSONObject mainJson = JsonHandler.updateWorkJsonArray(allJsonObject,workID,imageLink,description,price);
                JsonHandler.updateJsonOnline(mainJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
            loadingGUI.stop();
            new WorkUpdateForm(null,null,JsonHandler.getJsonAll());
            form.setVisible(false);
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
}

