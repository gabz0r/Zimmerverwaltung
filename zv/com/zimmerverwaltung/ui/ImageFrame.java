package com.zimmerverwaltung.ui;

import com.zimmerverwaltung.ui.custom.panels.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 21.05.13
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
public class ImageFrame extends JFrame {
    private static ImageFrame instance;
    private ImageViewerPanel panel;

    private ImageFrame(String path) {
        initUI(path);
    }

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

    public static ImageFrame getImageFrame(String imgPath) {
        if(instance == null) {
            instance = new ImageFrame(imgPath);
            instance.setBounds(200, 200, instance.getPanel().getImgWidth() + 6, instance.getPanel().getImgHeight() + 29);
        }
        instance.setVisible(true);
        return instance;
    }

    public void close() {
        this.setVisible(false);
        this.dispose();
        ImageFrame.setNull();
    }

    public ImageViewerPanel getPanel() {
        return panel;
    }

    public static void setNull() {
        instance = null;
    }
}
