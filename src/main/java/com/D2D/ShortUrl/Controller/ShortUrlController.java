package com.D2D.ShortUrl.Controller;

import com.D2D.ShortUrl.Entity.ShortUrl;
import com.D2D.ShortUrl.Entity.Url;
//import com.D2D.ShortUrl.Repository.ShortIdGenerator;
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
//    @Autowired
//    ShortIdGenerator shortIdGenerator;

    @PostMapping("/links")
    public ShortUrl createUrlObject (@RequestBody Url myNewUrl) throws MalformedURLException {
        ShortUrl ObjectToCreate = new ShortUrl();
        ObjectToCreate.setId(uuidGenerator.getUuid().toString());
//        ObjectToCreate.setShortId(shortIdGenerator.shortId());
        ObjectToCreate.setRealUrl(new URL(myNewUrl.getMyUrl().toString()));

        return ObjectToCreate;
    }
}
