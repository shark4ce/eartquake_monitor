package com.example.earthquakemonitor.data_models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contents implements Serializable {

    @SerializedName("nearby-cities.json")
    NearbyCitiesJSON nearbyCitiesJSON;

    public NearbyCitiesJSON getNearbyCitiesJSON() {
        return nearbyCitiesJSON;
    }

    public void setNearbyCitiesJSON(NearbyCitiesJSON nearbyCitiesJSON) {
        this.nearbyCitiesJSON = nearbyCitiesJSON;
    }

    public Contents(NearbyCitiesJSON nearbyCitiesJSON) {
        this.nearbyCitiesJSON = nearbyCitiesJSON;
    }
}
