package com.D2D.ShortUrl.service;

import com.D2D.ShortUrl.dto.ShortUrlDto;
import com.D2D.ShortUrl.dto.ShortUrlTokenDto;
import com.D2D.ShortUrl.repository.SaveFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Component
public class ShortUrlMapper {
    private static SaveFile savefile;


    public ShortUrlMapper(SaveFile saveFile) {
        this.savefile = saveFile;
    }


    public static ShortUrlDto toShortUrlDto(URL myNewUrl) throws MalformedURLException {
        ShortUrlDto shortUrlDto = new ShortUrlDto();
        shortUrlDto.setId(UUID.randomUUID().toString());
        shortUrlDto.setShortId(ShortIdGenerator.getThisShortID());
        shortUrlDto.setRealUrl(new URL(myNewUrl.toString()));
        return shortUrlDto;
    }

    public static ShortUrlTokenDto toShortUrlTokenDto(ShortUrlDto shortUrlDto) throws IOException {
        ShortUrlTokenDto shortUrlTokenDto = new ShortUrlTokenDto();
        shortUrlTokenDto.setId(shortUrlDto.getId());
        shortUrlTokenDto.setShortId(shortUrlDto.getShortId());
        shortUrlTokenDto.setRealUrl(shortUrlDto.getRealUrl());
        shortUrlTokenDto.setRemovalToken(TokenGenerator.generateToken());
        savefile.createFile(shortUrlTokenDto);
        return shortUrlTokenDto;
    }

    public static String readShortUrl(String shortId) {
        ObjectMapper mapper = new ObjectMapper();
        File file = savefile.getFilePath();
        try {
            ShortUrlTokenDto[] shortsUrl = mapper.readValue(file, ShortUrlTokenDto[].class);
            for (ShortUrlTokenDto shortUrlTokenDto : shortsUrl) {
                if (shortId.equals(shortUrlTokenDto.getShortId())) {
                    return shortUrlTokenDto.getRealUrl().toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "x";
    }
//    public ResponseEntity<?> deleteShortUrl( String id,  String token) {
//        ObjectMapper mapper = new ObjectMapper();
//        File file = savefile.getFilePath();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        try {
//            ShortUrlTokenDto[] shortsUrl = mapper.readValue(file, ShortUrlTokenDto[].class);
//            shortsUrl = Arrays.stream(shortsUrl).filter(item ->
//                            ((!item.getId().equals(id)) || (!item.getRemovalToken().equals(token))))
//                    .toArray(ShortUrlTokenDto[]::new);
//            mapper.writeValue(file, shortsUrl);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
