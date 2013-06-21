package com.zimmerverwaltung.ui.util;

import javax.swing.*;

/**
 * Erweitert JFrame um die abstrakte Methode close()
 */
public abstract class CustomFrame extends JFrame {
    public CustomFrame() {
        super();
    }

    /**
     * Jedes Fenster muss close implementieren, sonst wird Visible nur auf false gesetzt, das Fenster ist jedoch noch da
     */
    public abstract void close();
}
