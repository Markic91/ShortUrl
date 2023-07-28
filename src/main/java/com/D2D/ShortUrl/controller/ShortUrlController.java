package com.D2D.ShortUrl.controller;

import com.D2D.ShortUrl.dto.ShortUrlDto;
import com.D2D.ShortUrl.dto.ShortUrlTokenDto;
import com.D2D.ShortUrl.service.ShortIdGenerator;
import com.D2D.ShortUrl.service.VerificationUrl;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.*;
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
    private final VerificationUrl verificationUrl;
    private final ShortUrlDto shortUrlDto;
    private final ShortUrlTokenDto shortUrlTokenDto;

    public ShortUrlController(ShortIdGenerator shortIdGenerator, SaveFile saveFile, TokenGenerator tokenGenerator, VerificationUrl verificationUrl, ShortUrlDto shortUrlDto, ShortUrlTokenDto shortUrlTokenDto) {

        this.shortIdGenerator = shortIdGenerator;
        this.savefile = saveFile;
        this.tokenGenerator = tokenGenerator;
        this.verificationUrl = verificationUrl;
        this.shortUrlDto = shortUrlDto;
        this.shortUrlTokenDto = shortUrlTokenDto;
    }

    @PostMapping("/links")
    public ResponseEntity<?> createUrlObject(@RequestBody URL myNewUrl) throws MalformedURLException, IOException {
        if (!this.verificationUrl.checkUrl(myNewUrl)) {
            return new ResponseEntity<>("invalid url", HttpStatus.BAD_REQUEST);
        }
        ShortUrlDto shortUtlDto = new ShortUrlDto();
        shortUtlDto.setId(UUID.randomUUID().toString());
        shortUtlDto.setShortId(shortIdGenerator.getThisShortID());
        shortUtlDto.setRealUrl(new URL(myNewUrl.toString()));

        ShortUrlTokenDto shortUrlDtoToken = new ShortUrlTokenDto();
        shortUrlDtoToken.setId(shortUtlDto.getId());
        shortUrlDtoToken.setShortId(shortUtlDto.getShortId());
        shortUrlDtoToken.setRealUrl(shortUtlDto.getRealUrl());
        shortUrlDtoToken.setRemovalToken(this.tokenGenerator.generateToken());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        this.savefile.createFile(new File("C:\\Users\\9101015H\\www\\"), "fileTest", shortUrlDtoToken);
        headers.add("X-Removal-Token", shortUrlDtoToken.getRemovalToken());
        return new ResponseEntity<>(shortUtlDto, headers, HttpStatus.CREATED);

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
}
