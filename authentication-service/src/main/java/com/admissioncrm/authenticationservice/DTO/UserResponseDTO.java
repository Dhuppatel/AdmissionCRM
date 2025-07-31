package com.admissioncrm.authenticationservice.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private String id;
    private String fullName;
    private String username;
    private String email;
    private String instituteId;
    private String expertiseArea;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}
