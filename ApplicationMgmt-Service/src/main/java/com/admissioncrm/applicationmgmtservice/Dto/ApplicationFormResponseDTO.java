package com.admissioncrm.applicationmgmtservice.Dto;

import com.admissioncrm.applicationmgmtservice.Enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationFormResponseDTO {
    private String applicationId;
    private String referenceId;
    private String studentName;
    private String email;
    private String courseName; // or applied program
    private LocalDateTime submittedDate;
    private ApplicationStatus status; // "Pending", "Under Review", etc.

    private LocalDateTime updatedDate;

}
