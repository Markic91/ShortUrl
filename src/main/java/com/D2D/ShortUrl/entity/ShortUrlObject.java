package com.D2D.ShortUrl.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class ShortUrlObject {

    private String id;
    private String shortId;
    private URL realUrl;
    private String removalToken;
    private LocalDateTime access;

    public ShortUrlObject() {
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

    public LocalDateTime getAccess() {
        return access;
    }

    public void setAccess(LocalDateTime access) {
        this.access = access;
    }
}
