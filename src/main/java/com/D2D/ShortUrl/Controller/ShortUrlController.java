package com.D2D.ShortUrl.Controller;

import com.D2D.ShortUrl.Entity.ShortUrl;
import com.D2D.ShortUrl.service.ShortIdGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.D2D.ShortUrl.repository.SaveFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.UUID;

@RestController
public class ShortUrlController {


    private final ShortIdGenerator shortIdGenerator;
    private final SaveFile savefile;

    public ShortUrlController(ShortIdGenerator shortIdGenerator, SaveFile saveFile){
        this.shortIdGenerator = shortIdGenerator;
        this.savefile = saveFile;
    }


    @PostMapping("/links")
    public ShortUrl createUrlObject (@RequestBody URL myNewUrl) throws MalformedURLException, IOException {
        ShortUrl ObjectToCreate = new ShortUrl();
        ObjectToCreate.setId(UUID.randomUUID().toString());
        ObjectToCreate.setShortId(shortIdGenerator.getThisShortID());
        ObjectToCreate.setRealUrl(new URL(myNewUrl.toString()));
        this.savefile.createFile(new File("C:\\Users\\9101015H\\www"), "coucou" );
        return ObjectToCreate;
    }

}
