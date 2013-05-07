package com.zimmerverwaltung.storage.io.database;

import java.sql.ResultSet;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 29.04.13
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */

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
