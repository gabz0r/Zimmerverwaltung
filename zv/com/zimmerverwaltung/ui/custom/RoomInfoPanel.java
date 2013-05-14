package com.zimmerverwaltung.ui.custom;

import com.zimmerverwaltung.storage.container.*;
import com.zimmerverwaltung.ui.dispatcher.*;
import com.zimmerverwaltung.users.extended.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static com.zimmerverwaltung.ui.MainFrame.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 13.05.13
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */
public class RoomInfoPanel extends JPanel implements Observer {
    JLabel description;
    JLabel landlord;
    JLabel street;
    JLabel location;
    JLabel fees;
    JLabel distance;
    JLabel qm;
    JLabel remembered;

    public RoomInfoPanel() {
        super();
        EventDispatcher.getInstance().registerObserver(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        description = new JLabel("Beschreibung:"); description.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        landlord = new JLabel("Vermieter:"); landlord.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        street = new JLabel("Straße:"); street.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        location = new JLabel("Ort:"); location.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        fees = new JLabel("Mietkosten:"); fees.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        distance = new JLabel("Entfernung DH:"); distance.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        qm = new JLabel("Fläche:"); qm.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        remembered = new JLabel(""); remembered.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        remembered.setForeground(Color.RED);
        remembered.setFont(new Font(remembered.getFont().getName(), Font.BOLD, remembered.getFont().getSize() + 5));

        this.add(description);
        this.add(landlord);
        this.add(street);
        this.add(location);
        this.add(fees);
        this.add(distance);
        this.add(qm);
        this.add(remembered);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof EventDispatcher) {
            DispatcherObject disp = (DispatcherObject)arg;
            Room selectedRoom = (Room)disp.getContent();

            updateCurrentRoom(selectedRoom);
        }
    }

    /**
     * Aktualisiert das InfoPanel mit einem neuen Raum
     * @param r Der Raum, der im InfoPanel dargestellt werden soll
     */
    private void updateCurrentRoom(Room r) {
        description.setText("Beschreibung: " + r.getDescription());
        landlord.setText("Vermieter: " + r.getLandlord());
        street.setText("Straße: " + r.getStreet());
        location.setText("Ort: " + r.getLocation());
        fees.setText("Mietkosten: " + r.getFees());
        distance.setText("Entfernung DH: " + r.getDescription());
        qm.setText("Fläche: " + String.valueOf(r.getQm()) + " m²");

        if(getMainFrame().getCurrentUser() instanceof Student) {
            if(((Student)getMainFrame().getCurrentUser()).remembersRoom(r)) {
                remembered.setText("GEMERKT");
            }
            else {
                remembered.setText("");
            }
        }
        else {
            remembered.setText("");
        }
    }
}
