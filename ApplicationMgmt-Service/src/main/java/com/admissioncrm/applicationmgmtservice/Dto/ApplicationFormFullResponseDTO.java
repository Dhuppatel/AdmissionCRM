package com.admissioncrm.applicationmgmtservice.Dto;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.ApplicationFormSubmissionDTO;
import com.admissioncrm.applicationmgmtservice.Enums.ApplicationStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class ApplicationFormFullResponseDTO {

   //meta-data
    String applicationId;
    LocalDateTime submittedDate;
    ApplicationStatus status;
    boolean isEditable;

    //Re-used the SubmmissionDTO to reduce boilerplate code
    ApplicationFormSubmissionDTO formData;
}
