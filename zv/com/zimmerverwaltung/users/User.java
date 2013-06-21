package com.zimmerverwaltung.users;

import com.zimmerverwaltung.storage.handler.DataHandler;
import com.zimmerverwaltung.ui.LoginFrame;
import com.zimmerverwaltung.ui.MainFrame;
import com.zimmerverwaltung.users.extended.Employee;
import com.zimmerverwaltung.users.extended.Landlord;
import com.zimmerverwaltung.users.extended.Student;

import java.util.HashMap;

/**
 * Basisfunktionalität für den Benutzer:
 *  - Einloggen
 *  - Ausloggen
 *  - Anzeigen
 *  - Suchen
 *  - Speichern
 */
public abstract class User {
    protected String firstName;
    protected String lastName;
    protected String userName;
    protected String password;

    public User(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Mapt nach der in der CSV angegeben Rolle Objektinstanzen
     * @param line Zeile aus der CSV - Datei
     * @return Ein der Zeile entsprechendes Objekt
     */
    public static User mapRole(String line) {
        String role = line.split(";")[2];
        if(role.equals("Mitarbeiter")) {
            return new Employee(line);
        }
        else if(role.equals("Vermieter")) {
            return new Landlord(line);
        }
        else {
            return new Student(line);
        }
    }

    public void changePassword(String newPwd) {
        this.setPassword(newPwd);
        DataHandler.writeAllData();
    }

    public abstract String getRoleName();

    public void logout() {
        LoginFrame.getLoginFrame();
        MainFrame.getMainFrame().close();
    }

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName();
    }
}
