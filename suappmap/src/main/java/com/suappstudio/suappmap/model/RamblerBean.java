package com.suappstudio.suappmap.model;

import java.util.List;

/**
 * Created by laganas on 08/12/2014.
 */
public class RamblerBean {

    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    private String latitude;

    private String longitude;

    private int zoom;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public int getZoom() {
        return zoom;
    }

    public RamblerBean() {
    }
}
