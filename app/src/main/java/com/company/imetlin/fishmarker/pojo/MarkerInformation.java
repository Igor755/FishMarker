package com.company.imetlin.fishmarker.pojo;

public class MarkerInformation {



    private String uid;
    private String marker_id;
    private Double latitude;
    private Double longitude;
    private String title;
    private String date;
    private Double depth;
    private Integer amount;
    private String note;


    public MarkerInformation(){

    }
    public MarkerInformation(String uid,String marker_id, Double latitude, Double longitude, String title, String date, Double depth, Integer amount, String note) {
        this.uid = uid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.date = date;
        this.depth = depth;
        this.amount = amount;
        this.note = note;
        this.marker_id = marker_id;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMarker_id() {
        return marker_id;
    }

    public void setMarker_id(String marker_id) {
        this.marker_id = marker_id;
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





}
