package com.zimmerverwaltung.ui;

import com.zimmerverwaltung.storage.container.Room;
import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.ui.util.CustomFrame;
import com.zimmerverwaltung.ui.util.SpringUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.File;

/**
 * Combofenster zum Erstellen und Bearbeiten von Zimmern
 */
public class RoomComboFrame extends CustomFrame {

    private JPanel rootPanel;

    private JLabel descriptionLabel;
    private JLabel locationLabel;
    private JLabel streetLabel;
    private JLabel feesLabel;
    private JLabel distanceLabel;
    private JLabel qmLabel;
    private JLabel imageNameLabel;

    private JTextField description;
    private JTextField location;
    private JTextField street;
    private JTextField fees;
    private JTextField distance;
    private JTextField qm;
    private JTextField imageName;

    private JButton apply;

    private Room toChange;

    private RoomComboFrame() {
        setTitle("Zimmer erstellen");
        initUI(false);
    }

    private RoomComboFrame(Room toChange) {
        this.toChange = toChange;
        setTitle("Zimmer bearbeiten");
        initUI(true);
    }

    /**
     * Initialisiert die Benutzeroberfläche mit den Elementen
     * @param displayRoom Den aktuell ausgewählten Raum in die Felder übernehmen -> um den Raum zu ändern
     */
    private void initUI(boolean displayRoom) {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        if(displayRoom) {
            rootPanel = new JPanel(new SpringLayout());

            descriptionLabel = new JLabel("Beschreibung");
            description = new JTextField(toChange.getDescription());
            rootPanel.add(descriptionLabel);
            rootPanel.add(description);

            locationLabel = new JLabel("Ort");
            location = new JTextField(toChange.getLocation());
            rootPanel.add(locationLabel);
            rootPanel.add(location);

            streetLabel = new JLabel("Straße");
            street = new JTextField(toChange.getStreet());
            rootPanel.add(streetLabel);
            rootPanel.add(street);

            feesLabel = new JLabel("Miete");
            fees = new JTextField(toChange.getFees());
            rootPanel.add(feesLabel);
            rootPanel.add(fees);

            distanceLabel = new JLabel("Entfernung");
            distance = new JTextField(toChange.getDistance());
            rootPanel.add(distanceLabel);
            rootPanel.add(distance);

            qmLabel = new JLabel("Qm");
            qm = new JTextField(String.valueOf(toChange.getQm()).replace(".", ","));
            rootPanel.add(qmLabel);
            rootPanel.add(qm);

            imageNameLabel = new JLabel("Bildname");
            rootPanel.add(imageNameLabel);

            String[] imgPath = toChange.getImgPath().split("/");

            imageName = new JTextField(imgPath.length > 1 ? imgPath[1] : imgPath[0]);
            imageName.setEditable(false);
            imageName.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JFileChooser ch = new JFileChooser("pics");

                    FileFilter ff = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
                    ch.addChoosableFileFilter(ff);
                    ch.setFileFilter(ff);
                    ch.setAcceptAllFileFilterUsed(false);

                    if(ch.showOpenDialog(getRoomComboFrame()) == JFileChooser.APPROVE_OPTION) {
                        File f = ch.getSelectedFile();
                        imageName.setText(f.getName());
                    }
                }
            });
            rootPanel.add(imageName);

            apply = new JButton("OK");
            apply.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Raum zuerst löschen
                    if(MainFrame.getMainFrame().getGrid().getSelectedRow() != -1) {
                        ((DefaultTableModel) MainFrame.getMainFrame().getGrid().getModel()).removeRow(MainFrame.getMainFrame().getGrid().getSelectedRow());

                        DataHandler.removeRoom(toChange);

                        //Dann ändern
                        toChange.setDescription(description.getText());
                        toChange.setLocation(location.getText());
                        toChange.setStreet(street.getText());
                        toChange.setFees(fees.getText());
                        toChange.setDistance(distance.getText());
                        toChange.setQm(Float.valueOf(qm.getText().replace(",", ".")));
                        toChange.setImgPath(!imageName.getText().equals("") ? "pics/" + imageName.getText() : "");

                        //Und wieder einfügen
                        ((DefaultTableModel) MainFrame.getMainFrame().getGrid().getModel()).addRow(
                                new Object[] {
                                        toChange.getDescription(),
                                        toChange.getLandlord(),
                                        toChange.getStreet(),
                                        toChange.getLocation(),
                                        toChange.getFees(),
                                        toChange.getDistance(),
                                        (toChange.getQm() + " m²").replace(".", ","),
                                        toChange.getImgPath(),
                                        toChange.getId()
                        });
                        DataHandler.addRoom(toChange);

                        close();
                    } else {
                        close();
                    }
                }
            });

        } else {
            rootPanel = new JPanel(new SpringLayout());

            descriptionLabel = new JLabel("Beschreibung");
            description = new JTextField();
            rootPanel.add(descriptionLabel);
            rootPanel.add(description);

            locationLabel = new JLabel("Ort");
            location = new JTextField();
            rootPanel.add(locationLabel);
            rootPanel.add(location);

            streetLabel = new JLabel("Straße");
            street = new JTextField();
            rootPanel.add(streetLabel);
            rootPanel.add(street);

            feesLabel = new JLabel("Miete");
            fees = new JTextField();
            rootPanel.add(feesLabel);
            rootPanel.add(fees);

            distanceLabel = new JLabel("Entfernung");
            distance = new JTextField();
            rootPanel.add(distanceLabel);
            rootPanel.add(distance);

            qmLabel = new JLabel("Qm");
            qm = new JTextField();
            rootPanel.add(qmLabel);
            rootPanel.add(qm);

            imageNameLabel = new JLabel("Bildname");
            rootPanel.add(imageNameLabel);

            imageName = new JTextField();
            imageName.setEditable(false);
            imageName.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JFileChooser ch = new JFileChooser("pics");

                    FileFilter ff = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
                    ch.addChoosableFileFilter(ff);
                    ch.setFileFilter(ff);
                    ch.setAcceptAllFileFilterUsed(false);

                    if (ch.showOpenDialog(getRoomComboFrame()) == JFileChooser.APPROVE_OPTION) {
                        File f = ch.getSelectedFile();
                        imageName.setText(f.getName());
                    }
                }
            });
            rootPanel.add(imageName);

            apply = new JButton("OK");

            apply.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Wie oben, nur keinen Raum löschen
                    toChange = new Room(description.getText(),
                            MainFrame.getMainFrame().getCurrentUser().getUserName(),
                            street.getText(),
                            location.getText(),
                            fees.getText(),
                            distance.getText(),
                            Float.valueOf(qm.getText().replace(",", ".")),
                            (!imageName.getText().equals("") ? "pics/" + imageName.getText() : ""),
                            DataHandler.nextRoomID());


                    //Und wieder einfügen
                    ((DefaultTableModel) MainFrame.getMainFrame().getGrid().getModel()).addRow(
                            new Object[] {
                                    toChange.getDescription(),
                                    toChange.getLandlord(),
                                    toChange.getStreet(),
                                    toChange.getLocation(),
                                    toChange.getFees(),
                                    toChange.getDistance(),
                                    (toChange.getQm() + " m²").replace(".", ","),
                                    toChange.getImgPath(),
                                    toChange.getId()
                            });
                    DataHandler.addRoom(toChange);

                    close();
                }
            });
        }

        JButton dummy = new JButton();
        dummy.setVisible(false);
        rootPanel.add(dummy);

        rootPanel.add(apply);

        SpringUtilities.makeCompactGrid(rootPanel, 8, 2, 10, 10, 10, 10);
        getContentPane().add(rootPanel);
    }

    @Override
    public void close() {
        instance = null;
        this.setVisible(false);
        this.dispose();
    }

    private static RoomComboFrame instance;
    public static RoomComboFrame getRoomComboFrame() {
        if(instance == null) {
            instance = new RoomComboFrame();
        }
        instance.setBounds(200, 200, 300, 350);
        instance.setResizable(false);
        instance.setVisible(true);
        return instance;
    }

    public static RoomComboFrame getRoomComboFrame(Room r) {
        if(instance == null) {
            instance = new RoomComboFrame(r);
        }
        instance.setBounds(200, 200, 300, 350);
        instance.setResizable(false);
        instance.setVisible(true);
        return instance;
    }
}
