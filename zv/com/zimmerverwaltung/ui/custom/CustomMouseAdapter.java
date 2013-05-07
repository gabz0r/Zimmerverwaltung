package com.zimmerverwaltung.ui.custom;

import com.zimmerverwaltung.storage.container.*;
import com.zimmerverwaltung.storage.handler.*;
import com.zimmerverwaltung.users.extended.*;

import javax.swing.*;
import java.awt.event.*;

import static com.zimmerverwaltung.ui.MainFrame.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 07.05.13
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
public class CustomMouseAdapter extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2) {
            if(getMainFrame().getCurrentUser() instanceof Student) {
                String[] searchTokens = new String [] {
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 0).toString(), //Beschreibung
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 1).toString(), //Vermieter
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 2).toString(), //Straße
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 3).toString(), //Ort
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 4).toString(), //Preis
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 5).toString(), //Entfernung
                        ((JTable)e.getSource()).getValueAt(((JTable)e.getSource()).getSelectedRow(), 6).toString().split(" ")[0]  //qm
                };

                Room searchedRoom = DataHandler.getRoomByRowData(searchTokens);

                if(!((Student)getMainFrame().getCurrentUser()).watchesRoom(searchedRoom) &&
                   JOptionPane.showConfirmDialog(getMainFrame(), "Zimmer merken?", "Zimmerverwaltung", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    ((Student)getMainFrame().getCurrentUser()).rememberRoom(searchedRoom);
                }
                else if(((Student)getMainFrame().getCurrentUser()).watchesRoom(searchedRoom) &&
                        JOptionPane.showConfirmDialog(getMainFrame(), "Zimmer nicht mehr merken?", "Zimmerverwaltung", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    ((Student)getMainFrame().getCurrentUser()).forgetRoom(searchedRoom);
                }

            }

        }
    }
}
