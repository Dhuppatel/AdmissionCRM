package com.admissioncrm.applicationmgmtservice.Dto.WorkflowDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationWorkflowRequestDTO {
    @NotBlank(message = "New status is required")
    private String newStatus;

    private String remarks;

    @NotBlank(message = "Action by is required")
    private String actionBy;

    private String assignedTo;
}