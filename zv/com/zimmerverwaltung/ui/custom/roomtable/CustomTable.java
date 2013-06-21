package com.zimmerverwaltung.ui.custom.roomtable;

import javax.swing.*;
import javax.swing.table.*;

/**
 * Eigene Datentabellenklasse, enthält leicht erweiterte Funktionalität
 */
public class CustomTable extends JTable {
    /**
     * Konstruktor ruft super auf und formatiert Spalten
     * @param model Das Datenmodell, welches der Tabelle zugrundeliegt
     */
    public CustomTable(TableModel model) {
        super(model);

        this.getColumn("Gemerkt").setMaxWidth(60);

        getTableHeader().setReorderingAllowed(false);
    }

    /**
     * Versteckt die spezifizierten Spalten
     * @param columns Die Spaltennamen in einem Array
     */
    public void hideColumns(String[] columns) {
        for(String c : columns) {
            this.getColumn(c).setWidth(0);
            this.getColumn(c).setMinWidth(0);
            this.getColumn(c).setMaxWidth(0);
        }
    }
}
