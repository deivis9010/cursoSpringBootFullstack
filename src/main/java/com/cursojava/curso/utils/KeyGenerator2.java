package com.cursojava.curso.utils;

import java.util.Base64;
import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class KeyGenerator2 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Generate a 256-bit key for HS256
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256, new SecureRandom()); // 256 bits for HS256
        byte[] secretBytes = keyGen.generateKey().getEncoded();
        String base64UrlSecret = Base64.getUrlEncoder().withoutPadding().encodeToString(secretBytes);
        System.out.println("Generated Base64Url Secret: " + base64UrlSecret);
    }
}