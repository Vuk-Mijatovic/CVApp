package com.example.cvapp;

public class Attraction {
    private String title;
    private String shortDescription;
    private String address;
    private String description;
    private int imageResourceId;

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public Attraction(String title, String shortDescription, String address, String description) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.address = address;
        this.description = description;
    }

    public Attraction(String title, String shortDescription, String address){
        this.title = title;
        this.shortDescription = shortDescription;
        this.address = address;
    }
}
