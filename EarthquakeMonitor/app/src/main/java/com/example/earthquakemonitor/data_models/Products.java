package com.example.earthquakemonitor.data_models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Products {
    @SerializedName("nearby-cities")
    List<NearbyCities> nearbyCitiesList;

    public Products(List<NearbyCities> nearbyCitiesList) {
        this.nearbyCitiesList = nearbyCitiesList;
    }

    public List<NearbyCities> getNearbyCitiesList() {
        return nearbyCitiesList;
    }

    public void setNearbyCitiesList(List<NearbyCities> nearbyCitiesList) {
        this.nearbyCitiesList = nearbyCitiesList;
    }

    @NonNull
    @Override
    public String toString() {
        return "Products{" +
                "nearbyCitiesList=" + nearbyCitiesList +
                '}';
    }
}
