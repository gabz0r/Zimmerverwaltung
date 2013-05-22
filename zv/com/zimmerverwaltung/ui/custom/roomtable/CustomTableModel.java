package com.zimmerverwaltung.ui.custom.roomtable;

import javax.swing.table.*;
import java.awt.*;

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
        this.addColumn("Gemerkt");

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
