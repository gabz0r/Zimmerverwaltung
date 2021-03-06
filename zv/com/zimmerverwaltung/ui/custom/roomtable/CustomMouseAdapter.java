package com.zimmerverwaltung.ui.custom.roomtable;

import com.zimmerverwaltung.storage.container.*;
import com.zimmerverwaltung.storage.handler.*;
import com.zimmerverwaltung.ui.dispatcher.*;
import com.zimmerverwaltung.users.extended.*;

import javax.swing.*;
import java.awt.event.*;

import static com.zimmerverwaltung.ui.MainFrame.*;

/**
 * Maushandler für die Datentabelle
 * Unterscheidet Einzel / Doppelklick auf und handelt dementsprechend
 * Einfachklick: Dispatcher wird mit Ziel EET_INFOPANEL aufgerufen, Parameter ist der selektierte Raum -> update am Infopanel (Details anzeigen)
 * Doppelklick: Dispatcher wird mit Ziel EET_DATATABLE aufgerufen, Parameter ist der selektierte Raum -> update am Datatable (Raum merken)
 */
public class CustomMouseAdapter extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {

        String[] tokens =  new String[] {
                ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 0).toString(), //Beschreibung
                ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 1).toString(), //Vermieter
                ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 2).toString(), //Straße
                ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 3).toString(), //Ort
                ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 4).toString(), //Preis
                ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 5).toString(), //Entfernung
                ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 6).toString().split(" ")[0], //qm
                ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 7).toString(),
                ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 8).toString(), //id
        };
        Room room = DataHandler.getRoomByRowData(tokens);
        getMainFrame().setSelectedRoom(room);

        EventDispatcher.getInstance().dispatch(EventTargets.EET_INFOPANEL, room);

        if(e.getClickCount() == 2) {
            if(getMainFrame().getCurrentUser() instanceof Student && ((Student) getMainFrame().getCurrentUser()).getWatchList().size() >= 4) {
                JOptionPane.showMessageDialog(getMainFrame(), "Es können nicht mehr als vier Zimmer beobachtet werden!", "Zimmerverwaltung", JOptionPane.OK_OPTION);
                return;
            }
            if(getMainFrame().getCurrentUser() instanceof Student) {
                String[] searchTokens = new String [] {
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 0).toString(), //Beschreibung
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 1).toString(), //Vermieter
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 2).toString(), //Straße
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 3).toString(), //Ort
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 4).toString(), //Preis
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 5).toString(), //Entfernung
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 6).toString().split(" ")[0], //qm
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 7).toString(),
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 8).toString(), //id
                };

                Room searchedRoom = DataHandler.getRoomByRowData(searchTokens);

                if(!((Student)getMainFrame().getCurrentUser()).remembersRoom(searchedRoom) &&
                   JOptionPane.showConfirmDialog(getMainFrame(), "Zimmer merken?", "Zimmerverwaltung", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    ((Student)getMainFrame().getCurrentUser()).rememberRoom(searchedRoom);
                }
                else if(((Student)getMainFrame().getCurrentUser()).remembersRoom(searchedRoom) &&
                        JOptionPane.showConfirmDialog(getMainFrame(), "Zimmer nicht mehr merken?", "Zimmerverwaltung", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    ((Student)getMainFrame().getCurrentUser()).forgetRoom(searchedRoom);
                }

                EventDispatcher.getInstance().dispatch(EventTargets.EET_DATATABLE, room);

            }

        }
    }
}
