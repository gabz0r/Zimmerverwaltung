package com.zimmerverwaltung.storage.io;

import com.zimmerverwaltung.storage.io.database.MySQLHandler;

import java.sql.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 29.04.13
 * Time: 09:30
 * To change this template use File | Settings | File Templates.
 */

/**
 * Abstrahierte backend - Klasse für den Datenaustausch über eine Datenbank
 */
public class DbIO {
    private static MySQLHandler mysql = new MySQLHandler();

    /**
     * Liest Daten aus der Datenbank und gibt sie als ArrayList zurück
     * @return
     */
    public static ArrayList<String> readAllLines() {
        ResultSet rs = mysql.executeSQL("noch nicht implementiert");

        return null;
    }
}
