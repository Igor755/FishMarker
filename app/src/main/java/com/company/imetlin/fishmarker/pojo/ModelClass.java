package com.company.imetlin.fishmarker.pojo;

import java.util.Comparator;

public class ModelClass implements  Comparable<ModelClass> {


    private String name;
    private String photo;
    private Integer zoom;



    private Integer id;
    private Double latitude;
    private Double longitude;
    private String title;
    private String date;
    private Integer depth;
    private Integer amount;
    private String note;



    public ModelClass(Integer id, Double latitude, Double longitude, String title, String date, Integer depth, Integer amount, String note) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.date = date;
        this.depth = depth;
        this.amount = amount;
        this.note = note;
    }

    public ModelClass(String name, Double latitude, Double longitude, Integer zoom) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zoom = zoom;

    }

    public ModelClass(Double latitude, Double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;

    }

    public ModelClass(Double latitude, Double longitude, String date) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;

    }

    public ModelClass(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }

    public ModelClass(String name) {
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public double[] getCoordinates() {

        double[] cats = {latitude, longitude};

        return cats;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "ModelClass{" +
                "name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", zoom=" + zoom +
                '}';
    }



//SORT_WATER_ACTIVITY

    @Override
    public int compareTo(ModelClass o) {
        return this.name.compareTo(o.name);
    }
}


