package com.admissioncrm.authenticationservice.Controllers;

import com.admissioncrm.authenticationservice.DTO.ApiResponse;
import com.admissioncrm.authenticationservice.Services.Otp.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/sms/send")
    public ResponseEntity<?> sendSmsOtp(@RequestParam String phoneNumber) {

        if (!isValidPhoneNumber(phoneNumber)) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Phone number must be 10 digits and start with 6-9.","Invalid phone number" , HttpStatus.BAD_REQUEST.value()));

        }

        String otp = otpService.sendSmsOtp(phoneNumber);
        return ResponseEntity.ok("OTP sent successfully to " + phoneNumber + ". Please check your SMS.");
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

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^[6-9]\\d{9}$");
    }
}
