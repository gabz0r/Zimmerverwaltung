package com.zimmerverwaltung.ui;

import com.zimmerverwaltung.ui.custom.panels.PasswordChangePanel;
import com.zimmerverwaltung.ui.util.CustomFrame;

/**
 * Oberfläche zum ändern des Passworts
 */
public class PasswordChangeFrame extends CustomFrame {
	private PasswordChangePanel content;

    /**
     * Konstruktor
     * Initialisiert das UI
     */
	private PasswordChangeFrame() {
        initUI();
	}

    /**
     * Initialisiert das UI
     */
    private void initUI() {
        content = new PasswordChangePanel();
        getContentPane().add(content);
    }

    /**
     * Schließt das Fenster
     * Unsichtbar -> Dispose -> Singleton - Instanz null setzen
     */
	@Override
	public void close() {
        instance = null;
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

    /**
     * Singleton für das PasswordChangeFrame
     */
	private static PasswordChangeFrame instance;
    public static PasswordChangeFrame getPasswordChangeFrame() {
	    if(instance == null) {
            instance = new PasswordChangeFrame();
            instance.setResizable(false);
            instance.setBounds(20, 20, 375, 150);
            instance.setTitle("Passwort ändern");
            instance.setVisible(true);
        }

        return instance;
	}
}
