package com.D2D.ShortUrl.service;

import com.D2D.ShortUrl.entity.ShortUrlObject;
import com.D2D.ShortUrl.repository.SaveFile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
        shortUrlObject.setAccess(LocalDateTime.now());
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
        List<ShortUrlObject> list = savefile.getExistingContent();
        autoDelete();
        for (ShortUrlObject item : list) {
            if (shortId.equals(item.getShortId())) {
                item.setAccess(LocalDateTime.now());
                savefile.write();
                return item.getRealUrl().toString();
            }
        }
        return null;
    }


    public static Boolean deleteOneShortUrl(String id, String token) throws IOException {
        List<ShortUrlObject> list = savefile.getExistingContent();
        for (ShortUrlObject item : list) {
            if ((id.equals(item.getId())) && (token.equals(item.getRemovalToken()))) {
                savefile.deleteContent(item);
                return true;
            }
        }
        return false;
    }

    public static void autoDelete() throws IOException {
        List<ShortUrlObject> list = savefile.getExistingContent();
        LocalDateTime expirationDate = LocalDateTime.now().minusDays(30);
        for (ShortUrlObject item : list) {
            if ((item.getAccess().isBefore(expirationDate))) {
                savefile.deleteContent(item);
                return;
            }
        }
        return;
    }
}