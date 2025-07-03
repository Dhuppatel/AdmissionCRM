package com.admissioncrm.authenticationservice.DTO.Register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Builder
@AllArgsConstructor
@Data
public class RegisterResponse {
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String mobileNumber;

}
