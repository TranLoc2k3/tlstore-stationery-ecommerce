package com.tl.tlstore.tlstore.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class OtpStore {
    private static class OtpDetails {
        String otp;
        LocalDateTime expirationTime;
        public OtpDetails(String otp, LocalDateTime expirationTime) {
            this.otp = otp;
            this.expirationTime = expirationTime;
        }
    }
    private static final Map<String, OtpDetails> otpMap = new HashMap<>();
    private static int time = 0;

    public static void storeOtp(String email, String otp) {
        LocalDateTime expirationMinustes = LocalDateTime.now().plusMinutes(5);
        OtpDetails otpDetails = new OtpDetails(otp, expirationMinustes);
        otpMap.put(email, otpDetails);
    }

    public static String getOtp(String email) {
        OtpDetails otpDetails = otpMap.get(email);
        if (otpDetails != null) {
            return otpDetails.otp;
        }
        return null;
    }

    public static void removeOtp(String email) {
        otpMap.remove(email);
    }

    @Scheduled(fixedRate = 5 * 60 * 1000) // 5 mins
    public static void removeExpiredOtp() {
        otpMap.forEach((k, v) -> {
            System.out.println("Time : " + time + " Key: " + k + " Value: " + v.otp);
            time++;
        });
        LocalDateTime currentTime = LocalDateTime.now();
        otpMap.entrySet().removeIf(entry -> currentTime.isAfter(entry.getValue().expirationTime));
    }
}
