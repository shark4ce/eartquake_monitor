package com.example.earthquakemonitor.scheduled_jobs;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.earthquakemonitor.constants.Constants;
import com.example.earthquakemonitor.data_models.EarthquakeDataCollection;
import com.example.earthquakemonitor.getdata.EarthquakeDataController;
import com.example.earthquakemonitor.services.EarthquakeGetDataService;

import java.io.IOException;
import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class GetEarthquakesJobService extends JobService {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.d(TAG, "Scheduled Job started");

        LocalDate endDate = LocalDate.now();
        LocalDate startDate= endDate.minusWeeks(getWeeksTimeInterval());

        Log.d(TAG, startDate.toString() + " " + endDate);
        EarthquakeDataController earthquakeDataController;

        try {
            earthquakeDataController = new EarthquakeDataController();
            EarthquakeGetDataService earthquakeGetDataService = earthquakeDataController.getService();
            Call<EarthquakeDataCollection> earthquakeDataCall = earthquakeGetDataService.getEarthquakesData(Constants.GET_DATA_FORMAT, startDate.toString(), endDate.toString());

            earthquakeDataCall.enqueue(new Callback<EarthquakeDataCollection>() {
                @Override
                public void onResponse(@NonNull Call<EarthquakeDataCollection> call, @NonNull Response<EarthquakeDataCollection> response) {
                    Log.d(TAG, "EarthQuakeDataCall entered.");
                    if (response.body() != null) {

                        EarthquakeDataCollection rsp = response.body();

                        // send to main activity broadcast receiver
                        Intent broadcastIntent = new Intent(Constants.VIEW_EQ_DETAIL_INTENT);
                        Bundle args = new Bundle();
                        args.putSerializable(Constants.EQ_DATA, rsp);
                        broadcastIntent.putExtra(Constants.BUNDLE_ARGS, args);
                        LocalBroadcastManager.getInstance(GetEarthquakesJobService.this).sendBroadcast(broadcastIntent);

                        Log.d(TAG, "Data received " + rsp.getType());
                    } else {
                        Log.d(TAG, "Response body for earthquakeDataCall is null!");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<EarthquakeDataCollection> call, @NonNull Throwable t) {
                    Log.d(TAG, "Entered onFailure for earthquakeDataCall.");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "onStartJob problem.");
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private int getWeeksTimeInterval() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.SETTINGS_SHARED_PREF, Context.MODE_PRIVATE);
        return Integer.parseInt(sharedPreferences.getString(Constants.DATA_FEED_INTERVAL_SHARED_PREF, "4"));
    }
}
