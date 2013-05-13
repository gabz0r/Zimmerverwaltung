package com.zimmerverwaltung.ui;

import com.zimmerverwaltung.storage.handler.*;
import com.zimmerverwaltung.storage.io.*;
import com.zimmerverwaltung.ui.custom.*;
import com.zimmerverwaltung.users.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
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
    private JPanel wrapper;

    CustomTableModel model;
    CustomTable grid;
    RoomInfoPanel infoPanel;

    private MainFrame() {
        initUI();
    }

    /**
     * Initialisiert die komplette Benutzeroberfläche
     */
    private void initUI() {
        wrapper = new JPanel(new GridLayout(2,2));
        getContentPane().add(wrapper);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int reply = JOptionPane.showConfirmDialog(getMainFrame(), "Daten vor Programmende sichern?", "Beenden...", JOptionPane.YES_NO_CANCEL_OPTION);

                switch (reply) {
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
        initInfoPanel();
    }

    /**
     * Initialisiert die Datentabelle zur Darstellung aller Zimmer
     */
    private void initDataGrid() {
        model = new CustomTableModel(DataHandler.mapToStringArray(ZvwDataType.EDT_ROOM), CsvIO.getColumnNames(ZvwDataType.EDT_ROOM));
        grid = new CustomTable(model);

        grid.hideColumns(new String[] { "id", "Bild" });

        grid.setDefaultRenderer(Object.class, new CustomTableRenderer());
        grid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grid.addMouseListener(new CustomMouseAdapter());

        JScrollPane pane = new JScrollPane(grid);
        wrapper.add(pane);
    }

    /**
     * Initialisiert die Groupbox zur Darstellung zum Zimmer
     */
    private void initInfoPanel() {
        infoPanel = new RoomInfoPanel();
        TitledBorder groupBox = new TitledBorder("Info");
        infoPanel.setBorder(groupBox);

        wrapper.add(infoPanel);
    }

    /**
     * Singleton
     */
    private static MainFrame instance;
    public static MainFrame getMainFrame() {
        if(instance == null) {
            instance = new MainFrame();
            instance.setResizable(false);
            instance.setBounds(20, 20, 1000, 600);
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
            instance.setBounds(20, 20, 1000, 600);
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

    public CustomTable getGrid() {
        return grid;
    }

    public RoomInfoPanel getInfoPanel() {
        return infoPanel;
    }
}