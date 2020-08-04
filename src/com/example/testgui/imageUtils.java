package com.example.testgui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class imageUtils
{
    public static ImageIcon imageChooser()
    {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        ImageIcon image;
        Image resizedImage;
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            try
            {
                image = new ImageIcon(ImageIO.read(file));
                return image;

            } catch (IOException ioException) {
                //ioException.printStackTrace();
            } catch (NullPointerException e)
            {

            }
        }
        return null;

    }

}
