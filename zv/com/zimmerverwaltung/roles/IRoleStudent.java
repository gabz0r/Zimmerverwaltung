package com.zimmerverwaltung.roles;

import com.zimmerverwaltung.storage.container.*;

/**
 * Stellt Methoden bereit, die ein Student zum Zimmer Suchen benötigt
 */
public interface IRoleStudent {
    public void rememberRoom(Room r);
    public void forgetRoom(Room r);
}
