package com.zimmerverwaltung.roles;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 25.04.13
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */

import com.zimmerverwaltung.storage.container.*;

/**
 * @author Gabriel
 * @version 0.1
 * Stellt Methoden bereit, die ein Student zum Zimmer Suchen benötigt
 */
public interface IRoleStudent {
    public void rememberRoom(Room r);
    public void forgetRoom(Room r);
}
