package com.example.earthquakemonitor.data_models;

public class Feature {
    String id;
    String type;
    Properties properties;
    Geometry geometry;

    public Feature(String id, String type, Properties properties, Geometry geometry) {
        this.id = id;
        this.type = type;
        this.properties = properties;
        this.geometry = geometry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
