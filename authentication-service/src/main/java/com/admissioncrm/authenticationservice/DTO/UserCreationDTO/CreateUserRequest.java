package com.admissioncrm.authenticationservice.DTO.UserCreationDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank(message = "Full name is required")
    @Pattern(regexp = "^(\\S+\\s+\\S+.*)$", message = "Full name must contain at least first and last name")
    private String fullName;


    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String instituteId; //institute or DeptId to assign dept/Institute to the councellor/Admin

    private String ExpertiseArea;
}
