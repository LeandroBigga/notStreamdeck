package com.example.streamdeck;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ExeIconExtractor {

    public static Image extract(String exePath) {
        try {
            File file = new File(exePath);
            Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);

            BufferedImage img = new BufferedImage(
                    icon.getIconWidth(),
                    icon.getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            Graphics g = img.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();

            return SwingFXUtils.toFXImage(img, null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}