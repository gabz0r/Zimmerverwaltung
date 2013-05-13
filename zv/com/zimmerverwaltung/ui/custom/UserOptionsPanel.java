package com.zimmerverwaltung.ui.custom;

import com.zimmerverwaltung.users.*;
import com.zimmerverwaltung.users.extended.*;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 13.05.13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public class UserOptionsPanel<T extends User> extends JPanel {
    private JLabel currentRole;

    private T currentUser;

    public UserOptionsPanel(T currentUser) {
        this.currentUser = currentUser;

        currentRole = new JLabel();

        if(this.currentUser instanceof Student) {
            currentRole.setText("Rechte: Student");
        }
    }
}
