package com.D2D.ShortUrl.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class TokenGenerator {

    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int tokenLength = 40;

    public static String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder(tokenLength);

        for (int i = 0; i < tokenLength; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
