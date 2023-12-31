package com.D2D.ShortUrl.controller;

import com.D2D.ShortUrl.entity.ShortUrlMapper;
import com.D2D.ShortUrl.entity.ShortUrlObject;

import com.D2D.ShortUrl.service.ShortUrlService;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URL;


@RestController
public class ShortUrlController {
    private final ShortUrlMapper shortUrlMapper;

    public ShortUrlController(ShortUrlMapper shortUrlMapper) {

        this.shortUrlMapper = shortUrlMapper;
    }

    @PostMapping("/links")
    public ResponseEntity<?> createUrlObject(@RequestBody URL myNewUrl) {
        var verificationUrl = ShortUrlService.checkUrl(myNewUrl);
        if (!verificationUrl) {
            return new ResponseEntity<>("invalid url", HttpStatus.BAD_REQUEST);
        }
        ShortUrlObject shortUrlObject = ShortUrlService.createShortUrlWithToken(myNewUrl);
        ShortUrlMapper mapper = shortUrlMapper.createShortUrlMapper(shortUrlObject);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Removal-Token", shortUrlObject.getRemovalToken());
        return new ResponseEntity<>(mapper, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{shortId}")
    public ModelAndView redirectTo(@PathVariable String shortId) {
        return new ModelAndView("redirect:" + ShortUrlService.readShortUrl(shortId));
    }

    @DeleteMapping("/links/{id}")
    public ResponseEntity<?> deleteShortUrl(@PathVariable String id, @RequestHeader("X-Removal-Token") String token) {
        if ((ShortUrlService.deleteOneShortUrl(id, token)) == 403) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if ((ShortUrlService.deleteOneShortUrl(id, token)) == 204) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if ((ShortUrlService.deleteOneShortUrl(id, token)) == 404) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        throw new RuntimeException();
    }
}
