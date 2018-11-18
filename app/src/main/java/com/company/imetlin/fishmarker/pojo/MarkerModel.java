package com.company.imetlin.fishmarker.pojo;

public class MarkerModel {


    private int id;
    private float longitude;
    private float latitude;
    private String date;
    private float depth;
    private int amountoffish;
    private String note;

    public MarkerModel(int id, float longitude, float latitude, String date, float depth, int amountoffish, String note) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
        this.depth = depth;
        this.amountoffish = amountoffish;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public int getAmountoffish() {
        return amountoffish;
    }

    public void setAmountoffish(int amountoffish) {
        this.amountoffish = amountoffish;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "MarkerModel{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", date='" + date + '\'' +
                ", depth=" + depth +
                ", amountoffish=" + amountoffish +
                ", note='" + note + '\'' +
                '}';
    }
}
