package com.zimmerverwaltung.roles;

import com.zimmerverwaltung.storage.container.Room;
import com.zimmerverwaltung.users.extended.Landlord;

import java.util.ArrayList;

/**
 * Stellt Methoden bereit, die ein Vermieter benötigt, um seine Angebote zu verwalten
 */
public interface IRoleLandlord {
    public abstract ArrayList<Room> getMyRooms();
}
