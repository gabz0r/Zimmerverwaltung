package com.zimmerverwaltung.users.extended;

import com.zimmerverwaltung.roles.*;
import com.zimmerverwaltung.storage.container.Room;
import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.storage.handler.SearchType;
import com.zimmerverwaltung.users.*;

import java.util.ArrayList;

/**
 * Implementiert alle Rechte, die die Rolle "Vermieter" bereitstellt
 */
public class Landlord extends User implements IRoleLandlord {

    public Landlord(String line) {
        super(line.split(";")[0], line.split(";")[1], line.split(";")[3], line.split(";")[4]);
    }

    @Override
    public String getRoleName() {
        return "Vermieter";
    }

    @Override
    public ArrayList<Room> getMyRooms() {
        return DataHandler.searchRoom(this.getUserName(), SearchType.EST_LANDLORD);
    }
}
