package com.example.testgui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class imageUtils
{
    public static ImageIcon proportionalResizeImage(BufferedImage bufferedImage, int maxHeight, int maxWidth) {
        int aimHeight,aimWidth;
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        if (width <= maxWidth && height <= maxHeight)
        {
            return new ImageIcon(bufferedImage);
        }

        float multiplier = ((float)height / width);

        if (height>width)
        {
            aimHeight = maxHeight;
            aimWidth = Math.round(aimHeight / multiplier);
        }
        else
        {
            aimWidth = maxWidth;
            aimHeight = Math.round(aimWidth * multiplier);
        }

        BufferedImage resizedImage = new BufferedImage(aimWidth,aimHeight,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(bufferedImage, 0,0,aimWidth,aimHeight,null);
        graphics2D.dispose();

        return new ImageIcon(resizedImage);
    }

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
