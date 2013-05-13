package com.zimmerverwaltung.storage.container;

/**
 * Created with IntelliJ IDEA.
 * User: Gabriel
 * Date: 25.04.13
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Gabriel
 * @version 0.1
 * Speichert ein Zimmer, das aus der CSV ausgelesen wird
 */
public class Room {
    private int id;
    private String description;
    private String landlord;
    private String street;
    private String location;
    private String fees;
    private String distance;
    private float qm;
    private String imgPath;

    public Room(int id, String description, String landlord, String street, String location, String fees, String distance, float qm, String imgPath) {
        this.id = id;
        this.description = description;
        this.landlord = landlord;
        this.street = street;
        this.location = location;
        this.fees = fees;
        this.distance = distance;
        this.qm = qm;
        this.imgPath = imgPath;
    }

    public Room(int id, String description, String landlord, String street, String location, String fees, String distance, float qm) {
        this.id = id;
        this.description = description;
        this.landlord = landlord;
        this.street = street;
        this.location = location;
        this.fees = fees;
        this.distance = distance;
        this.qm = qm;
        this.imgPath = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLandlord() {
        return landlord;
    }

    public void setLandlord(String landlord) {
        this.landlord = landlord;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public float getQm() {
        return qm;
    }

    public void setQm(float qm) {
        this.qm = qm;
    }
}
