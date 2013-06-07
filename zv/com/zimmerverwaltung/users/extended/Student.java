package com.zimmerverwaltung.users.extended;

import com.zimmerverwaltung.roles.*;
import com.zimmerverwaltung.storage.container.*;
import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.ui.dispatcher.DispatcherObject;
import com.zimmerverwaltung.ui.dispatcher.EventDispatcher;
import com.zimmerverwaltung.ui.dispatcher.EventTargets;
import com.zimmerverwaltung.users.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 25.04.13
 * Time: 11:29
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Gabriel
 * @version 0.1
 * Implementiert alle Rechte, die die Rolle "Student" bereitstellt
 */
public class Student extends User implements IRoleStudent {
    private ArrayList<Room> watchList;

    public Student(String line) {
        super(line.split(";")[0], line.split(";")[1], line.split(";")[3], line.split(";")[4]);
        watchList = new ArrayList<Room>();

        if(line.split(";").length > 5) {
            watchList.addAll(DataHandler.getRoomsByIdTags(line.split(";")[5]));
        }
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
}
