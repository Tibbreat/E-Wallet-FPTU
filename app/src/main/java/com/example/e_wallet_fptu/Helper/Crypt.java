package com.example.e_wallet_fptu.Helper;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Crypt {
    public static String sha256(String input) {
        try {
            MessageDigest sha
                    = MessageDigest
                    .getInstance(
                            "SHA-256");
            int i = 0;

            byte[] hash = sha.digest(input.getBytes(StandardCharsets.UTF_8));

            // hexHash will contain
            // the Hexadecimal hash
            StringBuilder hexHash
                    = new StringBuilder();

            while (i < hash.length) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexHash.append('0');
                hexHash.append(hex);
                i++;
            }

            return hexHash.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
