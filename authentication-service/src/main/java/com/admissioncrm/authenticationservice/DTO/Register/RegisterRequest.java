package com.admissioncrm.authenticationservice.DTO.Register;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian mobile number")
    private String mobileNumber;
    private String email;
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
}
