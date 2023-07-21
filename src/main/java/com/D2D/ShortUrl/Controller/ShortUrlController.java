package com.D2D.ShortUrl.Controller;

import com.D2D.ShortUrl.Entity.ShortUrl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class ShortUrlController {

    @PostMapping("/links")
    public ShortUrl createUrlObject (@RequestBody String realUrl) throws MalformedURLException {
        ShortUrl ObjectToCreate = new ShortUrl();

        ObjectToCreate.setId("coucou");
        ObjectToCreate.setShortId("recoucou");;
        ObjectToCreate.setRealUrl(new URL(realUrl));

        return ObjectToCreate;
    }
}
