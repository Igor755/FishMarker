package com.company.imetlin.fishmarker.pojo;

public class ModelClass {


    private String name;
    private String photo;
    private Double latitude;
    private Double longitude;
    private Integer zoom;

    public ModelClass(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }
    public ModelClass(String name) {
        this.name = name;
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public  double[] getCoordinates(){

        double[] cats = {latitude,longitude};

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
}


