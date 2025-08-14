package com.warcardgame.game;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class CommonGUI {


    /**
     * Scales an image to the specified dimensions (200x290) and returns it as an ImageIcon.
     *
     * @param path The relative path to the image file.
     * @return A scaled ImageIcon if the image is successfully read; otherwise, returns null.
     */
    protected ImageIcon scaleImageIcon(String path) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(path));
            int width = 200;
            int height = 290;

            // Scale the image to the specified dimensions
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
