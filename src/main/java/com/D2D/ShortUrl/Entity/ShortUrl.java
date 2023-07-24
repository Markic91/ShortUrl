package com.D2D.ShortUrl.Entity;

import com.fasterxml.jackson.annotation.JsonTypeId;

import java.net.MalformedURLException;
import java.net.URL;
public class ShortUrl {

    private String id;
    private String shortId;
    private URL realUrl;


    public ShortUrl() throws MalformedURLException {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    public URL getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(URL realUrl) {
        this.realUrl = realUrl;
    }
}
