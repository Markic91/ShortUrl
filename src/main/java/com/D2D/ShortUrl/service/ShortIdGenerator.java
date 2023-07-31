package com.D2D.ShortUrl.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ShortIdGenerator {

    public ShortIdGenerator() {
    }

    public static String getThisShortID() {
        final char[] allowedChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        SecureRandom random = new SecureRandom();
        StringBuilder shortId = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            shortId.append(allowedChar[random.nextInt(allowedChar.length)]);
        }
        return shortId.toString();

    }

}
