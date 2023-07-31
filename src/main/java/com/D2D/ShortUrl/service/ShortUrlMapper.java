package com.D2D.ShortUrl.service;

import com.D2D.ShortUrl.dto.ShortUrlDto;
import com.D2D.ShortUrl.dto.ShortUrlTokenDto;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Component
public class ShortUrlMapper {
    private static ShortIdGenerator shortIdGenerator;
    private static TokenGenerator tokenGenerator;


    public ShortUrlMapper(ShortIdGenerator shortIdGenerator, TokenGenerator tokenGenerator) {
        this.shortIdGenerator = shortIdGenerator;
        this.tokenGenerator = tokenGenerator;
    }

    public static ShortUrlDto toShortUrlDto(URL myNewUrl) throws MalformedURLException {
        ShortUrlDto shortUrlDto = new ShortUrlDto();
        shortUrlDto.setId(UUID.randomUUID().toString());
        shortUrlDto.setShortId(shortIdGenerator.getThisShortID());
        shortUrlDto.setRealUrl(new URL(myNewUrl.toString()));
        return shortUrlDto;
    }

    public static ShortUrlTokenDto toShortUrlTokenDto(ShortUrlDto shortUrlDto) throws MalformedURLException {
        ShortUrlTokenDto shortUrlTokenDto = new ShortUrlTokenDto();
        shortUrlTokenDto.setId(shortUrlDto.getId());
        shortUrlTokenDto.setShortId(shortUrlDto.getShortId());
        shortUrlTokenDto.setRealUrl(shortUrlDto.getRealUrl());
        shortUrlTokenDto.setRemovalToken(tokenGenerator.generateToken());
        return shortUrlTokenDto;
    }
}
