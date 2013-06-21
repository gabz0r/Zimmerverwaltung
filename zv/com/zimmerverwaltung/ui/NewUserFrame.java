package com.zimmerverwaltung.ui;

import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.ui.util.CustomFrame;
import com.zimmerverwaltung.ui.util.SpringUtilities;
import com.zimmerverwaltung.users.User;
import com.zimmerverwaltung.users.extended.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 15.06.13
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */

/**
 * Zum erstellen eines neuen Benutzers
 */
public class NewUserFrame extends CustomFrame {
    private JPanel rootPanel;

    private JLabel nameLabel;
    private JLabel lastNameLabel;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JLabel roleChooserLabel;


    private JTextField name;
    private JTextField lastName;
    private JTextField userName;
    private JPasswordField password;
    private JPasswordField confirmPassword;
    private JComboBox roleChooser;

    private JButton createUser;


    private NewUserFrame() {
        initUI();
    }

    @Override
    public void close() {
        instance = null;
        setVisible(false);
        dispose();
    }

    /**
     * Initialisiert die Benutzeroberfläche (Legt Elemente an und bestimmt das Layout)
     */
    private void initUI() {
        setTitle("Neuen Benutzer anlegen");

        rootPanel = new JPanel(new SpringLayout());

        nameLabel = new JLabel("Vorname");
        lastNameLabel = new JLabel("Nachname");
        userNameLabel = new JLabel("Benutzername");
        passwordLabel = new JLabel("Passwort");
        confirmPasswordLabel = new JLabel("Passwort bestätigen");
        roleChooserLabel = new JLabel("Rolle");

        name = new JTextField();
        lastName = new JTextField();
        lastName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(lastName.getText().length() > 3) {
                    userName.setText(name.getText().substring(0, 3) + "_" + lastName.getText().substring(0, 3));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) { }
        });

        userName = new JTextField();
        password = new JPasswordField();
        confirmPassword = new JPasswordField();
        roleChooser = new JComboBox(new Object[] { "Student", "Vermieter" });
        roleChooser.setEditable(false);

        createUser = new JButton("Anlegen");
        createUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(((String) roleChooser.getSelectedItem()).equals("Student")) {
                    if(validateFields()) {
                        DataHandler.getUsers().add(new Student(name.getText(), lastName.getText(),
                                                               userName.getText(), String.valueOf(password.getPassword())));
                        DataHandler.writeAllData();
                        close();
                    }
                }
            }
        });

        rootPanel.add(nameLabel);
        rootPanel.add(name);

        rootPanel.add(lastNameLabel);
        rootPanel.add(lastName);

        rootPanel.add(userNameLabel);
        rootPanel.add(userName);

        rootPanel.add(passwordLabel);
        rootPanel.add(password);

        rootPanel.add(confirmPasswordLabel);
        rootPanel.add(confirmPassword);

        rootPanel.add(roleChooserLabel);
        rootPanel.add(roleChooser);

        JButton dummy = new JButton();
        dummy.setVisible(false);

        rootPanel.add(dummy);
        rootPanel.add(createUser);

        SpringUtilities.makeCompactGrid(rootPanel, 7, 2, 10, 10, 10, 10);

        getContentPane().add(rootPanel);
    }

    /**
     * Überprüft die Textfelder auf korrekte Eingabe
     * @return Wahrheitswert, entsprechend dem Ergebnis der Überprüfung
     */
    private boolean validateFields() {
        if(name.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vorname nicht ausgefüllt!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if(lastName.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nachname nicht ausgefüllt!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if(userName.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Benutzername nicht ausgefüllt!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if(String.valueOf(password.getPassword()).equals("")) {
            JOptionPane.showMessageDialog(this, "Passwort nicht ausgefüllt!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if(String.valueOf(confirmPassword.getPassword()).equals("")) {
            JOptionPane.showMessageDialog(this, "Passwort bitte bestätigen!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if(!String.valueOf(password.getPassword()).equals(String.valueOf(confirmPassword.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Passwörter stimmen nicht überein!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private static NewUserFrame instance;
    public static NewUserFrame getNewUserFrame() {
        if(instance == null) {
            instance = new NewUserFrame();
        }
        instance.setVisible(true);
        instance.setBounds(100, 100, 400, 300);
        instance.setResizable(false);
        return instance;
    }
}
