package com.example.apod.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class APOD implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private String date;

    @SerializedName("title")
    private String title;

    @SerializedName("explanation")
    private String explanation;

    @SerializedName("url")
    private String url;

    @SerializedName("media_type")
    private String media_type;

    public APOD(){}

    public APOD(String date, String title, String explanation, String url, String media_type){
        this.id = 0;
        this.date = date;
        this.title = title;
        this.explanation = explanation;
        this.url = url;
        this.media_type = media_type;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() { return explanation; }

    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia_type() { return media_type; }

    public void setMedia_type(String media_type) { this.media_type = media_type; }
}
