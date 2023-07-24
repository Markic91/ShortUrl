package com.D2D.ShortUrl.service;

import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.util.Random;

@Repository
public class ShortIdGenerator {
    private String thisShortID;

    public ShortIdGenerator() {
    }

    public String getThisShortID() {
        final char[] allowedChar= "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        SecureRandom random = new SecureRandom();
        StringBuilder shortId = new StringBuilder();
        for (int i=0; i<8; i++){
            shortId.append(allowedChar[random.nextInt(allowedChar.length)]);
        }
        String thisShortID = shortId.toString();
        return thisShortID;
    }

}
