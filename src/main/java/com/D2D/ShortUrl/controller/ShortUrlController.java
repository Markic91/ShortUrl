package com.D2D.ShortUrl.controller;

import com.D2D.ShortUrl.entity.ShortUrl;
import com.D2D.ShortUrl.entity.Url;
import com.D2D.ShortUrl.repository.UUIDGenerator;
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
    ShortUrl createShortUrl(@RequestBody Url myUrl) throws MalformedURLException {
       ShortUrl ObjectToCreate = new ShortUrl();
       ObjectToCreate.setId(uuidGenerator.getUuid().toString());
       ObjectToCreate.setShortId("xxx");
       String myUrlToString = myUrl.getMyUrl();
       ObjectToCreate.setRealUrl(new URL(myUrlToString));
       return ObjectToCreate;
    }
}
