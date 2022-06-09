package com.example.earthquakemonitor.services;

import com.example.earthquakemonitor.data_models.City;
import com.example.earthquakemonitor.data_models.EarthquakeDataCollection;
import com.example.earthquakemonitor.data_models.Feature;
import com.example.earthquakemonitor.data_models.NearbyCities;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EarthquakeGetDataService {
    @GET("/fdsnws/event/1/query")
    Call<EarthquakeDataCollection> getEarthquakesData(@Query("format") String format,
                                                      @Query("starttime") String starttime,
                                                      @Query("endtime") String endtime);

    @GET("{fullUrl}")
    Call<List<City>> getEarthquakeNearbyCities(@Path(value = "fullUrl", encoded = true) String fullUrl);

    @GET("/fdsnws/event/1/query")
    Call<Feature> getEarthquakeDetails(@Query("eventid") String eventid,
                                       @Query("format") String format);
}



