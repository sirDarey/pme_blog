package com.pme.token.utils;

import com.pme.token.Token;
import com.pme.token.repo.TokenRepo;

import java.time.LocalDateTime;
import java.util.Random;

public class Utils {
    public static String generateOTP() {
        Random rnd = new Random();
        Long number = rnd.nextLong(372036854775807L);
        return String.format("%06d", number).substring(0, 6);
    }

    public static Token generateToken (String type, String factor, String OTP) {
        return new Token(
                null,
                type,
                factor,
                encodedOTP(OTP),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5)
        );
    }

    public static String encodedOTP(String OTP) {
        return String.valueOf(new Random(Long.parseLong(OTP)).nextLong());
    }

    public static boolean isOTPValid(String factor, String OTP, TokenRepo tokenRepo) {
        LocalDateTime getExpiryDate = tokenRepo.findByFactorAndOTP(factor, encodedOTP(OTP));
        if (getExpiryDate != null && getExpiryDate.isAfter(LocalDateTime.now()))
            return true;
        return false;
    }
}