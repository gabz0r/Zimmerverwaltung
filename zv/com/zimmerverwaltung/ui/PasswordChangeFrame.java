package com.zimmerverwaltung.ui;

import com.zimmerverwaltung.ui.custom.panels.PasswordChangePanel;
import com.zimmerverwaltung.ui.util.CustomFrame;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 07.06.13
 * Time: 10:31
 * To change this template use File | Settings | File Templates.
 */
public class PasswordChangeFrame extends CustomFrame {
	private PasswordChangePanel content;

	private PasswordChangeFrame() {

	}

    private void initUI() {
        content = new PasswordChangePanel();
        getContentPane().add(content);
    }

	@Override
	public void close() {
        instance = null;
		setVisible(false);
		dispose();
	}

	private static PasswordChangeFrame instance;
    public static PasswordChangeFrame getPasswordChangeFrame() {
	    if(instance == null) {
            instance = new PasswordChangeFrame();
            instance.setResizable(false);
            instance.setBounds(20, 20, 375, 150);
            instance.setTitle("Passwort ändern");
            instance.setVisible(true);
            instance.initUI();
        }

        return instance;
	}
}
