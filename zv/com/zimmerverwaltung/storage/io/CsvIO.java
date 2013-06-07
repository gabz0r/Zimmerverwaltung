package com.zimmerverwaltung.storage.io;

import com.zimmerverwaltung.storage.container.Room;
import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.storage.handler.ZvwDataType;
import com.zimmerverwaltung.users.User;

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

                    String[] ret = new String[head.length + 1];
                    System.arraycopy(head,0,ret, 1, head.length);
                    ret[0] = "id";

                    return ret;
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

    public static boolean appendRoomsToUser(User u, ArrayList<Room> rs) {
        try {
            ArrayList<String> allLines = readRelevantLines("zimmer_verg_login.csv");
            ArrayList<String> newLines = new ArrayList<String>();

            for(String l : allLines) {
                if(l.split(";")[3].equals(u.getUserName())) {
                    l = l.split(";")[0] + ";" + l.split(";")[1] + ";" + l.split(";")[2] + ";" + l.split(";")[3] + ";" + l.split(";")[4];

                    for(Room r : rs) {
                            if(l.split(";").length <= 5) {
                                l += ";" + r.getId();
                            }
                            else {
                                l += "-" + r.getId();
                            }
                        }
                    }

                newLines.add(l);
            }

            new File("zimmer_verg_login.csv").delete();
            PrintWriter outw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("zimmer_verg_login.csv"), "ISO-8859-1"));

            for(String l : newLines) {
                outw.println(l);
            }

            outw.flush();
            outw.close();

            return true;
        } catch (FileNotFoundException e) { return false; }
          catch (IOException e)             { return false; }
    }

    public static boolean updateUserDataFile(User u, UserFileProperty up) {

        ArrayList<String> allLines = readRelevantLines("zimmer_verg_login.csv");
        ArrayList<String> newLines = new ArrayList<String>();



        switch(up) {
            case EUP_PASSWORD: {
                for(String l : allLines) {
                    if(l.split(";")[3].equals(u.getUserName())) {
                        l = l.split(";")[0] + ";" + l.split(";")[1] + ";" + l.split(";")[2] + ";" + l.split(";")[3] + ";" + u.getPassword();
                    }

                    newLines.add(l);
                }
                break;
            }
        }

        try {
            new File("zimmer_verg_login.csv").delete();
            PrintWriter outw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("zimmer_verg_login.csv"), "ISO-8859-1"));

            for(String l : newLines) {
                outw.println(l);
            }

            outw.flush();
            outw.close();

            return true;
        } catch (FileNotFoundException e) { return false; }
          catch (IOException e)           { return false; }

    }

}
