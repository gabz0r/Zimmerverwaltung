package com.zimmerverwaltung.ui;

import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.ui.util.CustomFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 30.04.13
 * Time: 11:51
 * To change this template use File | Settings | File Templates.
 */

/**
 * LoginFrame stellt das Login - Fenster dar
 * Es gibt zwei Textfelder und einen Button, der die Daten überprüft
 */
public class LoginFrame extends CustomFrame {
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
        getContentPane().setLayout(null);

        userName = new JTextField();
        password = new JPasswordField();
        login = new JButton("Login");

        userName.setBounds(10, 10, 200, 25);
        password.setBounds(10, 40, 200, 25);
        login.setBounds(10, 70, 200, 25);



        setResizable(false);

        getContentPane().add(userName);
        getContentPane().add(password);
        getContentPane().add(login);

        pack();



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
            instance.setBounds(200, 200, 225, 140);
            instance.setTitle("Login");
            instance.setVisible(true);
        }
        instance.setVisible(true);
        return instance;
    }

    public void close() {
        this.setVisible(false);
        this.dispose();
        LoginFrame.setNull();
    }

    public static void setNull() {
        instance = null;
    }
}
