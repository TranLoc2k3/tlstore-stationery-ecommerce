package com.tl.tlstore.tlstore.service.impl;

import com.tl.tlstore.tlstore.service.EmailService;
import com.tl.tlstore.tlstore.util.OtpStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendOtpMessage(String to, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tranloc120603@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        String otp = generateOtp();
        message.setText("Your OTP is: " +  otp);
        mailSender.send(message);
        OtpStore.storeOtp(to, otp);
    }

    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate 6 digit OTP
        return String.valueOf(otp);
    }
}
