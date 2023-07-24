//package com.D2D.ShortUrl.Repository;
//
//import org.springframework.stereotype.Repository;
//
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.util.Random;
//
//@Repository
//public class ShortIdGenerator(int length) {
//    final char[] chrs = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
//
//    private Random secureRandom = new SecureRandom();
//
//    private StringBuilder shortId = new StringBuilder();
//    for (int i = 0; i < length; i++){
//        shortId.append(chrs[secureRandom.nextInt(chrs.length)]);
//    }
//
//    public ShortIdGenerator() {
//    }
//
//    public char[] getChrs() {
//        return chrs;
//    }
//
//    public SecureRandom getSecureRandom() {
//        return secureRandom;
//    }
//
//    public void setSecureRandom(SecureRandom secureRandom) {
//        this.secureRandom = secureRandom;
//    }
//
//    public StringBuilder getShortId() {
//        return shortId;
//    }
//
//    public void setShortId(StringBuilder shortId) {
//        this.shortId = shortId;
//    }
//
//    public int getI() {
//        return i;
//    }
//
//    public void setI(int i) {
//        this.i = i;
//    }
//}
