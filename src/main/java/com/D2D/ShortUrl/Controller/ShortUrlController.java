package com.D2D.ShortUrl.Controller;

import com.D2D.ShortUrl.Entity.ShortUrl;
import com.D2D.ShortUrl.service.ShortIdGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.UUID;

@RestController
public class ShortUrlController {

    @Autowired
    ShortIdGenerator shortIdGenerator;


    @PostMapping("/links")
    public ShortUrl createUrlObject (@RequestBody URL myNewUrl) throws MalformedURLException {
        ShortUrl ObjectToCreate = new ShortUrl();
        ObjectToCreate.setId(UUID.randomUUID().toString());
        ObjectToCreate.setShortId(shortIdGenerator.getThisShortID());
        ObjectToCreate.setRealUrl(new URL(myNewUrl.toString()));
        return ObjectToCreate;
    }
}
