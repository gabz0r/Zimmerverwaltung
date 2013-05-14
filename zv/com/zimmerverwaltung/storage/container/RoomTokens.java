package com.zimmerverwaltung.storage.container;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 14.05.13
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
public class RoomTokens {
    private String distance;
    private String fees;
    private String location;
    private String qm;
    private String street;

    public RoomTokens(String distance, String fees, String location, String qm, String street) {
        this.distance = distance;
        this.fees = fees;
        this.location = location;
        this.qm = qm;
        this.street = street;
    }

    public String getDistance() {
        return distance;
    }

    public String getFees() {
        return fees;
    }

    public String getLocation() {
        return location;
    }

    public String getStreet() {
        return street;
    }

    public String getQm() {
        return qm;
    }
}
