package com.example.pandemic_monitoring.domain.geopoint;

import java.io.Serializable;

/**
 *
 * @author Martin Lopez <martinlopez@github.com>
 */
public class GeoPoint implements Serializable {

    private double lat;
    private double lon;

    public GeoPoint(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return "GeoPoint{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
