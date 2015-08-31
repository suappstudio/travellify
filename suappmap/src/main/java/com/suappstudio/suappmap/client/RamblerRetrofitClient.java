package com.suappstudio.suappmap.client;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by laganas on 08/12/2014.
 */
public class RamblerRetrofitClient {

    private  static RamblerApi ramblerApi;

    private static final String ENDPOINT = "http://api-rambler.rhcloud.com/rest";

    public static synchronized RamblerApi init(){
        OkHttpClient okHttpClient = new OkHttpClient();
        ramblerApi = new RestAdapter.Builder().setEndpoint(ENDPOINT).setLogLevel(RestAdapter.LogLevel.FULL).setClient(new OkClient(okHttpClient)).build().create(RamblerApi.class);
        return ramblerApi;
    }

}
