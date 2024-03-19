package com.example.e_wallet_fptu.Helper;

import org.mindrot.jbcrypt.BCrypt;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class DataEncode {
    public String hashData(String plainTextPassword) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(plainTextPassword, salt);
    }

    public boolean verifyHash(String string_1, String string_2) {
        return BCrypt.checkpw(string_1, string_2);
    }

    public String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public String generateRandomCode() {
        Random random = new Random();
        return Integer.toString(random.nextInt(9000) + 1000);
    }

    public String formatMoney(int amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")); // Locale for Vietnam
        return currencyFormat.format(amount);
    }

    public String formatMoney(long amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")); // Locale for Vietnam
        return currencyFormat.format(amount);
    }
    public String getTodayDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date());
    }
}
