package com.zimmerverwaltung.storage.io.database;

import java.sql.ResultSet;

/**
 * Implementiert das Interface IGeneralDbHandler für die SQLite - Datenbank
 */
public class SQLiteHandler implements IGeneralDbHandler {
    @Override
    public void setup(String connectionString) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultSet executeSQL(String qry) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void executeDML(String dml) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
