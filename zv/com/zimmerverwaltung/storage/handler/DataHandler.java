package com.zimmerverwaltung.storage.handler;

import com.zimmerverwaltung.storage.container.*;
import com.zimmerverwaltung.users.*;
import com.zimmerverwaltung.users.extended.Landlord;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Gabriel
 * @version 0.1
 * Verwaltet alle Daten, die aus den CSV - Dateien kommen
 */
public class DataHandler {
    //Datencontainer
    private static ArrayList<Room> rooms;
    private static ArrayList<User> users;

    public static void initContainers() {
        rooms = new ArrayList<Room>();
        users = new ArrayList<User>();
    }

    /**
     * Fügt einen Eintrag in die Liste hinzu
     * @param line Zeile im CSV - Format
     * @param type Typ nach enum ZvwDataType
     */
    public static void addCsvEntry(String line, ZvwDataType type) {
        String[] keys = line.split(";");
        switch(type) {
            case EDT_ROOM: {
                if(keys.length < 8) {
                    rooms.add(new Room(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], Float.parseFloat(keys[6]), ""));
                }
                else {
                    rooms.add(new Room(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], Float.parseFloat(keys[6]), keys[7]));
                }

                break;
            }
            case EDT_USER: {
                users.add(User.mapRole(line));
                break;
            }
        }
    }

    /**
     * Fügt ein ResultSet einer Datenbankabfrage der Liste hinzu
     * @param rss Das ResultSet der Abfrage
     * @param type Der Typ der Daten
     */
    public static void addFromDb(ResultSet rss, ZvwDataType type) {
        try {
            while(rss.next()) {
                switch(type) {
                    case EDT_ROOM: {
                        String description = rss.getString("DESCRIPTION");
                        String landlord = rss.getString("LANDLORD");
                        String street = rss.getString("STREET");
                        String location = rss.getString("LOCATION");
                        String fees = rss.getString("FEES");
                        String distance = rss.getString("DISTANCE");
                        Float qm = rss.getFloat("QM");
                        String imgPath = rss.getString("IMG_PATH");

                        rooms.add(new Room(description, landlord, street, location, fees, distance, qm, imgPath));
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * Suchfunktion nach einen bestimmten Attribut
     * @param token Suchattribut
     * @param keyType Typ des Suchattributs
     * @return Alle passenden Ergebnisse
     */
    public static Room[] searchRoom(String token, SearchType keyType) {
        Room[] results = null;

        switch(keyType) {
            case EST_DISTANCE: {
                break;
            }
            case EST_FEES: {
                break;
            }
            case EST_LOCATION: {
                break;
            }
            case EST_QM: {
                break;
            }
            case EST_STREET: {
                break;
            }
            default: {
                break;
            }
        }
        return results;
    }

    public static Room getRoomByRowData(String[] data) {
        for(Room r : rooms) {
            if(r.getDescription().equals(data[0]) &&
               r.getLandlord().equals(data[1]) &&
               r.getStreet().equals(data[2]) &&
               r.getLocation().equals(data[3]) &&
               r.getFees().equals(data[4]) &&
               r.getDistance().equals(data[5]) &&
               r.getQm() == Float.parseFloat(data[6])) {

                return r;
            }
        }

        return null;
    }

    /**
     * Überprüft, ob der Benutzer in den Daten geführt wird
     * @param username Zu überprüfender Name
     * @param password Zum User gehörendes Passwort
     * @return True - Der User ist gültig, False - Der User ist ungültig
     */
    public static boolean isValidUser(String username, String password) {
        for(User u : users) {
            if(u.getUserName().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Holt auf Basis der übergebenen Daten einen Benutzer aus dem internen Container und gibt diesen zurück
     * @param username Benutername
     * @param password Passwort
     * @return Das Benutzerobjekt, falls der Benutzer vorhanden ist, andernfalls null
     */
    public static User getCorrespondingUserObject(String username, String password) {
        for(User u : users) {
            if(u.getUserName().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Speichert alle Daten, die in den Containern aufgenommen wurden
     */
    public static void writeAllData() {

    }

    public static String[][] mapToStringArray(ZvwDataType type) {
        switch(type) {
            case EDT_USER: {
                String[][] usersRet = new String[users.size()][4];
                for(int i = 0; i < users.size(); i++) {
                    usersRet[i][0] = users.get(i).getFirstName();
                    usersRet[i][1] = users.get(i).getLastName();
                    usersRet[i][2] = users.get(i).getUserName();
                    usersRet[i][3] = users.get(i).getPassword();
                }
                return usersRet;
            }
            case EDT_ROOM: {
                String[][] roomsRet = new String[rooms.size()][8];
                for(int i = 0; i < rooms.size(); i++) {
                    roomsRet[i][0] = rooms.get(i).getDescription();
                    roomsRet[i][1] = rooms.get(i).getLandlord();
                    roomsRet[i][2] = rooms.get(i).getStreet();
                    roomsRet[i][3] = rooms.get(i).getLocation();
                    roomsRet[i][4] = rooms.get(i).getFees();
                    roomsRet[i][5] = rooms.get(i).getDistance();
                    roomsRet[i][6] = String.valueOf(rooms.get(i).getQm()) + " m²";
                    roomsRet[i][7] = rooms.get(i).getImgPath();
                }
                return roomsRet;
            }
            default: {
                return null;
            }
        }
    }
}
