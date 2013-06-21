package com.zimmerverwaltung.ui;

import com.zimmerverwaltung.ui.custom.panels.*;
import com.zimmerverwaltung.ui.util.CustomFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Framecontainer für das ImageViewerPanel
 * dient zur Bildbetrachtung für Zimmer, bei denen Bilder hinterlegt sind
 */
public class ImageFrame extends CustomFrame {
    private static ImageFrame instance;
    private ImageViewerPanel panel;

    /**
     * Initialisiert das UI mit dem Bildpfad
     * @param path Bildpfad
     */
    private ImageFrame(String path) {
        initUI(path);
    }

    /**
     * Initialisiert das UI
     * @param path Bildpfad
     */
    private void initUI(String path) {
        panel = new ImageViewerPanel(path);
        this.add(panel);
        this.setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
    }

    /**
     * Singleton
     * @param imgPath Bildpfad
     * @return gibt die Singleton - Instanz zurück
     */
    public static ImageFrame getImageFrame(String imgPath) {
        if(instance == null) {
            instance = new ImageFrame(imgPath);
            instance.setBounds(200, 200, instance.getPanel().getImgWidth() + 6, instance.getPanel().getImgHeight() + 29);
        }
        instance.setVisible(true);
        return instance;
    }



    public ImageViewerPanel getPanel() {
        return panel;
    }

    /**
     * Schließt das Fenster
     * Unsichtbar -> Dispose -> Singleton - Instanz null setzen
     */
    public void close() {
        this.setVisible(false);
        this.dispose();
        this.setNull();
    }

    /**
     * Setzt die Singleton - Instanz null
     */
    public static void setNull() {
        instance = null;
    }
}
