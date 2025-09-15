package com.admission_crm.lead_management.Payload;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CounsellorDTO {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String instituteId;
    private String instituteName;
    private String expertiseArea;
    private boolean isActive;
    private String department;
//    private Role role;


}