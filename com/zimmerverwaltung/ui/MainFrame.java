package com.zimmerverwaltung.ui;

import com.zimmerverwaltung.storage.handler.*;
import com.zimmerverwaltung.storage.io.*;
import com.zimmerverwaltung.ui.custom.CustomMouseAdapter;
import com.zimmerverwaltung.ui.custom.CustomTableModel;
import com.zimmerverwaltung.users.*;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 29.04.13
 * Time: 12:47
 * To change this template use File | Settings | File Templates.
 */
public class MainFrame extends JFrame {
    private User currentUser;

    private MainFrame() {
        initUI();
    }

    /**
     * Initialisiert die komplette Benutzeroberfläche
     */
    private void initUI() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int reply = JOptionPane.showConfirmDialog(getMainFrame(), "Daten vor Programmende sichern?", "Beenden...", JOptionPane.YES_NO_CANCEL_OPTION);

                switch(reply) {
                    case JOptionPane.YES_OPTION: {
                        DataHandler.writeAllData();
                        System.exit(0);
                        break;
                    }
                    case JOptionPane.NO_OPTION: {
                        System.exit(0);
                        break;
                    }
                    case JOptionPane.CANCEL_OPTION: {
                        break;
                    }
                }
            }
        });

        initDataGrid();
    }

    /**
     * Initialisiert die Datentabelle zur Darstellung aller Zimmer
     */
    private void initDataGrid() {
        CustomTableModel model = new CustomTableModel(DataHandler.mapToStringArray(ZvwDataType.EDT_ROOM), CsvIO.getColumnNames(ZvwDataType.EDT_ROOM));
        JTable grid = new JTable(model);
        grid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grid.addMouseListener(new CustomMouseAdapter());

        getContentPane().add(grid);
    }

    /**
     * Singleton
     */
    private static MainFrame instance;
    public static MainFrame getMainFrame() {
        if(instance == null) {
            instance = new MainFrame();
            instance.setResizable(false);
            instance.setBounds(20, 20, 600, 600);
            instance.setVisible(true);
        }
        return instance;
    }

    /**
     * Singleton #2 - Zur Erstinitialisierung, damit der Benutzername in der Titelleiste dargestellt wird
     * @param currentUser Der aktuell angemeldete Benutzer
     * @return Die MainFrame - Instanz
     */
    public static MainFrame getMainFrame(User currentUser) {
        if(instance == null) {
            instance = new MainFrame();
            instance.setResizable(false);
            instance.setBounds(20, 20, 600, 600);
            instance.setVisible(true);
            instance.setCurrentUser(currentUser);
            instance.setTitle("ZVW DHBW Lörrach - " + currentUser.getUserName());
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
