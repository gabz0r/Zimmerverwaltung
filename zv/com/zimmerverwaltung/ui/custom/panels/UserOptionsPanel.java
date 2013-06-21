package com.zimmerverwaltung.ui.custom.panels;

import com.zimmerverwaltung.storage.container.*;
import com.zimmerverwaltung.storage.handler.*;
import com.zimmerverwaltung.storage.io.*;
import static com.zimmerverwaltung.ui.MainFrame.*;

import com.zimmerverwaltung.ui.NewUserFrame;
import com.zimmerverwaltung.ui.PasswordChangeFrame;
import com.zimmerverwaltung.ui.RoomComboFrame;
import com.zimmerverwaltung.ui.UserListFrame;
import com.zimmerverwaltung.ui.custom.roomtable.CustomTableModel;
import com.zimmerverwaltung.ui.util.*;
import com.zimmerverwaltung.users.*;
import com.zimmerverwaltung.users.extended.Employee;
import com.zimmerverwaltung.users.extended.Landlord;
import com.zimmerverwaltung.users.extended.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Panel enthält Komponenten, die den Operationen entsprechen, die ein aktueller Benutzer entsprechend seiner Rolle ausführen kann
 * @param <T> Typ des aktuellen Benutzers
 */
public class UserOptionsPanel<T extends User> extends CustomPanel {
    private JLabel currentRole;
    JTabbedPane optionsPane;

    JPanel generalOptions;
    JPanel searchOptions;
    JPanel roleOptions;

    //Felder im Allgemeintab
    JButton logout;
    JButton save;
    JLabel userName;
    JLabel firstName;
    JLabel lastName;
    JLabel role;
    JButton changePwd;

    //Felder im Suchtab
    JTextField distance;
    JTextField fees;
    JTextField location;
    JTextField qm;
    JTextField street;
    JButton searchButton;

    private T currentUser;

    /**
     * Konstruktor initalisiert die UI passend zum aktuellen Benutzer
     * @param currentUser Der aktuelle Benutzer
     */
    public UserOptionsPanel(T currentUser) {
        setLayout(new BorderLayout());

        this.currentUser = currentUser;

        optionsPane = new JTabbedPane();

        generalOptions = new JPanel();
        searchOptions = new JPanel();
        roleOptions = new JPanel();

        setupGeneralTab();
        setupSearchTab();
        setupOptionsTab(currentUser);

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

                getMainFrame().getGrid().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

        SpringUtilities.makeCompactGrid(searchOptions,6, 2, 10, 10, 5, 5);
    }

