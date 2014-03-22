package com.example.imageapp.example;

import java.io.Serializable;

/**
 * ModelImage - represents each json object from the json
 * array returned.
 * Created by sratanjee on 3/1/14.
 */
public class ModelImage implements Serializable{

    private String mDescription;
    private String mDate;
    private String mUrl;
    private String mUser;
    private String mLat;
    private String mLong;

    public ModelImage() {
    }

    //Getters and setters for this object
    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getDate() {
        return this.mDate;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void setUser(String user) {
        this.mUser = user;
    }

    public String getUser() {
        return this.mUser;
    }

    public void setLatitude(String latitude) {
        this.mLat = latitude;
    }

    public double getLatitude() {
        double value = Double.parseDouble(this.mLat);
        return value;
    }

    public void setLongitude(String longitude) {
        this.mLong = longitude;
    }

    public double getLongitude() {
        double value = Double.parseDouble(this.mLong);
        return value;
    }
}
