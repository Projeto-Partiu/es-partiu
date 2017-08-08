package br.edu.ufcg.partiu.model;

import android.content.Context;

/**
 * Created by caiovidal on 08/08/17.
 */

public class LocationUser {
    private float latitude;
    private float longitude;

    public LocationUser(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
