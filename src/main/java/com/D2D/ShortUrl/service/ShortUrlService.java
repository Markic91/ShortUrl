package com.D2D.ShortUrl.service;

import com.D2D.ShortUrl.entity.ShortUrlMapper;
import com.D2D.ShortUrl.entity.ShortUrlObject;
import com.D2D.ShortUrl.repository.SaveFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class ShortUrlService {
    private static SaveFile savefile;


    public ShortUrlService(SaveFile saveFile) {
        this.savefile = saveFile;
    }


    public static ShortUrlObject createShortUrlWithToken(URL myNewUrl) throws IOException {
        ShortUrlObject shortUrlObject = new ShortUrlObject();
        shortUrlObject.setId(UUID.randomUUID().toString());
        shortUrlObject.setShortId(RandomGenerator.generateShortId());
        shortUrlObject.setRealUrl(new URL(myNewUrl.toString()));
        shortUrlObject.setRemovalToken(RandomGenerator.generateToken());
        savefile.addContent(shortUrlObject);
        return shortUrlObject;
    }

    public static boolean checkUrl(URL myUrl) {
        if (myUrl.getProtocol().equals("http") || myUrl.getProtocol().equals("https")) {
            return true;
        }
        return false;
    }

    public static String readShortUrl(String shortId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = savefile.getFilePath();
        ShortUrlObject[] shortsUrl = mapper.readValue(file, ShortUrlObject[].class);
        for (ShortUrlObject shortUrl : shortsUrl) {
            if (shortId.equals(shortUrl.getShortId())) {
                return shortUrl.getRealUrl().toString();
            }
        }
        return null;
    }


    public static Boolean deleteOneShortUrl(String id, String token) {
        ObjectMapper mapper = new ObjectMapper();
        File file = savefile.getFilePath();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            ShortUrlObject[] shortsUrl = mapper.readValue(file, ShortUrlObject[].class);
            shortsUrl = Arrays.stream(shortsUrl).filter(item ->
                            ((!item.getId().equals(id)) || (!item.getRemovalToken().equals(token))))
                    .toArray(ShortUrlObject[]::new);
            mapper.writeValue(file, shortsUrl);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean autoDelete() {
        return false;
    }
}
