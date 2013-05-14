package com.zimmerverwaltung.ui.custom;

import com.zimmerverwaltung.storage.container.*;
import com.zimmerverwaltung.storage.handler.*;
import com.zimmerverwaltung.storage.io.*;
import static com.zimmerverwaltung.ui.MainFrame.*;
import com.zimmerverwaltung.ui.util.*;
import com.zimmerverwaltung.users.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 13.05.13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public class UserOptionsPanel<T extends User> extends JPanel {
    private JLabel currentRole;
    JTabbedPane optionsPane;

    JPanel generalOptions;
    JPanel searchOptions;
    JPanel roleOptions;

    JButton searchButton;

    //Suchfelder
    JTextField distance;
    JTextField fees;
    JTextField location;
    JTextField qm;
    JTextField street;

    private T currentUser;

    public UserOptionsPanel(T currentUser) {
        setLayout(new BorderLayout());

        this.currentUser = currentUser;

        optionsPane = new JTabbedPane();

        generalOptions = new JPanel();
        searchOptions = new JPanel();
        roleOptions = new JPanel();

        setupSearchTab();

        currentRole = new JLabel();

        currentRole.setText("Rolle: " + currentUser.getRoleName());

        optionsPane.addTab("Allgemein", generalOptions);
        optionsPane.addTab("Suche", searchOptions);
        optionsPane.addTab("Rollenoptionen: " + currentUser.getRoleName(), roleOptions);

        this.add(optionsPane, BorderLayout.CENTER);
    }

    /**
     * Richtet den Tab "Suche" ein
     */
    private void setupSearchTab() {
        JLabel distanceLabel = new JLabel("Entfernung DH", JLabel.TRAILING);
        JLabel feesLabel = new JLabel("Mietkosten", JLabel.TRAILING);
        JLabel locationLabel = new JLabel("Ort", JLabel.TRAILING);
        JLabel qmLabel = new JLabel("Wohnfläche", JLabel.TRAILING);
        JLabel streetLabel = new JLabel("Straße", JLabel.TRAILING);
        JLabel dummyButton = new JLabel("", JLabel.TRAILING);

        searchOptions.setLayout(new SpringLayout());
        distance = new JTextField(10);
        fees = new JTextField(10);
        location = new JTextField(10);
        qm = new JTextField(10);
        street = new JTextField(10);

        searchButton = new JButton("Suchen");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!validateTextFields()) {
                    return;
                }

                ArrayList<Room> results = DataHandler.searchRoomsByTokens(new RoomTokens(distance.getText(),
                                                                                    fees.getText(),
                                                                                    location.getText(),
                                                                                    qm.getText(),
                                                                                    street.getText()));
                CustomTableModel searchResult = new CustomTableModel(DataHandler.mapToStringArray(results), CsvIO.getColumnNames(ZvwDataType.EDT_ROOM));
                getMainFrame().getGrid().setModel(searchResult);

                getMainFrame().getGrid().hideColumns(new String[]{"id", "Bild"});

                getMainFrame().getGrid().setDefaultRenderer(Object.class, getMainFrame().getTableRenderer());
                getMainFrame().getGrid().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                getMainFrame().getGrid().addMouseListener(getMainFrame().getMouseAdapter());
            }
        });

        distanceLabel.setLabelFor(distance);
        feesLabel.setLabelFor(fees);
        locationLabel.setLabelFor(location);
        qmLabel.setLabelFor(qm);
        streetLabel.setLabelFor(street);
        dummyButton.setLabelFor(searchButton);

        searchOptions.add(distanceLabel);
        searchOptions.add(distance);

        searchOptions.add(feesLabel);
        searchOptions.add(fees);

        searchOptions.add(locationLabel);
        searchOptions.add(location);

        searchOptions.add(qmLabel);
        searchOptions.add(qm);

        searchOptions.add(streetLabel);
        searchOptions.add(street);

        searchOptions.add(dummyButton);
        searchOptions.add(searchButton);

        SpringUtilities.makeCompactGrid(searchOptions,6, 2, 10, 10, 10, 10);
    }

    private boolean validateTextFields() {
        try {
            if(!distance.getText().equals("")) {
                Float.parseFloat(distance.getText());
            }
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(getMainFrame(), "Falsches Eingabeformat: Entfernung DH");
            return false;
        }

        try {
            if(!fees.getText().equals("")) {
                Float.parseFloat(fees.getText());
            }
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(getMainFrame(), "Falsches Eingabeformat: Mietkosten");
            return false;
        }

        try {
            if(!qm.getText().equals("")) {
                Float.parseFloat(qm.getText());
            }
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(getMainFrame(), "Falsches Eingabeformat: Wohnfläche");
            return false;
        }

        return true;
    }
}