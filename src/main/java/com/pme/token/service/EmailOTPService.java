package com.pme.token.service;

import com.pme.token.repo.TokenRepo;
import com.pme.token.utils.Utils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailOTPService implements OTPService{
    private final JavaMailSender mailSender;
    private final TokenRepo tokenRepo;

    @Autowired
    public EmailOTPService (JavaMailSender mailSender, TokenRepo tokenRepo) {
        this.mailSender = mailSender;
        this.tokenRepo = tokenRepo;
    }

    @Transactional
    @Override
    public HttpStatusCode sendOTP(String email) {
        String OTP = Utils.generateOTP();
        try {

            sendOTPtoEmail(email, OTP);

        } catch (Exception e) {
            return HttpStatusCode.valueOf(501);
        }

        tokenRepo.save(Utils.generateToken("email", email, OTP));
        return HttpStatusCode.valueOf(201);
    }

    private void sendOTPtoEmail(String email, String otp) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("****SENDER-EMAIL*****", "pme-blog"); //change sender mail
        helper.setTo(email);
        String subject = "One Time Password (OTP)";

        String content = "<p> Welcome to PME Blog, </p>"
                +"<p> Here's your OTP to complete your signup: "+otp+" </p>"
                +"<br>"
                +"<p> Note: OTP expires within 5 minutes";
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
}