package com.admissioncrm.applicationmgmtservice.Dto.WorkflowDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationWorkflowResponseDTO {
    private String referenceId;
    private String previousStatus;
    private String currentStatus;
    private String actionBy;
    private LocalDateTime actionDate;
    private String remarks;
    private List<String> nextPossibleActions;
}