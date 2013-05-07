package com.zimmerverwaltung.ui.custom;

import javax.swing.table.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 07.05.13
 * Time: 09:56
 * To change this template use File | Settings | File Templates.
 */
public class CustomTableModel extends DefaultTableModel {
    public CustomTableModel(String[][] data, String[] header) {
        super(data, header);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
