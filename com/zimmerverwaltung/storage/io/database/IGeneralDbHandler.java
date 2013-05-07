package com.zimmerverwaltung.storage.io.database;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 29.04.13
 * Time: 09:34
 * To change this template use File | Settings | File Templates.
 */

/**
 * Stellt Methoden für Datenbankhandler bereit
 */
public interface IGeneralDbHandler {
    public void setup(String connectionString);
    public ResultSet executeSQL(String qry);
    public void executeDML(String dml);
}
