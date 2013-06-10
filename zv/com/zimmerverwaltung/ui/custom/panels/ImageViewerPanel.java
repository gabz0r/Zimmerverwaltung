package com.zimmerverwaltung.ui.custom.panels;

import com.zimmerverwaltung.ui.util.CustomPanel;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 21.05.13
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */

/**
 * Panel für die Bildvorschau
 */
public class ImageViewerPanel extends CustomPanel {
    private BufferedImage img;

    /**
     * Konstruktor liest die Bilddatei aus und weißt sie dem BufferedImage zu
     * @param path
     */
    public ImageViewerPanel(String path) {
        try {
            img = ImageIO.read(new File(path));

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * Override lässt das Bild zeichnen
     * @param g Graphics - Objekt, mit dem gezeichnet wird
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

    public int getImgHeight() {
        return img.getHeight();
    }

    public int getImgWidth() {
        return img.getWidth();
    }
}
