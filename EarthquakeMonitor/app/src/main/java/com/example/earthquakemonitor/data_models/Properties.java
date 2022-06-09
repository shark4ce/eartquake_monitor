package com.example.earthquakemonitor.data_models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Properties implements Serializable {
    float mag;
    String place;
    Long time;
    Long updated;
    String tz;
    Integer felt;
    String alert;
    String status;
    String magType;
    String type;
    String ids;
    String detail;
    Products products;

    public Properties(float mag, String place, Long time, Long updated, String tz, Integer felt, String alert, String status, String magType, String type, String ids, String detail, Products products) {
        this.mag = mag;
        this.place = place;
        this.time = time;
        this.updated = updated;
        this.tz = tz;
        this.felt = felt;
        this.alert = alert;
        this.status = status;
        this.magType = magType;
        this.type = type;
        this.ids = ids;
        this.detail = detail;
        this.products = products;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public float getMag() {
        return mag;
    }

    public void setMag(float mag) {
        this.mag = mag;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getTime() {
        return new Date(time);
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public Integer getFelt() {
        return felt;
    }

    public void setFelt(Integer felt) {
        this.felt = felt;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMagType() {
        return magType;
    }

    public void setMagType(String magType) {
        this.magType = magType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return "Properties{" +
                "mag=" + mag +
                ", place='" + place + '\'' +
                ", time=" + time +
                ", updated=" + updated +
                ", tz='" + tz + '\'' +
                ", felt=" + felt +
                ", alert='" + alert + '\'' +
                ", status='" + status + '\'' +
                ", magType='" + magType + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
