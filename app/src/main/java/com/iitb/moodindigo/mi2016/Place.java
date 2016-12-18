package com.iitb.moodindigo.mi2016;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by sajalnarang on 18/12/16.
 */

public class Place {
    private LatLng latLng;
    private String title;

    public Place(LatLng latLng, String title) {
        this.latLng = latLng;
        this.title = title;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}