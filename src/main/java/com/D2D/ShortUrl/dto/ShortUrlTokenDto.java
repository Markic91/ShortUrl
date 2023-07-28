package com.D2D.ShortUrl.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class ShortUrlTokenDto {

    private String id;
    private String shortId;
    private URL realUrl;
    private String removalToken;

    public ShortUrlTokenDto() {
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

    public String getRemovalToken() {
        return removalToken;
    }

    public void setRemovalToken(String removalToken) {
        this.removalToken = removalToken;
    }
}
