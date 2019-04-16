package com.company.imetlin.fishmarker.pojo;

public class Places {

    private String nameplace;
    private Double latitude;
    private Double longitude;
    private int zoom;

    public Places(String nameplace, Double latitude, Double longitude, int zoom) {
        this.nameplace = nameplace;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zoom = zoom;
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

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
}
