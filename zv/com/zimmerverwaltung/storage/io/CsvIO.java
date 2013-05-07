package com.zimmerverwaltung.storage.io;

import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.storage.handler.ZvwDataType;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 29.04.13
 * Time: 09:01
 * To change this template use File | Settings | File Templates.
 */

/**
 * Backend - Klasse für die CSV - Dateien
 */
public class CsvIO {
    /**
     * List den Inhalt einer CSV - Datei in eine ArrayList
     * @param fname Pfad zu der Datei
     * @return Eine ArrayList, welche die CSV - Datei zeilenweise enthält
     */
    public static ArrayList<String> readRelevantLines(String fname) {
        try {
            BufferedReader inr = new BufferedReader(new InputStreamReader(new FileInputStream(fname), "ISO-8859-1"));
            String line = "";
            ArrayList<String> ret = new ArrayList<String>();
            int i = 0;
            while((line = inr.readLine()) != null) {
                //Erste Zeile enthält für das Programm irrelevante Spaltennamen
                if(i == 0) {
                    i++;
                    continue;
                }
                ret.add(line);
                i++;
            }

            return ret;
        } catch(IOException io) {
            return null;
        }
    }

    /**
     * Lädt alle CSV - Daten und parst sie in die Objektlisten
     */
    public static void loadAllCsvData() {
        DataHandler.initContainers();

        ArrayList<String> vergZimmer = readRelevantLines("zimmer_verg_zimmer.csv");
        ArrayList<String> vergLogin  = readRelevantLines("zimmer_verg_login.csv");

        for(String vergZimmerElement : vergZimmer) {
            DataHandler.addCsvEntry(vergZimmerElement, ZvwDataType.EDT_ROOM);
        }

        for(String vergLoginElement : vergLogin) {
            DataHandler.addCsvEntry(vergLoginElement, ZvwDataType.EDT_USER);
        }
    }

    /**
     * Liefert die Spaltennamen für die JTable auf der Hauptoberfläche
     * @param type Datentyp, User oder Room
     * @return Die Überschriften als String - Array
     */
    public static String[] getColumnNames(ZvwDataType type) {
        switch(type) {
            case EDT_ROOM: {
                try {
                    BufferedReader inr = new BufferedReader(new InputStreamReader(new FileInputStream("zimmer_verg_zimmer.csv"), "ISO-8859-1"));
                    String line = inr.readLine();
                    String[] head = line.split(";");
                    return head;
                } catch (FileNotFoundException e) { return null; }
                  catch (IOException e)           { return null; }
            }
            case EDT_USER: {
                try {
                    BufferedReader inr = new BufferedReader(new InputStreamReader(new FileInputStream("zimmer_verg_login.csv"), "ISO-8859-1"));
                    String line = inr.readLine();
                    String[] head = line.split(";");
                    return head;
                } catch (FileNotFoundException e) { return null; }
                catch (IOException e)             { return null; }
            }
            default: {
                return null;
            }
        }
    }
}
