package com.zimmerverwaltung.ui;

import com.zimmerverwaltung.storage.container.Room;
import com.zimmerverwaltung.storage.handler.*;
import com.zimmerverwaltung.storage.io.*;
import com.zimmerverwaltung.ui.custom.panels.RoomInfoPanel;
import com.zimmerverwaltung.ui.custom.panels.UserOptionsPanel;
import com.zimmerverwaltung.ui.custom.roomtable.CustomMouseAdapter;
import com.zimmerverwaltung.ui.custom.roomtable.CustomTable;
import com.zimmerverwaltung.ui.custom.roomtable.CustomTableModel;
import com.zimmerverwaltung.ui.dispatcher.EventDispatcher;
import com.zimmerverwaltung.ui.dispatcher.EventTargets;
import com.zimmerverwaltung.ui.util.CustomFrame;
import com.zimmerverwaltung.ui.util.CustomPanel;
import com.zimmerverwaltung.users.*;
import com.zimmerverwaltung.users.extended.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Hauptbenutzeroberfläche
 */
public class MainFrame extends CustomFrame {
    private User currentUser;
    private CustomPanel topWrapper;
    private CustomPanel bottomWrapper;

    private CustomTableModel model;
    private CustomTable grid;
    private RoomInfoPanel infoPanel;
    private UserOptionsPanel optionsPanel;

    private CustomMouseAdapter mouseAdapter;

    private Room selectedRoom;

    private MainFrame() {
    }

    /**
     * Initialisiert die komplette Benutzeroberfläche
     */
    public void initUI() {
        this.setLayout(new GridLayout(2, 1));

        topWrapper = new CustomPanel(new GridLayout(1, 1, 10, 10));
        getContentPane().add(topWrapper);

        bottomWrapper = new CustomPanel(new GridLayout(1, 2, 10, 10));
        getContentPane().add(bottomWrapper);

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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                if(getMainFrame().getCurrentUser() instanceof Student) {
                    for(Room r : ((Student)getCurrentUser()).getWatchList()) {
                        EventDispatcher.getInstance().dispatch(EventTargets.EET_DATATABLE, r);
                    }
                }
            }
        });

        initDataGrid();
        initInfoPanel();
        initOptionsPanel();
    }

    /**
     * Initialisiert die Datentabelle zur Darstellung aller Zimmer
     */
    private void initDataGrid() {
        mouseAdapter = new CustomMouseAdapter();

        model = new CustomTableModel(DataHandler.mapToStringArray(ZvwDataType.EDT_ROOM), CsvIO.getColumnNames(ZvwDataType.EDT_ROOM));

        grid = new CustomTable(model);

        if(getMainFrame().getCurrentUser() instanceof Student) {
            grid.hideColumns(new String[] { "id", "Bild" });
        }
        else {
            grid.hideColumns(new String[] { "id", "Bild", "Gemerkt" });
        }

        grid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grid.addMouseListener(mouseAdapter);

        JScrollPane pane = new JScrollPane(grid);
        topWrapper.add(pane);
    }

    /**
     * Initialisiert die Groupbox zur Darstellung des Zimmers
     */
    private void initInfoPanel() {
        infoPanel = new RoomInfoPanel();
        TitledBorder groupBox = new TitledBorder("Info");
        infoPanel.setBorder(groupBox);

        bottomWrapper.add(infoPanel);
    }

    /**
     * Initialisiert die Groupbox, welche die Operationen bereitstellt
     */
    private void initOptionsPanel() {
        if(getMainFrame().getCurrentUser() instanceof Student) {
            optionsPanel = new UserOptionsPanel<Student>((Student)getMainFrame().getCurrentUser());
        }
        else if(getMainFrame().getCurrentUser() instanceof Landlord) {
            optionsPanel = new UserOptionsPanel<Landlord>((Landlord)getMainFrame().getCurrentUser());
        }
        else if(getMainFrame().getCurrentUser() instanceof Employee) {
            optionsPanel = new UserOptionsPanel<Employee>((Employee)getMainFrame().getCurrentUser());
        }

        TitledBorder groupBox = new TitledBorder("Bedienelemente");
        optionsPanel.setBorder(groupBox);

        bottomWrapper.add(optionsPanel);
    }

    /**
     * Schließt das Fenster
     * Unsichtbar -> Dispose -> Singleton - Instanz null setzen
     * Die Anwendung läuft allerdings weiter
     */
    public void close() {
        this.setVisible(false);
        this.dispose();
        MainFrame.setNull();
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
            instance.initUI();
        }
        instance.setVisible(true);
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
            instance.initUI();
        }
        instance.setVisible(true);
        return instance;
    }

    /**
     * Setzt die Singleton - Instanz null
     */
    public static void setNull() {
        instance = null;
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

    public CustomMouseAdapter getMouseAdapter() {
        return mouseAdapter;
    }

    public Room getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Room r) {
        selectedRoom = r;
    }
}
