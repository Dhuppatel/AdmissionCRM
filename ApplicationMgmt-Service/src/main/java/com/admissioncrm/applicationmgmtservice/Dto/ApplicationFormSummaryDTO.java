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
public class ApplicationFormSummaryDTO {
    private String referenceId;
    private String studentFullName;
    private String email;
    private String courseAppliedFor; // or applied program
    private LocalDateTime submittedDate;
    private ApplicationStatus status; // "Pending", "Under Review", etc.
    private long daysSinceSubmission;
    private LocalDateTime updatedDate;

}
