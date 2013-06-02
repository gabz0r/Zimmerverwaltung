package com.zimmerverwaltung.ui.custom.roomtable;

import com.zimmerverwaltung.storage.container.Room;
import com.zimmerverwaltung.ui.MainFrame;
import com.zimmerverwaltung.ui.dispatcher.DispatcherObject;
import com.zimmerverwaltung.ui.dispatcher.EventDispatcher;
import com.zimmerverwaltung.ui.dispatcher.EventTargets;
import com.zimmerverwaltung.users.extended.Student;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 07.05.13
 * Time: 09:56
 * To change this template use File | Settings | File Templates.
 */
public class CustomTableModel extends DefaultTableModel  implements Observer {

    public CustomTableModel(String[][] data, String[] header) {
        super(data, header);
        this.addColumn("Gemerkt");

        EventDispatcher.getInstance().registerObserver(this);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof EventDispatcher) {
            DispatcherObject disp = (DispatcherObject)arg;
            if(disp.getTarget() == EventTargets.EET_DATATABLE) {
                Room selectedRoom = (Room)disp.getContent();

                for(int i = 0; i < this.getRowCount(); i++) {
                    if(getValueAt(i, 0).toString().equals(String.valueOf(selectedRoom.getId()))) {
                        if(((Student)MainFrame.getMainFrame().getCurrentUser()).remembersRoom(selectedRoom)) {
                            setValueAt("Ja", i, getColumnCount() - 1);
                        }
                        else {
                            setValueAt("", i, getColumnCount() - 1);
                        }
                    }
                }
            }
        }
    }
}
