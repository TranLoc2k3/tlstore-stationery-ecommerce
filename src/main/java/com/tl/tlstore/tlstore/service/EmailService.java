package com.tl.tlstore.tlstore.service;

public interface EmailService {
    void sendOtpMessage(String to, String subject);
    String generateOtp();
}
