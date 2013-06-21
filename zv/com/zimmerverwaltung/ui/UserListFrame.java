package com.zimmerverwaltung.ui;

import com.zimmerverwaltung.storage.container.Room;
import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.ui.util.*;
import com.zimmerverwaltung.users.User;
import com.zimmerverwaltung.users.extended.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Zeigt eine Benutzerliste an
 * Die Liste kann spezifiziert werden
 */
public class UserListFrame extends CustomFrame {

    private ArrayList<User> customList;
    private Room roomToAssign;

    private JPanel rootPanel;
    private JList userList;
    private JScrollPane scrollPane;
    private JButton okButton;

    /**
     * Konstruktor für eine Liste der kompletten Benutzer
     */
    private UserListFrame() {
        initUI();
    }

    /**
     * Konstruktor für eine benutzerdefinierte Liste
     * @param customList Die anzuzeigende Liste
     */
    private UserListFrame(ArrayList<User> customList) {
        this.customList = customList;
        initUI();
    }

    /**
     * Konstruktor für eine benutzerdefinierte Liste und die Zuweisungs - Funktionalität
     * @param customList Die anzuzeigende Liste
     * @param roomToAssign Der Raum, der zugewiesen werden soll
     */
    private UserListFrame(ArrayList<User> customList, Room roomToAssign) {
        this.customList = customList;
        this.roomToAssign = roomToAssign;
        initUI();
    }

    /**
     * Initialisiert die Benutzeroberfläche
     */
    private void initUI() {
        rootPanel = new JPanel(new SpringLayout());
        if(customList != null) {
            userList = new JList(customList.toArray());
        } else {
            userList = new JList(DataHandler.getUsers().toArray());
        }
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rootPanel.add(userList);

        scrollPane = new JScrollPane(rootPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollPane);

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(roomToAssign == null) {
                    close();
                } else {
                    String name = String.valueOf(userList.getSelectedValue()).split(" ")[0];
                    String lastName = String.valueOf(userList.getSelectedValue()).split(" ")[1];

                    ((Student)DataHandler.getCorrespondingUserObjectEx(name, lastName)).setMyRoomId(roomToAssign.getId());
                    close();
                }
            }
        });

        rootPanel.add(okButton);

        SpringUtilities.makeCompactGrid(rootPanel, 2, 1, 7, 7, 0, 5);
        getContentPane().add(rootPanel);
    }

    @Override
    public void close() {
        instance = null;
        this.setVisible(false);
        this.dispose();
    }


    /**
     * Singleton
     */
    private static UserListFrame instance;
    public static UserListFrame getUserListFrame() {
        if(instance == null) {
            instance = new UserListFrame();
        }
        instance.setVisible(true);
        instance.setResizable(false);
        instance.setBounds(200, 200, 100, 535);
        return instance;
    }

    public static UserListFrame getUserListFrame(ArrayList<User> customList) {
        if(instance == null) {
            instance = new UserListFrame(customList);
        }
        instance.setVisible(true);
        instance.setResizable(false);
        instance.setBounds(200, 200, 100, 535);
        return instance;
    }

    public static UserListFrame getUserListFrame(ArrayList<User> customList, Room roomToAssign) {
        if(instance == null) {
            instance = new UserListFrame(customList, roomToAssign);
        }
        instance.setVisible(true);
        instance.setResizable(false);
        instance.setBounds(200, 200, 100, 535);
        return instance;
    }
}
