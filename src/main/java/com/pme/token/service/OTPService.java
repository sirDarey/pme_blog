package com.pme.token.service;

import com.pme.token.repo.TokenRepo;
import com.pme.token.utils.Utils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public interface OTPService {
    HttpStatusCode sendOTP(String factor);

    default ResponseEntity<String> validateOTP(String factor, String otp, TokenRepo tokenRepo) {
        if (Utils.isOTPValid(factor, otp, tokenRepo))
            return ResponseEntity.status(200).body("OTP Verification Successful");

        return ResponseEntity.status(401).body("Invalid OTP");
    }
}
