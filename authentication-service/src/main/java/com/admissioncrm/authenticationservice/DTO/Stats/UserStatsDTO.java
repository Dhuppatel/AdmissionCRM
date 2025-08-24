package com.admissioncrm.authenticationservice.DTO.Stats;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStatsDTO {
    private long totalCounselors;
    private long totalStudents;
    private long totalAdmins;
    private long totalInstituteAdmins;
    private long activeCounselors;
    private long activeStudents;
}
