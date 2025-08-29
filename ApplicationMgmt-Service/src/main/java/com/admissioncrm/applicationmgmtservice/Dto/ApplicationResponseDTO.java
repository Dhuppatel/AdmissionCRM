package com.admissioncrm.applicationmgmtservice.Dto;

import com.admissioncrm.applicationmgmtservice.Enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDTO {
    private boolean success;
    private String message;
    private String applicationId;
    private String referenceId;
    private ApplicationStatus status;
    private List<String> errors;
    private Map<String, String> documentUrls;
}
