package com.example.earthquakemonitor.data_models;

public class NearbyCities {
    Contents contents;

    public NearbyCities(Contents contents) {
        this.contents = contents;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }
}