    /**
     * General beinhaltet allgemeine Operationen, die für alle Benutzer gleich sind
     */
    private void setupGeneralTab() {
        generalOptions.setLayout(new SpringLayout());

        logout = new JButton("Ausloggen");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(getMainFrame(), "Ausloggen?", "Zimmerverwaltung", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    getMainFrame().getCurrentUser().logout();
                }
            }
        });

        save = new JButton("Änderungen speichern");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataHandler.writeAllData();
            }
        });

        userName = new JLabel("Benutzername: " + getMainFrame().getCurrentUser().getUserName());
        firstName = new JLabel("Vorname: " + getMainFrame().getCurrentUser().getFirstName());
        lastName = new JLabel("Nachname: " + getMainFrame().getCurrentUser().getLastName());
        role = new JLabel("Rolle: " + getMainFrame().getCurrentUser().getRoleName());

        changePwd = new JButton("Passwort ändern");
        changePwd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordChangeFrame pwc = PasswordChangeFrame.getPasswordChangeFrame();
            }
        });

        generalOptions.add(logout);
        generalOptions.add(save);
        generalOptions.add(userName);
        generalOptions.add(firstName);
        generalOptions.add(lastName);
        generalOptions.add(role);
        generalOptions.add(changePwd);

        SpringUtilities.makeCompactGrid(generalOptions, 7, 1, 20, 10, 5, 9);
    }

    /**
     * Validiert die Eingaben in den Suchfeldern
     * @return true / false, je nach dem
     */
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

    /**
     * Generiert das Benutzeroptionen - Panel je nach übergebenen Nutzertyp
     * @param u Benutzer, für den das Panel eingerichtet werden soll
     */
    private void setupOptionsTab(User u) {
        if(u instanceof Student) {
            //Keine besonderen Optionen
        } else if(u instanceof Landlord) {
            roleOptions.setLayout(new SpringLayout());

            createLandlordOptions();

            SpringUtilities.makeCompactGrid(roleOptions, 4, 1, 30, 25, 20, 20);
        } else /* Employee */ {
            roleOptions.setLayout(new SpringLayout());

            createLandlordOptions();
            createEmployeeOptions();

            SpringUtilities.makeCompactGrid(roleOptions, 4, 2, 30, 25, 20, 20);
        }
    }


    /**
     * Erstellt die für den Vermieter relevanten Optionen
     * Ausgelagert, um die Fkt in Vermieter und Mitarbeiter besser einsetzen zu können
     */
    private void createLandlordOptions() {
        //Option, um nur die selbst zur Vermietung freigebenen Räume anzuzeigen
        JButton onlyMyRooms = new JButton("Nur eigene Zimmer anzeigen");
        onlyMyRooms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Room> mr;
                if(currentUser instanceof Landlord) {
                    mr = ((Landlord) getMainFrame().getCurrentUser()).getMyRooms();
                } else /*Employee*/ {
                    mr = ((Employee) getMainFrame().getCurrentUser()).getMyRooms();
                }

                CustomTableModel searchResult = new CustomTableModel(DataHandler.mapToStringArray(mr), CsvIO.getColumnNames(ZvwDataType.EDT_ROOM));

                getMainFrame().getGrid().setModel(searchResult);

                getMainFrame().getGrid().hideColumns(new String[]{ "id", "Bild", "Gemerkt" });

                getMainFrame().getGrid().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }
        });

        //Alle Räume anzeigen
        JButton showAllRooms = new JButton("Alle anzeigen");
        showAllRooms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomTableModel searchResult = new CustomTableModel(DataHandler.mapToStringArray(DataHandler.getRooms()), CsvIO.getColumnNames(ZvwDataType.EDT_ROOM));

                getMainFrame().getGrid().setModel(searchResult);

                getMainFrame().getGrid().hideColumns(new String[]{ "id", "Bild", "Gemerkt" });

                getMainFrame().getGrid().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }
        });

        //Eigenschaften eines Zimmers ändern
        JButton changeRoomProperties = new JButton("Zimmer bearbeiten");
        changeRoomProperties.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Room r = getMainFrame().getSelectedRoom();
                if(r == null) {
                    JOptionPane.showMessageDialog(getMainFrame(), "Kein Zimmer ausgewählt!", "Fehler", JOptionPane.ERROR_MESSAGE);
                } else if(!getMainFrame().getCurrentUser().getRoleName().equals("Mitarbeiter") && !r.getLandlord().equals(getMainFrame().getCurrentUser().getUserName())) {
                    JOptionPane.showMessageDialog(getMainFrame(), "Sie sind nicht der Vermieter dieses Zimmers!", "Fehler", JOptionPane.ERROR_MESSAGE);
                } else {
                    RoomComboFrame.getRoomComboFrame(r);
                }
            }
        });

        //Zimmer hinzufügen
        JButton addNewRoom = new JButton("Neues Zimmer anlegen");
        addNewRoom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                RoomComboFrame.getRoomComboFrame();
            }
        });

        roleOptions.add(onlyMyRooms);
        roleOptions.add(showAllRooms);
        roleOptions.add(changeRoomProperties);
        roleOptions.add(addNewRoom);
    }

    /**
     * Erstellt die für den Mitarbeiter relevanten Optionen
     * Ausgelagert, um die Fkt in Vermieter und Mitarbeiter besser einsetzen zu können
     */
    private void createEmployeeOptions() {
        JButton newUser = new JButton("Neuen Benutzer anlegen");
        newUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewUserFrame.getNewUserFrame();
            }
        });


        JButton assignRoom = new JButton("Ausgewähltes Zimmer zuweisen");
        assignRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<User> noRooms = new ArrayList<User>();
                for(User u : DataHandler.getUsersByRole("Student")) {
                    if(((Student)u).getMyRoomId() == -1) {
                        noRooms.add(u);
                    }
                }

                UserListFrame.getUserListFrame(noRooms, getMainFrame().getMainFrame().getSelectedRoom());
            }
        });

        JButton showHomelessStudents = new JButton("Studenten ohne Zimmer zeigen");
        showHomelessStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<User> noRooms = new ArrayList<User>();
                for(User u : DataHandler.getUsersByRole("Student")) {
                    if(((Student)u).getMyRoomId() == -1) {
                        noRooms.add(u);
                    }
                }

                UserListFrame.getUserListFrame(noRooms);
            }
        });

        JButton showOnlyNewRooms = new JButton("Änderungen seit letztem Login");
        showOnlyNewRooms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(getMainFrame(), "Dieses Feature ist nur in der Professional - Version erhältlich!", "Lizenzeinschränkung", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        roleOptions.add(newUser);
        roleOptions.add(assignRoom);
        roleOptions.add(showHomelessStudents);
        roleOptions.add(showOnlyNewRooms);
    }
}
