package com.D2D.ShortUrl.Controller;

import com.D2D.ShortUrl.Entity.ShortUrl;
import com.D2D.ShortUrl.Entity.Url;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.UUID;

@RestController
public class ShortUrlController {


    @PostMapping("/links")
    public ShortUrl createUrlObject (@RequestBody URL myNewUrl) throws MalformedURLException {
        ShortUrl ObjectToCreate = new ShortUrl();
        ObjectToCreate.setId(UUID.randomUUID().toString());
        SecureRandom random = new SecureRandom();
        byte seed[] = random.generateSeed(20);
//        byte bytes[] = new byte[20];
//        random.nextBytes(bytes);
        ObjectToCreate.setShortId(seed.toString());
        ObjectToCreate.setRealUrl(new URL(myNewUrl.toString()));

        return ObjectToCreate;
    }
}
