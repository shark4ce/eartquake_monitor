package com.example.earthquakemonitor.data_models;

public class NearbyCitiesJSON
{
    int length;
    String url;

    public NearbyCitiesJSON(int length, String url) {
        this.length = length;
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
