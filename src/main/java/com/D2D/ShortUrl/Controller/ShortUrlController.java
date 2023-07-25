package com.D2D.ShortUrl.Controller;

import com.D2D.ShortUrl.Entity.ShortUrl;
import com.D2D.ShortUrl.service.ShortIdGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.D2D.ShortUrl.repository.SaveFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@RestController
public class ShortUrlController {


    private final ShortIdGenerator shortIdGenerator;
    private final SaveFile savefile;

//    private final ObjectMapper objectMapper;

    public ShortUrlController(ShortIdGenerator shortIdGenerator, SaveFile saveFile, ObjectMapper objectMapper){
        this.shortIdGenerator = shortIdGenerator;
        this.savefile = saveFile;
//        this.objectMapper = objectMapper;
    }


    @PostMapping("/links")
    public ShortUrl createUrlObject (@RequestBody URL myNewUrl) throws MalformedURLException, IOException {
        ShortUrl objectToCreate = new ShortUrl();
        objectToCreate.setId(UUID.randomUUID().toString());
        objectToCreate.setShortId(shortIdGenerator.getThisShortID());
        objectToCreate.setRealUrl(new URL(myNewUrl.toString()));
        this.savefile.createFile(new File("C:\\Users\\7902872D\\www"), "fileTest", objectToCreate);


        return objectToCreate;
    }

}
