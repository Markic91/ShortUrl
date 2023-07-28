package com.D2D.ShortUrl.service;

import com.D2D.ShortUrl.dto.ShortUrlDto;
import com.D2D.ShortUrl.dto.ShortUrlTokenDto;

public class ShortUrlMapper {

    public static ShortUrlDto toShortUrlDto(ShortUrlDto shortUrl) {
        ShortUrlDto shortUrlDto = new ShortUrlDto();
        shortUrlDto.setId(shortUrl.getId());
        shortUrlDto.setShortId(shortUrl.getShortId());
        shortUrlDto.setRealUrl(shortUrl.getRealUrl());
        return shortUrlDto;
    }

    public static ShortUrlTokenDto toShortUrlTokenDto(ShortUrlTokenDto shortUrl, String removalToken) {
        ShortUrlTokenDto ShortUrlTokenDto = new ShortUrlTokenDto();
        ShortUrlTokenDto.setId(shortUrl.getId());
        ShortUrlTokenDto.setShortId(shortUrl.getShortId());
        ShortUrlTokenDto.setRealUrl(shortUrl.getRealUrl());
        ShortUrlTokenDto.setRemovalToken(removalToken);
        return ShortUrlTokenDto;
    }
}
