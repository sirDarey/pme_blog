package com.pme.token.service;

import com.pme.token.repo.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TokenService {
    private final OTPService emailOTPService;
    @Autowired
    public TokenService(OTPService emailOTPService) {
        this.emailOTPService = emailOTPService;
    }

    public HttpStatusCode sendOTP (String factor) {
        HttpStatusCode sendOTPResponse = emailOTPService.sendOTP(factor);
        return sendOTPResponse;
    }

    public ResponseEntity<String> validateOTP(String factor, String otp, TokenRepo tokenRepo) {
        return emailOTPService.validateOTP(factor, otp, tokenRepo);
    }
}
