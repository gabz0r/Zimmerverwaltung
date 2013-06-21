package com.zimmerverwaltung.storage.io.database;

import java.sql.*;

/**
 * Stellt Methoden für Datenbankhandler bereit
 */
public interface IGeneralDbHandler {
    public void setup(String connectionString);
    public ResultSet executeSQL(String qry);
    public void executeDML(String dml);
}
