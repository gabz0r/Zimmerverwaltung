package com.zimmerverwaltung.storage.io;

import com.zimmerverwaltung.storage.container.Room;
import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.storage.handler.ZvwDataType;
import com.zimmerverwaltung.users.User;
import com.zimmerverwaltung.users.extended.Student;

import java.io.*;
import java.util.*;

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
            BufferedReader inr = new BufferedReader(new InputStreamReader(new FileInputStream(fname), "ISO-8859-15"));
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
                    BufferedReader inr = new BufferedReader(new InputStreamReader(new FileInputStream("zimmer_verg_zimmer.csv"), "ISO-8859-15"));
                    String line = inr.readLine();
                    String[] head = line.split(";");

                    return head;
                } catch (FileNotFoundException e) { return null; }
                  catch (IOException e)           { return null; }
            }
            case EDT_USER: {
                try {
                    BufferedReader inr = new BufferedReader(new InputStreamReader(new FileInputStream("zimmer_verg_login.csv"), "ISO-8859-15"));
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

    /**
     * Schreibt die IDs der gemerkten Räume in die CSV - Datei
     * @param u Benutzer, an den die Zimmer angehängt werden sollen
     * @param rs Die Liste der gemerkten Zimmer
     * @return true / false, je nach dem Rollback
     */
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
            PrintWriter outw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("zimmer_verg_login.csv"), "ISO-8859-15"));
            String[] headerArray = CsvIO.getColumnNames(ZvwDataType.EDT_USER);
            String header = "";

            for(int i = 0; i < headerArray.length; i++) {
                if(i == 0) {
                    header += headerArray[i];
                } else {
                    header += ";" + headerArray[i];
                }
            }

            outw.println(header);

            for(String l : newLines) {
                outw.println(l);
            }

            outw.flush();
            outw.close();

            return true;
        } catch (FileNotFoundException e) { return false; }
          catch (IOException e)             { return false; }
    }

    /**
     * Wird generell dazu verwendet, eine gewisse Eigenschaft eines Benutzers in der Datei zu ändern
     * @param u Benutzer, dessen Eigenschaften geändert werden
     * @param up ID der Eigenschaft, die geändert werden soll
     * @see UserFileProperty
     * @return true / false, je nach dem Rollback
     */
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
            PrintWriter outw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("zimmer_verg_login.csv"), "ISO-8859-15"));

            for(String l : newLines) {
                outw.println(l);
            }

            outw.flush();
            outw.close();

            return true;
        } catch (FileNotFoundException e) { return false; }
          catch (IOException e)           { return false; }

    }

    /**
     * Löscht die Zimmerdatei und legt sie neu an
     * Dabei werden die im Programm geänderten Eigenschaften übernommen
     */
    public static void updateRoomFile() {
        ArrayList<Room> rl = DataHandler.getRooms();

        try {
            String[] header = CsvIO.getColumnNames(ZvwDataType.EDT_ROOM);
            new File("zimmer_verg_zimmer.csv").delete();
            PrintWriter outw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("zimmer_verg_zimmer.csv"), "ISO-8859-15"));

            for(int i = 0; i < header.length; i++) {
                if(i == 0) {
                    outw.print(header[i]);
                }
                else {
                    outw.print(";" + header[i]);
                }
            }

            outw.println();

            for(Room r : rl) {
                outw.println(r.getDescription() + ";" + r.getLandlord() + ";" +
                             r.getStreet() + ";" + r.getLocation() + ";" +
                             r.getFees() + ";" + r.getDistance() + ";" +
                             r.getQm() + ";" + r.getImgPath() + ";" + r.getId());
            }

            outw.flush();
            outw.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    /**
     * Löscht die Benutzerdatei und legt sie neu an
     * Dabei werden die im Programm geänderten Eigenschaften übernommen
     */
    public static void updateUserFile() {
        ArrayList<User> ul = DataHandler.getUsers();

        try {
            String[] header = CsvIO.getColumnNames(ZvwDataType.EDT_USER);
            new File("zimmer_verg_login.csv").delete();
            PrintWriter outw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("zimmer_verg_login.csv"), "ISO-8859-15"));

            for(int i = 0; i < header.length; i++) {
                if(i == 0) {
                    outw.print(header[i]);
                }
                else {
                    outw.print(";" + header[i]);
                }
            }

            outw.println();

            for(User u : ul) {
                String assignedRoomValue = "";
                if(u instanceof Student) {
                    assignedRoomValue = ";" + String.valueOf(((Student)u).getMyRoomId());
                }

                outw.println(u.getLastName() + ";" + u.getFirstName() + ";" +
                        u.getRoleName() + ";" + u.getUserName() + ";" +
                        u.getPassword() + assignedRoomValue);
            }

            outw.flush();
            outw.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
