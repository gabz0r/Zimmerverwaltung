package com.zimmerverwaltung.storage.container;

/**
 * Klasse hält Tokens für einen Suchvorgang
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
