package com.example.testgui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class imageUtils
{
    public static File imageChooser()
    {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            return file;
        }
        return null;
    }
    public static BufferedImage rotateImage(BufferedImage image, double theta)
    {
        int w = image.getWidth();
        int h = image.getHeight();

        BufferedImage rotated = new BufferedImage(w,h,image.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(theta,w/2,h/2);
        graphic.drawImage(image, null,0,0);
        graphic.dispose();
        return rotated;

    }

}
