package com.example.cvapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VolumeInfo {

    @SerializedName("title")
    @Expose
    private  String title;

    private  List<String> authors;

    private  String canonicalVolumeLink;

    private  VolumeImageLinks imageLinks;

    private  String description;

    private ReadingModes readingModes;

    public ReadingModes getReadingModes() {
        return readingModes;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public String getWebPage() {
        return canonicalVolumeLink;
    }

    public VolumeImageLinks getImageLinks() {
        return imageLinks;
    }
}
