package com.admissioncrm.authenticationservice.DTO.Counselor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CounselorDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String assignedInstitute;
    private String expertiseArea;
    private boolean isActive;
    private String department;


}