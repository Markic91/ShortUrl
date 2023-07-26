package com.D2D.ShortUrl.entity;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.net.MalformedURLException;
import java.net.URL;
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)

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
