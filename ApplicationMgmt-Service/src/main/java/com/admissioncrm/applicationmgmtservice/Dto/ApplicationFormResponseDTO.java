package com.admissioncrm.applicationmgmtservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationFormResponseDTO {
    private String applicationId;
    private String studentName;
    private String email;
    private String courseName; // or applied program
    private LocalDate submittedDate;
    private String status; // "Pending", "Under Review", etc.



}
