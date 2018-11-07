package com.company.imetlin.fishmarker.pojo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ModelClass {


    private String name;
    private String photo;
    private Double latitude;
    private Double longitude;

    public ModelClass(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }
    public ModelClass(String name) {
        this.name = name;
    }

    public ModelClass(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Double getLatitude() {
        return latitude;
    }
    public  double[] getCoordinates(){

        double[] cats = {longitude,longitude};

        return cats;
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

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}


