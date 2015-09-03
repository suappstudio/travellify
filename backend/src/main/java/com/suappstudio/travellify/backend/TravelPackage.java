package com.suappstudio.travellify.backend;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Load;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Dragon on 03/09/2015.
 */
@Entity
public class TravelPackage  {

    @Id
    private String id;

    @Load
    private List<Ref<TravelPoint>> points = new ArrayList<Ref<TravelPoint>>();

    private String name;

    public List<TravelPoint> getPoints() {

        List<TravelPoint> travelPoints = new ArrayList<TravelPoint>();
        while (points.iterator().hasNext()){
            travelPoints.add(points.iterator().next().get());
        }
        return  travelPoints;
    }



    public void setPoints(List<TravelPoint> points) {

        for (TravelPoint p : points){

            this.points.add(Ref.create(p));
        }
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
