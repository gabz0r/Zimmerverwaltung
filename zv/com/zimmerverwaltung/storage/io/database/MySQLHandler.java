package com.zimmerverwaltung.storage.io.database;

import java.sql.ResultSet;

/**
 * Implementiert das Interface IGeneralDbHandler für die MySQL - Datenbank
 */
public class MySQLHandler implements IGeneralDbHandler {

    /**
     * Erfasst Daten für die Verbindung
     * @param connectionString Der ConnectionString für die Datenbank
     */
    @Override
    public void setup(String connectionString) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Führt eine Query auf die Datenbank aus. Die Verbindung wird für jede Query erneut aufgebaut und nach jeder Query geschlossen.
     * @param qry Der Query - String
     * @return Daten, welche die Datenbank nach der Query liefert
     */
    @Override
    public ResultSet executeSQL(String qry) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Führt eine DML - Operation auf die Datenbank aus. Die Verbindung wird für jede Query erneut aufgebaut und nach jeder Query geschlossen.
     * @param dml DML - Operation, die ausgeführt werden soll
     */
    @Override
    public void executeDML(String dml) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
