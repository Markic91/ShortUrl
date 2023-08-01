package com.D2D.ShortUrl.controller;

import com.D2D.ShortUrl.dto.ShortUrlDto;
import com.D2D.ShortUrl.dto.ShortUrlTokenDto;
import com.D2D.ShortUrl.service.ShortIdGenerator;
import com.D2D.ShortUrl.service.ShortUrlMapper;
import com.D2D.ShortUrl.service.VerificationUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.D2D.ShortUrl.repository.SaveFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


@RestController
public class ShortUrlController {


    private final SaveFile savefile;
    private final VerificationUrl verificationUrl;
    private final ShortUrlMapper shortUrlMapper;

    public ShortUrlController(ShortUrlMapper shortUrlMapper, ShortIdGenerator shortIdGenerator, SaveFile saveFile, VerificationUrl verificationUrl) {

        this.savefile = saveFile;
        this.verificationUrl = verificationUrl;
        this.shortUrlMapper = shortUrlMapper;
    }

    @PostMapping("/links")
    public ResponseEntity<?> createUrlObject(@RequestBody URL myNewUrl) throws MalformedURLException, IOException {
        if (!this.verificationUrl.checkUrl(myNewUrl)) {
            return new ResponseEntity<>("invalid url", HttpStatus.BAD_REQUEST);
        }
        ShortUrlDto shortUrl = ShortUrlMapper.toShortUrlDto(myNewUrl);
        ShortUrlTokenDto shortUrlToken = ShortUrlMapper.toShortUrlTokenDto(shortUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.savefile.createFile(new File("C:\\Users\\9101015H\\www\\"), "fileTest", shortUrlToken);
        headers.add("X-Removal-Token", shortUrlToken.getRemovalToken());
        return new ResponseEntity<>(shortUrl, headers, HttpStatus.CREATED);

    }

    @GetMapping("/{shortId}")
    public ModelAndView redirectTo(@PathVariable String shortId) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ShortUrlTokenDto[] shortsUrl = mapper.readValue(new File("C:\\Users\\9101015H\\www\\fileTest.json"), ShortUrlTokenDto[].class);
            for (ShortUrlTokenDto shortUrlTokenDto : shortsUrl) {
                if (shortId.equals(shortUrlTokenDto.getShortId())) {
                    String realUrl = shortUrlTokenDto.getRealUrl().toString();
                    return new ModelAndView("redirect:" + realUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect://www.google.com");
    }

    @DeleteMapping("/links/{id}")
    public ResponseEntity<?> deleteShortUrl(@PathVariable String id, @RequestHeader("X-Removal-Token") String token) {
        File file = new File("C:\\Users\\9101015H\\www\\fileTest.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            ShortUrlTokenDto[] shortsUrl = mapper.readValue(file, ShortUrlTokenDto[].class);
            shortsUrl = Arrays.stream(shortsUrl).filter(item ->
                            ((!item.getId().equals(id)) || (!item.getRemovalToken().equals(token))))
                    .toArray(ShortUrlTokenDto[]::new);
            mapper.writeValue(file, shortsUrl);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
