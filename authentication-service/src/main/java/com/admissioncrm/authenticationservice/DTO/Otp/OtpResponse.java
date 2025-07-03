package com.admissioncrm.authenticationservice.DTO.Otp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OtpResponse {
    private String message;
    private boolean success;
    private String identifier;
}