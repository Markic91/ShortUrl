package com.D2D.ShortUrl.dto;


import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class ShortUrlDto {

    private String id;
    private String shortId;
    private URL realUrl;

    public ShortUrlDto() {
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
