package com.D2D.ShortUrl.controller;

import com.D2D.ShortUrl.entity.ShortUrl;
import com.D2D.ShortUrl.service.ShortIdGenerator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.jfr.Event;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.D2D.ShortUrl.repository.SaveFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

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
        this.savefile.createFile(new File("C:\\Users\\7902872D\\www\\"), "fileTest", objectToCreate);
        return objectToCreate;
    }
    @GetMapping("/{shortId}")
    public ModelAndView redirectTo(@PathVariable String shortId) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            ShortUrl[] shortsUrl = mapper.readValue(new File("C:\\Users\\7902872D\\www\\fileTest.json"), ShortUrl[].class);
            for (ShortUrl shortUrl : shortsUrl) {
                if (shortId.equals(shortUrl.getShortId())) {
                    String realUrl = shortUrl.getRealUrl().toString();
                    return new ModelAndView("redirect:" + realUrl);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return new ModelAndView("redirect://www.google.com");
    }
}
