package com.zimmerverwaltung.ui.custom.panels;

import com.zimmerverwaltung.ui.MainFrame;
import com.zimmerverwaltung.ui.util.CustomFrame;
import com.zimmerverwaltung.ui.util.CustomPanel;
import com.zimmerverwaltung.ui.util.SpringUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel für die Passwort - ändern Funktionalität
 */
public class PasswordChangePanel extends CustomPanel {
    private JLabel newPwdLabel;
    private JTextField newPwd;

    private JLabel confirmPwdLabel;
    private JTextField confirmPwd;

    private JButton changePwd;

    /**
     * Konstruktor initialisiert die Benutzeroberfläche
     */
    public PasswordChangePanel() {
        newPwdLabel = new JLabel("Neues Passwort", JLabel.TRAILING);
        newPwd = new JTextField(10);

        confirmPwdLabel = new JLabel("Passwort bestätigen", JLabel.TRAILING);
        confirmPwd = new JTextField(10);
        changePwd = new JButton("Ändern");

        initUI();
    }

    /**
     * Legt das Layout an, verknüpft Events (-> Button)
     */
    public void initUI() {
        this.setLayout(new SpringLayout());

        this.add(newPwdLabel);
        this.add(newPwd);

        this.add(confirmPwdLabel);
        this.add(confirmPwd);

        JButton dummy = new JButton();
        dummy.setVisible(false);
        this.add(dummy); //Dummy

        this.add(changePwd);
        changePwd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(newPwd.getText().equals(confirmPwd.getText())) {
                    MainFrame.getMainFrame().getCurrentUser().changePassword(confirmPwd.getText());
                    JOptionPane.showMessageDialog(MainFrame.getMainFrame(), "Passwort geändert!", "Nachricht", JOptionPane.OK_OPTION);
                    close();
                }
                else {
                    JOptionPane.showMessageDialog(MainFrame.getMainFrame(), "Passwörter stimmen nicht überein!", "Fehler", JOptionPane.OK_OPTION);
                }
            }
        });

        SpringUtilities.makeCompactGrid(this, 3, 2, 10, 10, 10, 10);
    }

    /**
     * Schließt das Fenster
     * -> Unsichtbar machen, Parentframe schließen
     */
    public void close() {
        this.setVisible(false);
        CustomFrame cf = (CustomFrame) SwingUtilities.getRoot(this);
        cf.close();
    }
}
