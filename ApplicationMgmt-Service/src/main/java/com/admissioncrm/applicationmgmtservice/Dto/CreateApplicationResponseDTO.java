package com.admissioncrm.applicationmgmtservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateApplicationResponseDTO {
    private String id;
    private String referenceId;
    private String studentId;
    private String email;

    private String instituteId;
    private String instituteName;

    private String programId;
    private String programName;

    private String status;
    private Integer progress;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime submittedAt;
    private LocalDateTime reviewedAt;
    private LocalDateTime completedAt;
}
