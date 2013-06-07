package com.zimmerverwaltung.users.extended;

import com.zimmerverwaltung.roles.*;
import com.zimmerverwaltung.users.*;

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
}
