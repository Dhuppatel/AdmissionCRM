package com.admissioncrm.authenticationservice.DTO;

import com.admissioncrm.authenticationservice.Entities.CoreEntities.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InstituteAdminDTO {
    private String id;
    private String fullName;
    private String username;
    private String email;
    private String instituteId;
    private String instituteName;
    private String expertiseArea;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

}