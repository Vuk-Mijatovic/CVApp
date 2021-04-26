package com.example.cvapp;

import java.util.List;

public class VolumeInfo {


    private  String title;

    private  List<String> authors;

    private  String canonicalVolumeLink;

    private  VolumeImageLinks imageLinks;

    private  String description;



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
