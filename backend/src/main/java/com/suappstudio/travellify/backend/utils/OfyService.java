package com.suappstudio.travellify.backend.utils;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.suappstudio.travellify.backend.TravelPoint;


/**
 * Created by Android Dragon on 16/08/2015.
 */
public final class OfyService {


    /**
     * Dnefault constructor, never called.
     */
    private OfyService() {
    }

    static {
        factory().register(TravelPoint.class);
//        factory().register(CheckIn.class);
//        factory().register(Offer.class);
//        factory().register(Recommendation.class);
//        factory().register(Place.class);
    }

    /**
     * Returns the Objectify service wrapper.
     * @return The Objectify service wrapper.
     */
    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    /**
     * Returns the Objectify factory service.
     * @return The factory service.
     */
    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
