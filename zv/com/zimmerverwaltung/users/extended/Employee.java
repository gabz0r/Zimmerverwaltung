package com.zimmerverwaltung.users.extended;

import com.zimmerverwaltung.roles.*;
import com.zimmerverwaltung.users.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 25.04.13
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Gabriel
 * @version 0.1
 * Implementiert alle Rechte, die von den Rollen "Mitarbeiter" und "Vermieter" bereitgestellt werden
 */
public class Employee extends User implements IRoleEmployee, IRoleLandlord {
    public Employee(String line) {
        super(line.split(";")[3], line.split(";")[4]);
    }

    @Override
    public String getRoleName() {
        return "Mitarbeiter";
    }
}
