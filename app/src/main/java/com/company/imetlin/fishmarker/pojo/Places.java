package com.company.imetlin.fishmarker.pojo;

public class Places {

    private String nameplace;
    private Double latitude;
    private Double longitude;
    private Double zoom;
    private String uid;
    private String place_id;
    private String waterobject;

    public Places() {
    }


    public Places(String nameplace, Double latitude, Double longitude, Double zoom) {
        this.nameplace = nameplace;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zoom = zoom;
    }
    public Places(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWaterobject() {
        return waterobject;
    }

    public void setWaterobject(String waterobject) {
        this.waterobject = waterobject;
    }

    public Places(String nameplace, Double latitude, Double longitude, Double zoom, String uid, String waterobject, String place_id) {
        this.nameplace = nameplace;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zoom = zoom;
        this.uid = uid;
        this.waterobject = waterobject;
        this.place_id = place_id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getNameplace() {
        return nameplace;
    }

    public void setNameplace(String nameplace) {
        this.nameplace = nameplace;
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

    public Double getZoom() {
        return zoom;
    }

    public void setZoom(Double zoom) {
        this.zoom = zoom;
    }
}
