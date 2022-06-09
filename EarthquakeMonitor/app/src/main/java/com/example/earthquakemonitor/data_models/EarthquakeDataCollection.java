package com.example.earthquakemonitor.data_models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class EarthquakeDataCollection implements Serializable {
    String type;
    Metadata metadata;
    List<Feature> features;

    public EarthquakeDataCollection(String type, Metadata metadata, List<Feature> features) {
        this.type = type;
        this.metadata = metadata;
        this.features = features;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    @NonNull
    @Override
    public String toString() {
        return "EarthquakeDataCollection{" +
                "type='" + type + '\'' +
                ", metadata=" + metadata +
                ", features=" + features +
                '}';
    }
}
