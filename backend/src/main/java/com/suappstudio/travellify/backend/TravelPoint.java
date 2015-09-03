package com.suappstudio.travellify.backend;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.io.Serializable;

/**
 * Created by Android Dragon on 19/08/2015.
 */
@Entity
public class TravelPoint {

    @Id
    private String pointName;

    private String info;

    private GeoPt location;

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public GeoPt getLocation() {
        return location;
    }

    public void setLocation(GeoPt location) {
        this.location = location;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


}
