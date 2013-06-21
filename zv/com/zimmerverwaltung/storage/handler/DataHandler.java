package com.zimmerverwaltung.storage.handler;

import com.zimmerverwaltung.storage.container.*;
import com.zimmerverwaltung.storage.io.CsvIO;
import com.zimmerverwaltung.ui.MainFrame;
import com.zimmerverwaltung.users.*;
import com.zimmerverwaltung.users.extended.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Verwaltet alle Daten, die aus den CSV - Dateien kommen
 */
public class DataHandler {
    //Datencontainer
    private static ArrayList<Room> rooms;
    private static ArrayList<User> users;

    /**
     * Initialisiert die ArrayList für Zimmer und Benutzer
     */
    public static void initContainers() {
        rooms = new ArrayList<Room>();
        users = new ArrayList<User>();
    }

    /**
     * Fügt einen Eintrag in die Liste hinzu
     * @param line Zeile im CSV - Format
     * @param type Typ nach enum ZvwDataType
     * @see ZvwDataType
     */
    public static void addCsvEntry(String line, ZvwDataType type) {
        String[] keys = line.split(";");
        switch(type) {
            case EDT_ROOM: {
                if(keys[7].equals("")) {
                    rooms.add(new Room(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], Float.parseFloat(keys[6]), Integer.parseInt(keys[8])));
                }
                else {
                    rooms.add(new Room(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], Float.parseFloat(keys[6]), keys[7], Integer.parseInt(keys[8])));
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
     * @see ZvwDataType
     */
    public static void addFromDb(ResultSet rss, ZvwDataType type) {
        try {
            while(rss.next()) {
                switch(type) {
                    case EDT_ROOM: {
                        int id = rss.getInt("ID");
                        String description = rss.getString("DESCRIPTION");
                        String landlord = rss.getString("LANDLORD");
                        String street = rss.getString("STREET");
                        String location = rss.getString("LOCATION");
                        String fees = rss.getString("FEES");
                        String distance = rss.getString("DISTANCE");
                        Float qm = rss.getFloat("QM");
                        String imgPath = rss.getString("IMG_PATH");

                        rooms.add(new Room(description, landlord, street, location, fees, distance, qm, imgPath, id));
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
     * @see SearchType
     * @return Alle passenden Ergebnisse
     */
    public static ArrayList<Room> searchRoom(String token, SearchType keyType) {
        ArrayList<Room> results = new ArrayList<Room>();

        switch(keyType) {
            case EST_DISTANCE: {
                for(Room r : rooms) {
                    if(Float.parseFloat(r.getDistance().replace("km", "")) <= Float.parseFloat(token)) {
                        results.add(r);
                    }
                }
                break;
            }
            case EST_FEES: {
                for(Room r : rooms) {
                    if(Float.parseFloat(r.getFees().split(" ")[0]) <= Float.parseFloat(token)) {
                        results.add(r);
                    }
                }
                break;
            }
            case EST_LOCATION: {
                for(Room r : rooms) {
                    if(r.getLocation().toLowerCase().equals(token.toLowerCase())) {
                        results.add(r);
                    }
                }
                break;
            }
            case EST_QM: {
                for(Room r : rooms) {
                    if(r.getQm() >= Float.parseFloat(token)) {
                        results.add(r);
                    }
                }
                break;
            }
            case EST_STREET: {
                for(Room r : rooms) {
                    if(r.getStreet().toLowerCase().equals(token.toLowerCase())) {
                        results.add(r);
                    }
                }
                break;
            }
            case EST_LANDLORD: {
                for(Room r : rooms) {
                    if(r.getLandlord().toLowerCase().contains(token.toLowerCase())) {
                        results.add(r);
                    }
                }
            }
            default: {
                break;
            }
        }
        return results;
    }


    /**
     * Suchfunktion nach einen bestimmten Attribut im mitgegebnem Container
     * @param token Suchattribut
     * @param keyType Typ des Suchattributs
     * @see SearchType
     * @param searchContainer container, in dem gesucht werden soll
     * @return Alle passenden Ergebnisse
     */
    public static ArrayList<Room> searchRoom(String token, SearchType keyType, ArrayList<Room> searchContainer) {
        ArrayList<Room> results = new ArrayList<Room>();

        switch(keyType) {
            case EST_DISTANCE: {
                for(Room r : searchContainer) {
                    if(Float.parseFloat(r.getDistance().replace("km", "")) <= Float.parseFloat(token)) {
                        results.add(r);
                    }
                }
                break;
            }
            case EST_FEES: {
                for(Room r : searchContainer) {
                    if(Float.parseFloat(r.getFees().split(" ")[0].replace(',', '.')) <= Float.parseFloat(token.replace(',','.'))) {
                        results.add(r);
                    }
                }
                break;
            }
            case EST_LOCATION: {
                for(Room r : searchContainer) {
                    if(r.getLocation().toLowerCase().contains(token.toLowerCase())) {
                        results.add(r);
                    }
                }
                break;
            }
            case EST_QM: {
                for(Room r : searchContainer) {
                    if(r.getQm() >= Float.parseFloat(token.replace(',','.'))) {
                        results.add(r);
                    }
                }
                break;
            }
            case EST_STREET: {
                for(Room r : searchContainer) {
                    if(r.getStreet().toLowerCase().contains(token.toLowerCase())) {
                        results.add(r);
                    }
                }
                break;
            }
            case EST_LANDLORD: {
                for(Room r : searchContainer) {
                    if(r.getLandlord().toLowerCase().contains(token.toLowerCase())) {
                        results.add(r);
                    }
                }
            }
            default: {
                break;
            }
        }
        return results;
    }

    /**
     * Liefert ein Room - Objekt aus dem Container, dass den erhaltenen Daten entspricht
     * Das Array muss 7 Einträge lang sein:
     *  - Description
     *  - Landlord
     *  - Street
     *  - Location
     *  - Fees
     *  - Distance
     *  - Qm
     * @param data
     * @return Serialisiertes Room - Objekt
     */
    public static Room getRoomByRowData(String[] data) {
        for(Room r : rooms) {
            if(
               r.getDescription().equals(data[0]) &&
               r.getLandlord().equals(data[1]) &&
               r.getStreet().equals(data[2]) &&
               r.getLocation().equals(data[3]) &&
               r.getFees().equals(data[4]) &&
               r.getDistance().equals(data[5]) &&
               r.getQm() == Float.parseFloat(data[6].replace(",", ".")) &&
               r.getId() == Integer.parseInt(data[8])) {

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
     * Holt auf Basis der übergebenen Daten einen Benutzer aus dem internen Container und gibt diesen zurück
     * @param name Vorname
     * @param lastname Nachname
     * @return Das Benutzerobjekt, falls der Benutzer vorhanden ist, andernfalls null
     */
    public static User getCorrespondingUserObjectEx(String name, String lastname) {
        for(User u : users) {
            if(u.getFirstName().equals(name) && u.getLastName().equals(lastname)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Speichert alle Daten, die in den Containern aufgenommen wurden
     */
    public static void writeAllData() {
        CsvIO.updateRoomFile();
        CsvIO.updateUserFile();

        if(MainFrame.getMainFrame().getCurrentUser() instanceof Student) {
            CsvIO.appendRoomsToUser(MainFrame.getMainFrame().getCurrentUser(), ((Student)MainFrame.getMainFrame().getCurrentUser()).getWatchList());
        }
    }

    /**
     * Mapt die gelesenen Daten in ein Format, dass die JTable versteht (2D - Array)
     * @param type Welcher Container gemappt werden soll
     * @see ZvwDataType
     * @return Den Container in Array - Darstellung
     */
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
                String[][] roomsRet = new String[rooms.size()][9];
                for(int i = 0; i < rooms.size(); i++) {
                    roomsRet[i][0] = rooms.get(i).getDescription();
                    roomsRet[i][1] = rooms.get(i).getLandlord();
                    roomsRet[i][2] = rooms.get(i).getStreet();
                    roomsRet[i][3] = rooms.get(i).getLocation();
                    roomsRet[i][4] = rooms.get(i).getFees();
                    roomsRet[i][5] = rooms.get(i).getDistance();
                    roomsRet[i][6] = (String.valueOf(rooms.get(i).getQm()) + " m²").replace(".", ",");
                    roomsRet[i][7] = rooms.get(i).getImgPath();
                    roomsRet[i][8] = String.valueOf(rooms.get(i).getId());
                }
                return roomsRet;
            }
            default: {
                return null;
            }
        }
    }

    /**
     * Mapt die gelesenen Daten in ein Format, dass die JTable versteht (2D - Array)
     * @param data Der benutzerdefinierte Container
     * @return Den Container in Array - Darstellung
     */
    public static <T> String[][] mapToStringArray(ArrayList<T> data) {
        if(data.size() > 0) {
            if(data.get(0).getClass() == User.class) {
                String[][] usersRet = new String[data.size()][4];
                for(int i = 0; i < data.size(); i++) {
                    usersRet[i][0] = ((User)data.get(i)).getFirstName();
                    usersRet[i][1] = ((User)data.get(i)).getLastName();
                    usersRet[i][2] = ((User)data.get(i)).getUserName();
                    usersRet[i][3] = ((User)data.get(i)).getPassword();
                }
                return usersRet;
            }
            else if(data.get(0).getClass() == Room.class) {
                String[][] roomsRet = new String[data.size()][9];
                for(int i = 0; i < data.size(); i++) {
                    roomsRet[i][0] = ((Room)data.get(i)).getDescription();
                    roomsRet[i][1] = ((Room)data.get(i)).getLandlord();
                    roomsRet[i][2] = ((Room)data.get(i)).getStreet();
                    roomsRet[i][3] = ((Room)data.get(i)).getLocation();
                    roomsRet[i][4] = ((Room)data.get(i)).getFees();
                    roomsRet[i][5] = ((Room)data.get(i)).getDistance();
                    roomsRet[i][6] = (String.valueOf(((Room)data.get(i)).getQm()) + " m²").replace(".", ",");;
                    roomsRet[i][7] = ((Room)data.get(i)).getImgPath();
                    roomsRet[i][8] = String.valueOf(rooms.get(i).getId());
                }
                return roomsRet;
            }
            else {
                return new String[][] { };
            }
        }
        else {
            return new String[][] { };
        }
    }

    /**
     * Sucht Räume nach mehreren Kriterien
     * @param tokens Suchkriterien
     * @return Die Liste mit entsprechenden Räumen
     */
    public static ArrayList<Room> searchRoomsByTokens(RoomTokens tokens) {
        ArrayList<Room> resList = new ArrayList<Room>(rooms);

        if(!tokens.getDistance().equals("")) {
            resList = searchRoom(tokens.getDistance(),SearchType.EST_DISTANCE);
        }
        if(!tokens.getFees().equals("")) {
            resList = searchRoom(tokens.getFees(), SearchType.EST_FEES, resList);
        }
        if(!tokens.getLocation().equals("")) {
            resList = searchRoom(tokens.getLocation(), SearchType.EST_LOCATION, resList);
        }
        if(!tokens.getQm().equals("")) {
            resList = searchRoom(tokens.getQm(), SearchType.EST_QM, resList);
        }
        if(!tokens.getStreet().equals("")) {
            resList = searchRoom(tokens.getStreet(), SearchType.EST_STREET, resList);
        }

        return resList;

    }

    /**
     * Liefert die Räume anhand von "-" getrennten IDs aus der Benutzerdatei
     * Dient zur Speicherung von Räumen, die ein Student beobachtet
     * @param tags Die ID - Tags, die aus der login - csv ausgelesen wurden
     * @return Eine ArrayList, die die Zimmer enthält, die der jeweilige Student beobachtet
     */
    public static ArrayList<Room> getRoomsByIdTags(String tags) {
        String[] t = tags.split("-");
        ArrayList<Room> ret = new ArrayList<Room>();

        for(int i = 0; i < t.length; i++) {
            for(Room e : rooms) {
                if(String.valueOf(e.getId()).equals(t[i])) {
                    ret.add(e);
                }
            }
        }
        return ret;
    }

    public static void removeRoom(Room r) {
        rooms.remove(r);
    }

    public static void addRoom(Room r) {
        rooms.add(r);
    }

    /**
     * Generiert die nächstgrößte Zimmer - ID für ein neu erstelltes Zimmer
     * @return Die neue ID
     */
    public static int nextRoomID() {
        int maxId = 0;
        for(Room r : rooms) {
            if(r.getId() > maxId) {
                maxId = r.getId();
            }
        }

        return ++maxId;
    }

    /**
     * Erstellt eine nach den Benutzerrollen gefilterte Benutzerliste
     * @param role Rollenname ("Student", "Mitarbeiter", "Vermieter")
     * @return Die gefilterte Liste
     */
    public static ArrayList<User> getUsersByRole(String role) {
        ArrayList<User> ret = new ArrayList<User>();

        for(User u : users) {
            if(u.getRoleName().equals(role)) {
                ret.add(u);
            }
        }

        return ret;
    }

    //Getter

    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}
