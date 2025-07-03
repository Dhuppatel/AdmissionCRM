package com.admissioncrm.authenticationservice.Controllers;

import com.admissioncrm.authenticationservice.Services.Otp.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/sms/send")
    public String sendSmsOtp(@RequestParam String phoneNumber) {

        String otp = otpService.sendSmsOtp(phoneNumber);
        return "OTP sent successfully to " + phoneNumber + ". Please check your SMS.";
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String identifier, @RequestParam String otp) {
        boolean isValid = otpService.verifyOtp(identifier, otp);
        if (isValid) {
            return "true";
        } else {
            return "Invalid OTP. Please try again......";
        }
    }
    @PostMapping("/email/send")
    public String sendEmailOtp(@RequestParam String email) {
        String otp = otpService.sendEmailOtp(email);
        return "OTP sent successfully to " + email + ". Please check your SMS.";
    }
}
