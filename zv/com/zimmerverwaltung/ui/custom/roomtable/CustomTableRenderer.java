package com.zimmerverwaltung.ui.custom.roomtable;

import com.zimmerverwaltung.storage.handler.*;
import com.zimmerverwaltung.ui.*;
import com.zimmerverwaltung.users.extended.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 13.05.13
 * Time: 09:34
 * To change this template use File | Settings | File Templates.
 */
public class CustomTableRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);

        String[] rowData = null;

        try {

            rowData = new String[] {
                    table.getValueAt(table.getSelectedRow(), 0).toString(),
                    table.getValueAt(table.getSelectedRow(), 1).toString(),
                    table.getValueAt(table.getSelectedRow(), 2).toString(),
                    table.getValueAt(table.getSelectedRow(), 3).toString(),
                    table.getValueAt(table.getSelectedRow(), 4).toString(),
                    table.getValueAt(table.getSelectedRow(), 5).toString(),
                    table.getValueAt(table.getSelectedRow(), 6).toString(),
                    table.getValueAt(table.getSelectedRow(), 7).toString().split(" ")[0],
            };

        } catch(ArrayIndexOutOfBoundsException e) {
            return c;
        }

        if(MainFrame.getMainFrame().getCurrentUser() instanceof Student) {
            /*if(((Student)MainFrame.getMainFrame().getCurrentUser()).remembersRoom(DataHandler.getRoomByRowData(rowData))
               && table.getValueAt((int)c.getLocation().getX(), 0).equals(rowData[0])) {
                c.setBackground(Color.GREEN);
            }
            else {
                c.setBackground(Color.WHITE);
            }                */
        }

        return this;
    }
}
