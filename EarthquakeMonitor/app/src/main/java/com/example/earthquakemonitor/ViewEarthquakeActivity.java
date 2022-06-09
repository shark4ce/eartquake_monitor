package com.example.earthquakemonitor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.earthquakemonitor.constants.Constants;
import com.example.earthquakemonitor.data_models.City;
import com.example.earthquakemonitor.data_models.Contents;
import com.example.earthquakemonitor.data_models.Feature;
import com.example.earthquakemonitor.data_models.Geometry;
import com.example.earthquakemonitor.data_models.NearbyCities;
import com.example.earthquakemonitor.data_models.NearbyCitiesJSON;
import com.example.earthquakemonitor.data_models.Products;
import com.example.earthquakemonitor.data_models.Properties;
import com.example.earthquakemonitor.getdata.EarthquakeDataController;
import com.example.earthquakemonitor.services.EarthquakeGetDataService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewEarthquakeActivity extends AppCompatActivity {

    TextView textViewMag;
    TextView textViewTime;
    TextView textViewPlace;
    TextView textViewCities;
    TextView textViewMagType;
    TextView textViewEventType;
    TextView textViewTypeTitle;
    TextView textViewPlaceTitle;
    TextView textViewTimeTitle;
    TextView textViewCitiesTitle;
    TextView textViewMagTitle;
    TextView textViewMagTypeTitle;

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_earthquake);
        initUI();

        Intent intent = this.getIntent();
        if (intent != null) {
            EarthquakeDataController earthquakeDataController;
            String featureId = intent.getStringExtra(Constants.FEATURE_ID);
            Log.d(TAG, featureId);

            try {
                earthquakeDataController = new EarthquakeDataController();
                EarthquakeGetDataService earthquakeGetDataService = earthquakeDataController.getService();
                Call<Feature> earthquakeDetails = earthquakeGetDataService.getEarthquakeDetails(featureId, Constants.GET_DATA_FORMAT);

                earthquakeDetails.enqueue(new Callback<Feature>() {
                    @Override
                    public void onResponse(@NonNull Call<Feature> call, @NonNull Response<Feature> response) {
                        if (response.body() != null) {
                            Feature earthquakeFeature = response.body();
                            Products earthquakeProducts = earthquakeFeature.getProperties().getProducts();
                            Log.d(TAG, earthquakeProducts.toString());
                            List<NearbyCities> nearbyCitiesList = earthquakeProducts.getNearbyCitiesList();

                            if (nearbyCitiesList != null && nearbyCitiesList.size() > 0) {
                                Contents contents = nearbyCitiesList.get(0).getContents();
                                NearbyCitiesJSON nearbyCitiesJSON = contents.getNearbyCitiesJSON();
                                String urlNearbyCities = nearbyCitiesJSON.getUrl();

                                Call<List<City>> earthQuakeCitiesCall = earthquakeGetDataService.getEarthquakeNearbyCities(urlNearbyCities);
                                earthQuakeCitiesCall.enqueue(new Callback<List<City>>() {
                                    @Override
                                    public void onResponse(@NonNull Call<List<City>> call, @NonNull Response<List<City>> response) {
                                        if (response.body() != null) {
                                            List<City> cityList = response.body();

                                            if (cityList.size() > 0) {
                                                setItemData(earthquakeFeature, cityList);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<List<City>> call, @NonNull Throwable t) {
                                        Log.d(TAG, "earthQuakeCitiesCall Failure" + t);
                                    }
                                });
                            }else{
                                Log.d(TAG, "earthquake doesn't have nearby cities");
                                setItemData(earthquakeFeature, null);
                            }
                        } else {
                            Log.d(TAG, "Response body for earthquakeDetails is null!");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Feature> call, @NonNull Throwable t) {
                        Log.d(TAG, "Entered onFailure for earthquakeDataCall22.");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onStartJob problem.");
            }
        }
    }

    private void initUI() {
        textViewMag =  findViewById(R.id.mag);
        textViewEventType = findViewById(R.id.eventType);
        textViewPlace = findViewById(R.id.place);
        textViewTime = findViewById(R.id.time);
        textViewCities = findViewById(R.id.cities);
        textViewMagType = findViewById(R.id.magType);
        textViewTypeTitle = findViewById(R.id.type_title);
        textViewPlaceTitle = findViewById(R.id.place_title);
        textViewTimeTitle = findViewById(R.id.time_title);
        textViewCitiesTitle = findViewById(R.id.cities_title);
        textViewMagTitle = findViewById(R.id.mag_title);
        textViewMagTypeTitle = findViewById(R.id.magtype_title);
    }

    @SuppressLint("SetTextI18n")
    private void setItemData(Feature earthquakeFeature, List<City> nearbyCitiesList) {
        initUI();

        Properties earthquakeProperties = earthquakeFeature.getProperties();
        textViewMag.setText(Float.toString(earthquakeProperties.getMag()));
        textViewMagType.setText(earthquakeProperties.getMagType());
        textViewTime.setText(earthquakeProperties.getTime().toString());
        textViewEventType.setText(earthquakeProperties.getType());
        textViewPlace.setText(earthquakeProperties.getPlace());
        textViewTypeTitle.setText("Type");
        textViewMagTypeTitle.setText("Magnitude Type");
        textViewPlaceTitle.setText("Place");
        textViewTimeTitle.setText("Time");
        textViewCitiesTitle.setText("Nearby Cities");
        textViewMagTitle.setText("Magnitude");

        StringBuilder cities = new StringBuilder();
        if (nearbyCitiesList != null && nearbyCitiesList.size() != 0){
            for (City city: nearbyCitiesList) {
                cities.append(city.getDistance())
                        .append("km ")
                        .append(city.getDirection())
                        .append(" - ")
                        .append(city.getName())
                        .append("\n");
            }
        }else{
            cities.append("No data");
        }
        textViewCities.setText(cities);

        addOnClickListeners(earthquakeFeature.getGeometry());
    }

    private void addOnClickListeners(Geometry geometry) {

        List<Float> coordinates = geometry.getCoordinates();
        Float longitude = coordinates.get(0);
        Float latitude = coordinates.get(1);

        Log.d("Geometry", longitude + " " + latitude);
        textViewPlace.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:" + 0 + ", " + 0 + "?q=" +  latitude + ", " + longitude + "(label)");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
    }
}