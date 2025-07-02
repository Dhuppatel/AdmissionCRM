package com.admissioncrm.authenticationservice.DTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String mobileNumber;
    private String email;
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
}
