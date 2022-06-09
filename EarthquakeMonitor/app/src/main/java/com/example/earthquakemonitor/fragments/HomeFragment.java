package com.example.earthquakemonitor.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.earthquakemonitor.adapters.EarthquakesListAdapter;
import com.example.earthquakemonitor.R;
import com.example.earthquakemonitor.ViewEarthquakeActivity;
import com.example.earthquakemonitor.constants.Constants;
import com.example.earthquakemonitor.data_models.EarthquakeDataCollection;
import com.example.earthquakemonitor.data_models.Feature;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class HomeFragment extends Fragment {

    Gson gson = new Gson();
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getDataFromFile(view);
        initBroadcastReceiver(view);

        return view;
    }

    private void initBroadcastReceiver(View view) {
        BroadcastReceiver updateUIReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Received broadcast message");
                Bundle args = intent.getBundleExtra(Constants.BUNDLE_ARGS);
                EarthquakeDataCollection earthquakeDataCollection = (EarthquakeDataCollection) args.getSerializable(Constants.EQ_DATA);
                saveDataToFile(context, earthquakeDataCollection);
                loadItemContent(view, earthquakeDataCollection);
            }
        };
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(updateUIReceiver, new IntentFilter(Constants.VIEW_EQ_DETAIL_INTENT));
    }

    private void saveDataToFile(Context context, EarthquakeDataCollection earthquakeDataCollection) {
        File fileWithData = new File(context.getFilesDir(), Constants.DATA_FEED_FILE_NAME);
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(fileWithData, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(gson.toJson(earthquakeDataCollection));
            bufferedWriter.close();
            Log.d(TAG, "Saved to file: " + fileWithData.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Not saved to file: " + fileWithData.getAbsolutePath());
        }
    }

    private void loadItemContent(View view, EarthquakeDataCollection earthquakeDataCollection) {
        EarthquakesListAdapter earthquakesListAdapter = new EarthquakesListAdapter(view.getContext(), earthquakeDataCollection);
        ListView earthquakeList = view.findViewById(R.id.androidList);
        earthquakeList.setAdapter(earthquakesListAdapter);
        Log.d(TAG, "loadItem ----> " + earthquakeDataCollection.getFeatures().size());
        earthquakeList.setOnItemClickListener((parent, view1, position, id) -> {
            Log.d(TAG, "Started item activity");
            Feature earthquakeFeature =  earthquakeDataCollection.getFeatures().get(position);
            Intent intent = new Intent(view1.getContext(), ViewEarthquakeActivity.class);
            intent.putExtra(Constants.FEATURE_ID, earthquakeFeature.getId());
            startActivity(intent);
        });
    }

    private void getDataFromFile(View view) {

        EarthquakeDataCollection earthquakeDataCollection;
        File fileWithData = new File(view.getContext().getFilesDir(), Constants.DATA_FEED_FILE_NAME);
        try {
            earthquakeDataCollection = gson.fromJson(new FileReader(fileWithData), EarthquakeDataCollection.class);
            loadItemContent(view, earthquakeDataCollection);
            Log.d(TAG, "Get Data from file: " + fileWithData.getAbsolutePath());
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Data could not be extracted from file: " + fileWithData.getAbsolutePath());
        }
    }
}