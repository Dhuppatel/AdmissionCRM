package com.admissioncrm.applicationmgmtservice.Dto;

import com.admissioncrm.applicationmgmtservice.Enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplicationDTO {
    String programId;
    String instituteId;
    String applicantId;

    String email;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    ApplicationStatus status;
    String referenceId;


}
