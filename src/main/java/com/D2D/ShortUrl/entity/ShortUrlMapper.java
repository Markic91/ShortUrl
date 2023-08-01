package com.D2D.ShortUrl.entity;


import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class ShortUrlMapper {

    private String id;
    private String shortId;
    private URL realUrl;

    public ShortUrlMapper createShortUrlMapper(ShortUrlObject shortUrlObject) {
        ShortUrlMapper mapper = new ShortUrlMapper();
        mapper.id = shortUrlObject.getId();
        mapper.shortId = shortUrlObject.getShortId();
        mapper.realUrl = shortUrlObject.getRealUrl();
        return mapper;
    }

    public String getId() {
        return id;
    }

    public String getShortId() {
        return shortId;
    }

    public URL getRealUrl() {
        return realUrl;
    }
}
