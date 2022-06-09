package com.example.earthquakemonitor.data_models;

public class City {
    String name;
    String direction;
    int distance;

    public City(String name, String direction, int distance) {
        this.name = name;
        this.direction = direction;
        this.distance = distance;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
