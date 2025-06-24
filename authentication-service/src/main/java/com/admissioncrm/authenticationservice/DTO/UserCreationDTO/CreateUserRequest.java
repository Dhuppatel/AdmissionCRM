package com.admissioncrm.authenticationservice.DTO.UserCreationDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String instituteId; //institute or DeptId to assign dept/Institute to the councellor/Admin
}
