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
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class UpdateAppMenuForm extends JFrame
{
    //public AtomicBoolean loaded = new AtomicBoolean(false);

    private MainMenu m;

    private boolean mainLogoUrl=true;
    private boolean newsLogoUrl=true;
    private boolean blogLogoUrl=true;
    private boolean contactLogoUrl=true;
    private boolean workLogoUrl=true;
    private boolean updateLogoUrl=true;

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

    protected JSONObject allJsonObject;
    private JSONObject mainJsonObject;

    private String serverDirectory = "getArt/NealeHowells/profile/";


    public UpdateAppMenuForm(MainMenu m,JSONObject jsonObject)
    {
        this.m = m;
        this.allJsonObject = jsonObject;
        this.mainJsonObject = (JSONObject)this.allJsonObject.get("main_page");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,700);
        setResizable(false);
        setTitle("Update Main Menu");
        getContentPane().setBackground(new Color(135,122,253));
        setLayout(null);

        //Set-Up Update Button
        btnUpdate = new JButton("UPDATE");
        btnUpdate.setBackground(new Color(150,0,34));
        btnUpdate.setBounds(400,10,275,120);
        btnUpdate.setFont(new Font("Calbri", Font.BOLD,18));
        btnUpdate.setForeground(new Color(255,255,255));
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String[] links = new String[]{mainLogo,contactLogo,blogLogo,workLogo,newsLogo};
                boolean[] urls = new boolean[]{mainLogoUrl,contactLogoUrl,blogLogoUrl,workLogoUrl,newsLogoUrl};
                String[] publicIds = new String[]{serverDirectory+"main",serverDirectory+"contact",
                                    serverDirectory+"blog",serverDirectory+"work",serverDirectory+"news"};

                UploadImages uploadImages = new UploadImages(links,publicIds,urls);
                Thread uploadThread = new Thread(uploadImages);
                uploadThread.start();
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
                        workLogo = file.getAbsolutePath();
                        Image resizedImage = image.getImage().getScaledInstance(btnWork.getWidth(),btnWork.getHeight()
                                , Image.SCALE_DEFAULT);
                        btnWork.setIcon(new ImageIcon(resizedImage));
                        workLogoUrl = false;
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
                        blogLogo = file.getAbsolutePath();
                        Image resizedImage = image.getImage().getScaledInstance(btnMainLogo.getWidth(),
                                btnMainLogo.getHeight(),Image.SCALE_DEFAULT);
                        btnBlog.setIcon(new ImageIcon(resizedImage));
                        blogLogoUrl = false;
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
                        contactLogo = file.getAbsolutePath();
                        Image resizedImage = image.getImage().getScaledInstance(btnContact.getWidth(),
                                btnContact.getHeight(),Image.SCALE_DEFAULT);
                        btnContact.setIcon(new ImageIcon(resizedImage));
                        contactLogoUrl = false;
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
                        newsLogo = file.getAbsolutePath();
                        Image resizedImage = image.getImage().getScaledInstance(btnNews.getWidth(),btnNews.getHeight()
                                , Image.SCALE_DEFAULT);
                        btnNews.setIcon(new ImageIcon(resizedImage));
                        newsLogoUrl = false;
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        add(btnNews);


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
                        mainLogo = file.getAbsolutePath();
                        Image resizedImage = image.getImage().getScaledInstance(btnMainLogo.getWidth(),
                                btnMainLogo.getHeight(),Image.SCALE_DEFAULT);
                        btnMainLogo.setIcon(new ImageIcon(resizedImage));
                        mainLogoUrl = false;
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        add(btnMainLogo);

        //Phone Background
        JPanel temp = new JPanel();
        temp.setBounds(10,10,380,640);
        temp.setBackground(new Color(0,0,0));
        add(temp);

        SetUpImages setUpImages = new SetUpImages();
        Thread setUpImagesThread = new Thread(setUpImages);
        setUpImagesThread.start();
    }

    class UploadImages implements Runnable
    {
        private String[] imagePaths;
        private String[] publicIds;
        private boolean[] urlPaths;

        public UploadImages(String[] imagePaths, String[] publicIds, boolean[] urlPaths)
        {
            this.imagePaths = imagePaths;
            this.publicIds = publicIds;
            this.urlPaths = urlPaths;
        }

        @Override
        public void run()
        {
            String[] links = new String[imagePaths.length];
            System.out.println("Starting Upload");
            for (int i=0;i<imagePaths.length;i++)
            {
                links[i] = ResourceServerHandler.StoreImage(urlPaths[i],imagePaths[i],publicIds[i]);
                System.out.println(i);
                //TODO if link[i]==null set up an error page
            }
            System.out.println(Arrays.toString(links));
            try {
                JSONObject json = JsonHandler.UpdateImageLinksMainMenuJson(mainJsonObject,links);
                json = JsonHandler.UpdateAllJson("main_page",json, allJsonObject);
                JsonHandler.updateJsonOnline(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                workLogo = JsonHandler.getMainMenuImageLink(mainJsonObject, "work");
                sizedIcon = icon.getImage().getScaledInstance(btnWork.getWidth(),btnWork.getHeight(),Image.SCALE_DEFAULT);
                btnWork.setIcon(new ImageIcon(sizedIcon));

            } catch (IOException e) {
                e.printStackTrace();
            }
            //loaded.set(true);
            setVisible(true);
            m.loadingGUI.stop();
            m.setVisible(false);
        }
    }


}
