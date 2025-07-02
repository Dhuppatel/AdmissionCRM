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

    private String instituteId; //institute or DeptId to assign dept/Institute to the councellor/Admin

    private String ExpertiseArea;
}
