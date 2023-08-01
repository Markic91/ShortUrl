package com.D2D.ShortUrl.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomGenerator {

    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int tokenLength = 40;
    private static final int shortIdLength = 8;

    public static String generateToken() {
        return getString(tokenLength);
    }

    public static String generateShortId() {
        return getString(shortIdLength);

    }

    private static String getString(int shortIdLength) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder shortId = new StringBuilder(shortIdLength);
        for (int i = 0; i < shortIdLength; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            shortId.append(randomChar);
        }
        return shortId.toString();
    }

}
