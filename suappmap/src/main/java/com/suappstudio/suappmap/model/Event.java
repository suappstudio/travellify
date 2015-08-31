package com.suappstudio.suappmap.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by laganas on 08/12/2014.
 */
public class Event implements ClusterItem {

    private String id;
    private String latitude;
    private String longitude;
    private String name;
    private String people;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }


    public Event() {
    }

    @Override
    public LatLng getPosition() {

        return new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }
}
