package com.example.cvapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VolumesResponse {


    @SerializedName("items")
    @Expose
    List<Volume> items;

    public List<Volume> getItems() {
        return items;
    }
}


