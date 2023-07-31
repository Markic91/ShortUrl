package com.D2D.ShortUrl.controller;

import com.D2D.ShortUrl.dto.ShortUrlDto;
import com.D2D.ShortUrl.dto.ShortUrlTokenDto;

import com.D2D.ShortUrl.service.ShortUrlMapper;
import com.D2D.ShortUrl.service.VerificationUrl;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.URL;
import java.util.*;


@RestController
public class ShortUrlController {
    private final VerificationUrl verificationUrl;
    private final File filePath;


    public ShortUrlController(VerificationUrl verificationUrl, @Value("${file.path}") File filePath) {
        this.verificationUrl = verificationUrl;
        this.filePath = filePath;
    }

    @PostMapping("/links")
    public ResponseEntity<?> createUrlObject(@RequestBody URL myNewUrl) throws IOException {
        if (!this.verificationUrl.checkUrl(myNewUrl)) {
            return new ResponseEntity<>("invalid url", HttpStatus.BAD_REQUEST);
        }
        ShortUrlDto shortUrl = ShortUrlMapper.toShortUrlDto(myNewUrl);
        ShortUrlTokenDto shortUrlToken = ShortUrlMapper.toShortUrlTokenDto(shortUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Removal-Token", shortUrlToken.getRemovalToken());
        return new ResponseEntity<>(shortUrl, headers, HttpStatus.CREATED);

    }

    @GetMapping("/{shortId}")
    public ModelAndView redirectTo(@PathVariable String shortId) {
        return new ModelAndView("redirect:" + ShortUrlMapper.readShortUrl(shortId));
    }

    @DeleteMapping("/links/{id}")
    public ResponseEntity<?> deleteShortUrl(@PathVariable String id, @RequestHeader("X-Removal-Token") String token) {
        File file = new File(filePath.toURI());
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
