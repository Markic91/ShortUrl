package com.D2D.ShortUrl.Controller;

import com.D2D.ShortUrl.Entity.ShortUrl;
import com.D2D.ShortUrl.Entity.Url;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@RestController
public class ShortUrlController {


    @PostMapping("/links")
    public ShortUrl createUrlObject (@RequestBody Url myNewUrl) throws MalformedURLException {
        ShortUrl ObjectToCreate = new ShortUrl();
        ObjectToCreate.setId(UUID.randomUUID().toString());
        ObjectToCreate.setShortId("coucou");
        ObjectToCreate.setRealUrl(new URL(myNewUrl.getMyUrl().toString()));

        return ObjectToCreate;
    }
}
