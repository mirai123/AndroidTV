package com.example.sunke.myapplicationtv;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Movie implements Serializable {

    private static final  String TAG = Movie.class.getSimpleName();
    private static final long serialVersionUID = 8645502599457857667L;

    private long id;
    private String title;
    private String studio;
    private String cardImageUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getCardImageUrl() {
        return cardImageUrl;
    }

    public void setCardImageUrl(String cardImageUrl) {
        this.cardImageUrl = cardImageUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", studio='" + studio + '\'' +
                '}';
    }

    public Movie(long id, String title, String studio) {
        this.id = id;
        this.title = title;
        this.studio = studio;
    }

    public Movie() {
    }

    //将URL字符串转化为URI格式
    public URI getCardImageURI(){
        try {
            URI uri = new URI(getCardImageUrl());
            return uri;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
