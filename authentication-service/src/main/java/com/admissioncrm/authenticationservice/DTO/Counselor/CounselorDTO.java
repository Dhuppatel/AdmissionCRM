package com.admissioncrm.authenticationservice.DTO.Counselor;

import com.admissioncrm.authenticationservice.Entities.CoreEntities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CounselorDTO {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String instituteId;
    private String instituteName;
    private String expertiseArea;
    private boolean isActive;
    private String department;
    private Role role;
    private LocalDateTime lastActive;
    private LocalDateTime joinedDate;
    private String status;
    private long totalLeads;
    private long convertedLeads;

    private long assignedApplications;
    private long completedApplications;
}