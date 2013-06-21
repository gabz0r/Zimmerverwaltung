package com.zimmerverwaltung.users.extended;

import com.zimmerverwaltung.roles.*;
import com.zimmerverwaltung.storage.container.*;
import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.users.*;

import java.util.ArrayList;

/**
 * Implementiert alle Rechte, die die Rolle "Student" bereitstellt
 */
public class Student extends User implements IRoleStudent {
    private ArrayList<Room> watchList;
    private int myRoomId;

    public Student(String line) {
        super(line.split(";")[0], line.split(";")[1], line.split(";")[3], line.split(";")[4]);
        myRoomId = -1;
        watchList = new ArrayList<Room>();

        if(line.split(";").length > 5) {
            if(line.split(";")[5].contains("-")) {
                watchList.addAll(DataHandler.getRoomsByIdTags(line.split(";")[5]));
            }
            else {
                myRoomId = Integer.valueOf(line.split(";")[5]);
            }
        } else if(line.split(";").length > 6) {
            myRoomId = Integer.valueOf(line.split(";")[6]);
        }
    }

    public Student(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
    }

    /**
     * Fügt eine Wohnung zur Merkliste hinzu
     * @param r Hinzuzufügender Raum
     */
    @Override
    public void rememberRoom(Room r) {
        watchList.add(r);
    }

    /**
     * Entfernt eine Wohung aus der Merkliste
     * @param r Zu entfernender Raum
     */
    @Override
    public void forgetRoom(Room r) {
        watchList.remove(r);
    }

    /**
     * Gibt an, ob ein Student bereits eine Wohnung beobachtet
     * @param r Zu überprüfender Raum
     * @return Warheitswert, der angibt, ob der Raum bereits beobachtet wird
     */
    public boolean remembersRoom(Room r) {
        return watchList.contains(r);
    }

    public ArrayList<Room> getWatchList() {
        return watchList;
    }

    @Override
    public String getRoleName() {
        return "Student";
    }

    public int getMyRoomId() {
        return myRoomId;
    }

    public void setMyRoomId(int myRoomId) {
        this.myRoomId = myRoomId;
    }
}
