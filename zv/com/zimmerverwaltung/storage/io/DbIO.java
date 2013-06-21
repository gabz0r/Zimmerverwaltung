package com.zimmerverwaltung.storage.io;

import com.zimmerverwaltung.storage.io.database.MySQLHandler;

import java.sql.*;
import java.util.*;

/**
 * Abstrahierte backend - Klasse für den Datenaustausch über eine Datenbank
 */
public class DbIO {
    private static MySQLHandler mysql = new MySQLHandler();

    /**
     * Liest Daten aus der Datenbank und gibt sie als ArrayList zurück
     * @return Das Zeilenarray aus der Datei
     */
    public static ArrayList<String> readAllLines() {
        ResultSet rs = mysql.executeSQL("noch nicht implementiert");

        return null;
    }
}
