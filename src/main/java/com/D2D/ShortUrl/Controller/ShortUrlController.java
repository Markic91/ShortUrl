package com.D2D.ShortUrl.Controller;

import com.D2D.ShortUrl.Entity.ShortUrl;
import com.D2D.ShortUrl.Entity.Url;
import com.D2D.ShortUrl.Repository.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class ShortUrlController {
    @Autowired
    UUIDGenerator uuidGenerator;


    @PostMapping("/links")
    public ShortUrl createUrlObject (@RequestBody Url url) throws MalformedURLException {
        ShortUrl ObjectToCreate = new ShortUrl();

        ObjectToCreate.setId(uuidGenerator.getUuid().toString());
        ObjectToCreate.setShortId("recoucou");
        ObjectToCreate.setRealUrl(new URL(url.getMyUrl().toString()));

        return ObjectToCreate;
    }
}
