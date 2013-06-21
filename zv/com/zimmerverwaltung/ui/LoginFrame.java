package com.zimmerverwaltung.ui;

import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.ui.util.CustomFrame;
import com.zimmerverwaltung.ui.util.SpringUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * LoginFrame stellt das Login - Fenster dar
 * Es gibt zwei Textfelder und einen Button, der die Daten überprüft
 */
public class LoginFrame extends CustomFrame {
    private JPanel rootPanel;

    JLabel userNameLabel;
    JLabel passwordLabel;

    private JTextField userName;
    private JPasswordField password;

    private JButton login;

    /**
     * Konstruktor
     */
    private LoginFrame() {
        initUI();
    }

    /**
     * Initialisiert das gesamte Login - Fenster
     * Setzt Größe, Position, verschiedene Properties und das Click - Event für den Button
     */
    private void initUI() {
        rootPanel = new JPanel(new SpringLayout());

        userNameLabel = new JLabel("Benutzername");
        rootPanel.add(userNameLabel);

        userName = new JTextField();
        rootPanel.add(userName);

        passwordLabel = new JLabel("Passwort");
        rootPanel.add(passwordLabel);

        password = new JPasswordField();
        rootPanel.add(password);

        login = new JButton("Login");
        JButton dummy = new JButton();
        dummy.setVisible(false);

        rootPanel.add(dummy);
        rootPanel.add(login);

        SpringUtilities.makeCompactGrid(rootPanel, 3, 2, 10, 10, 10, 10);

        getContentPane().add(rootPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /**
         * Die Daten werden an den DataHandler weitergeleitet und dort überprüft
         * Je nach dem wird der Vorgang abgebrochen (Logindaten falsch), oder das MainFrame wird geöffnet
         */
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pw = String.valueOf(password.getPassword());
                if(!DataHandler.isValidUser(userName.getText(), pw)) {
                    JOptionPane.showMessageDialog(getLoginFrame(), "Ungültige Benutzerdaten!", "Fehler", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    MainFrame.getMainFrame(DataHandler.getCorrespondingUserObject(userName.getText(), pw));
                    close();
                }
            }
        });
    }

    private static LoginFrame instance;
    /**
     * Singleton
     * @return Die Instanz des LoginFrames
     */
    public static LoginFrame getLoginFrame() {
        if(instance == null) {
            instance = new LoginFrame();
            instance.setBounds(200, 200, 270, 160);
            instance.setTitle("Login");
            instance.setVisible(true);
        }
        instance.setResizable(false);
        instance.setVisible(true);
        return instance;
    }

    /**
     * Schließt das Fenster
     * Unsichtbar -> Dispose -> Singleton - Instanz null setzen
     */
    public void close() {
        this.setVisible(false);
        this.dispose();
        LoginFrame.setNull();
    }

    /**
     * Setzt die Singleton - Instanz null
     */
    public static void setNull() {
        instance = null;
    }
}
