package com.D2D.ShortUrl.service;

import com.D2D.ShortUrl.entity.ShortUrlObject;
import com.D2D.ShortUrl.repository.SaveFile;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class ShortUrlService {
    private static SaveFile savefile;

    public ShortUrlService(SaveFile saveFile) {
        this.savefile = saveFile;
    }


    public static ShortUrlObject createShortUrlWithToken(URL myNewUrl) {
        ShortUrlObject shortUrlObject = new ShortUrlObject();
        shortUrlObject.setId(UUID.randomUUID().toString());
        shortUrlObject.setShortId(RandomGenerator.generateShortId());
        try {
            shortUrlObject.setRealUrl(new URL(myNewUrl.toString()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        shortUrlObject.setRemovalToken(RandomGenerator.generateToken());
        shortUrlObject.setAccess(LocalDateTime.now());
        savefile.addContent(shortUrlObject);
        return shortUrlObject;
    }

    public static boolean checkUrl(URL myUrl) {
        if ((myUrl.getProtocol().equals("http") || myUrl.getProtocol().equals("https")) && myUrl.getPort() != 8080) {
            return true;
        }
        return false;
    }

    public static String readShortUrl(String shortId) {
        List<ShortUrlObject> list = savefile.getExistingContent();
//        autoDelete();
        for (ShortUrlObject item : list) {
            if (shortId.equals(item.getShortId())) {
                item.setAccess(LocalDateTime.now());
                savefile.write();
                return item.getRealUrl().toString();
            }
        }
        throw new RuntimeException();
    }


    public static int deleteOneShortUrl(String id, String token) {
        List<ShortUrlObject> list = savefile.getExistingContent();
        for (ShortUrlObject item : list) {
            if ((id.equals(item.getId())) && (token.equals(item.getRemovalToken()))) {
                Thread t = new Thread(() -> savefile.deleteContent(item));
                t.start();
                return 204;
            }
            if ((id.equals(item.getId())) && !token.equals(item.getRemovalToken())) {
                return 403;
            }
        }
        return 404;
    }

    @PostConstruct
    public void autoDelete() {
        List<ShortUrlObject> list = savefile.getExistingContent();
        LocalDateTime expirationDate = LocalDateTime.now().minusDays(30);
        List<ShortUrlObject> newList = list.stream().filter(item -> item.getAccess().isAfter(expirationDate)).collect(Collectors.toList());
        savefile.setExistingContent(newList);
        savefile.write();
    }
}
