package com.company.imetlin.fishmarker.pojo;

public class MarkerInformation {



    private String uid;
    private String id_marker_key;
    private Double latitude;
    private Double longitude;
    private String title;
    private String date;
    private Double depth;
    private Integer amount;
    private String note;


    public MarkerInformation(){

    }
    public MarkerInformation(String uid,String id_marker_key, Double latitude, Double longitude, String title, String date, Double depth, Integer amount, String note) {
        this.uid = uid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.date = date;
        this.depth = depth;
        this.amount = amount;
        this.note = note;
        this.id_marker_key = id_marker_key;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId_marker_key() {
        return id_marker_key;
    }

    public void setId_marker_key(String id_marker_key) {
        this.id_marker_key = id_marker_key;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "MarkerInformation{" +
                "uid='" + uid + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", depth='" + depth + '\'' +
                ", amount='" + amount + '\'' +
                ", note='" + note + '\'' +
                ", id_marker_key='" + id_marker_key + '\'' +
                '}';
    }

}
