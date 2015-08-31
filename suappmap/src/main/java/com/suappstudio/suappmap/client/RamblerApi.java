package com.suappstudio.suappmap.client;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import com.suappstudio.suappmap.model.RamblerBean;

/**
 * Created by laganas on 08/12/2014.
 */
public interface RamblerApi {

    @GET("/event/{place}")
    public RamblerBean getEvents(@Path("place") String placeName );

    @GET("/event/{place}")
    public void getEvents(@Path("place") String placeName, @Query("day") String day, @Query("time") String time, Callback<RamblerBean> callback);


}
