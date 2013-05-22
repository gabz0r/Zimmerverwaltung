package com.zimmerverwaltung.ui.custom.roomtable;

import javax.swing.*;
import javax.swing.table.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 13.05.13
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
public class CustomTable extends JTable {
    public CustomTable(AbstractTableModel model) {
        super(model);

        this.getColumn("Gemerkt").setWidth(20);

        getTableHeader().setReorderingAllowed(false);
    }

    public void hideColumns(String[] columns) {
        for(String c : columns) {
            this.getColumn(c).setWidth(0);
            this.getColumn(c).setMinWidth(0);
            this.getColumn(c).setMaxWidth(0);
        }
    }
}
