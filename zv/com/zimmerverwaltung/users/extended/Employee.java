package com.zimmerverwaltung.users.extended;

import com.zimmerverwaltung.roles.*;
import com.zimmerverwaltung.storage.container.Room;
import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.storage.handler.SearchType;
import com.zimmerverwaltung.users.*;

import java.util.ArrayList;

/**
 * Implementiert alle Rechte, die von den Rollen "Mitarbeiter" und "Vermieter" bereitgestellt werden
 */
public class Employee extends User implements IRoleEmployee, IRoleLandlord {

    public Employee(String line) {
        super(line.split(";")[0], line.split(";")[1], line.split(";")[3], line.split(";")[4]);
    }

    @Override
    public String getRoleName() {
        return "Mitarbeiter";
    }

    /**
     * Überschrieben von IRoleLandlord
     * @return
     */
    @Override
    public ArrayList<Room> getMyRooms() {
        return DataHandler.searchRoom("DHBW Lörrach", SearchType.EST_LANDLORD);
    }
}
