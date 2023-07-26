package com.D2D.ShortUrl.controller;

import com.D2D.ShortUrl.entity.ShortUrl;
import com.D2D.ShortUrl.service.ShortIdGenerator;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.D2D.ShortUrl.repository.SaveFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
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
        ShortUrl objectToCreate = new ShortUrl();
        objectToCreate.setId(UUID.randomUUID().toString());
        objectToCreate.setShortId(shortIdGenerator.getThisShortID());
        objectToCreate.setRealUrl(new URL(myNewUrl.toString()));
        ArrayList array = new ArrayList();
        array.add(objectToCreate);
        this.savefile.createFile(new File("C:\\Users\\9101015H\\www"), "fileTest",objectToCreate);
        Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));
        return objectToCreate;
    }

    @GetMapping("/{shortId}")
    public ModelAndView redirectTo(@PathVariable String shortId) {
        return new ModelAndView("redirect://www.amazon.com");
    }
}
