package com.D2D.ShortUrl.controller;

import com.D2D.ShortUrl.entity.ShortUrl;
import com.D2D.ShortUrl.service.ShortIdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.D2D.ShortUrl.repository.SaveFile;
import org.springframework.web.servlet.ModelAndView;
import com.D2D.ShortUrl.service.TokenGenerator;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


@RestController
public class ShortUrlController {


    private final ShortIdGenerator shortIdGenerator;
    private final SaveFile savefile;
    private final TokenGenerator tokenGenerator;

    public ShortUrlController(ShortIdGenerator shortIdGenerator, SaveFile saveFile, TokenGenerator tokenGenerator) {

        this.shortIdGenerator = shortIdGenerator;
        this.savefile = saveFile;
        this.tokenGenerator = tokenGenerator;
    }

    @PostMapping("/links")
    public ResponseEntity<?> createUrlObject(@RequestBody URL myNewUrl) throws MalformedURLException, IOException {
        ShortUrl objectToCreate = new ShortUrl();
        objectToCreate.setId(UUID.randomUUID().toString());
        objectToCreate.setShortId(shortIdGenerator.getThisShortID());
        objectToCreate.setRealUrl(new URL(myNewUrl.toString()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Removal-Token", this.tokenGenerator.generateToken());
        this.savefile.createFile(new File("C:\\Users\\9101015H\\www\\"), "fileTest", objectToCreate);
        return new ResponseEntity<>(objectToCreate, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{shortId}")
    public ModelAndView redirectTo(@PathVariable String shortId) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            ShortUrl[] shortsUrl = mapper.readValue(new File("C:\\Users\\9101015H\\www\\fileTest.json"), ShortUrl[].class);
            for (ShortUrl shortUrl : shortsUrl) {
                if (shortId.equals(shortUrl.getShortId())) {
                    String realUrl = shortUrl.getRealUrl().toString();
                    return new ModelAndView("redirect:" + realUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect://www.google.com");
    }
}
