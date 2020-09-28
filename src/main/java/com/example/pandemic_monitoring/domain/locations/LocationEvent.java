package com.example.pandemic_monitoring.domain.locations;

import com.example.pandemic_monitoring.domain.geopoint.GeoPoint;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;


public class LocationEvent implements Serializable {

    private Long userID;
    private GeoPoint geoPoint;
    private Date publishTime;

    public LocationEvent(Long userID, GeoPoint geoPoint, Date publishTime) {
        this.userID = userID;
        this.geoPoint = geoPoint;
        this.publishTime = publishTime;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "LocationEvent{" +
                "userID=" + userID +
                ", geoPoint=" + geoPoint +
                ", publishTime=" + publishTime +
                '}';
    }
}
