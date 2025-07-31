package com.admissioncrm.authenticationservice.DTO.UserCreationDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateUserRequest {

    private String fullName;

    private String username;

    private String email;

    private String password;

    private String instituteId;

    private String ExpertiseArea;

    private String mobileNumber;
}
