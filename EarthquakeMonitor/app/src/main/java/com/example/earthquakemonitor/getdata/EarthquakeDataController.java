package com.example.earthquakemonitor.getdata;

import android.util.Log;

import com.example.earthquakemonitor.services.EarthquakeGetDataService;

import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EarthquakeDataController {

    private static Retrofit retrofit = null;
    private final String TAG = this.getClass().getSimpleName();

    public EarthquakeDataController() throws MalformedURLException {
        URL url = new URL("https://earthquake.usgs.gov/");


//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.level(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public EarthquakeGetDataService getService() {
        Log.d(TAG, "Getting Service from EarthquakeGetDataController. ");
        return retrofit.create(EarthquakeGetDataService.class);
    }
}
