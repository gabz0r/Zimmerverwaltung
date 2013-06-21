package com.zimmerverwaltung.storage.container;

/**
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

    /**
     * Konstruktor für Zimmer mit Bildangabe
     * @param description Raumbeschreibung
     * @param landlord Vermieter
     * @param street Straße, Hausnummer [Straße Hausnr]
     * @param location Ortschaft
     * @param fees Miete
     * @param distance Entfernung zur DH
     * @param qm Wohnfläche
     * @param imgPath Pfad zum Bild (Vorschau)
     * @param id ID des Zimmers
     */
    public Room(String description, String landlord, String street, String location, String fees, String distance, float qm, String imgPath, int id) {
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

    /**
     * Konstruktor für Zimmer ohne Bildangabe
     * @param description Raumbeschreibung
     * @param landlord Vermieter
     * @param street Straße, Hausnummer [Straße Hausnr]
     * @param location Ortschaft
     * @param fees Miete
     * @param distance Entfernung zur DH
     * @param qm Wohnfläche
     * @param id ID des Zimmers
     */
    public Room(String description, String landlord, String street, String location, String fees, String distance, float qm, int id) {
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

    /**
     * Vergleich über ID
     * @param o Anderes Room - Objekt
     * @return True / False
     */
    @Override
    public boolean equals(Object o) {
        if(((Room) o).getId() == getId()) {
            return true;
        }
        return false;
    }

    /**
     * Override für Vergleichsfunktionen
     * Ruft die Funktion aus super auf
     * @return
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
