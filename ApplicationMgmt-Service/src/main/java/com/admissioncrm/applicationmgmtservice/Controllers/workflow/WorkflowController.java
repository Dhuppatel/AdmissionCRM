package com.admissioncrm.applicationmgmtservice.Controllers.workflow;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormSummaryDTO;
import com.admissioncrm.applicationmgmtservice.Dto.WorkflowDTO.ApplicationWorkflowRequestDTO;
import com.admissioncrm.applicationmgmtservice.Dto.WorkflowDTO.ApplicationWorkflowResponseDTO;
import com.admissioncrm.applicationmgmtservice.Enums.ApplicationStatus;
import com.admissioncrm.applicationmgmtservice.Services.workflow.WorkflowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;


    @PostMapping("/{applicationId}/process")
    public ResponseEntity<ApplicationWorkflowResponseDTO> processWorkflow(
            @PathVariable String applicationId,
            @Valid @RequestBody ApplicationWorkflowRequestDTO request) {
        ApplicationWorkflowResponseDTO response = workflowService.processApplicationWorkflow(applicationId, request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/stage/{status}")
    public ResponseEntity<List<ApplicationFormSummaryDTO>> getApplicationsByStage(
            @PathVariable String status) {
        ApplicationStatus applicationStatus = ApplicationStatus.valueOf(status.toUpperCase());
        List<ApplicationFormSummaryDTO> applications = workflowService.getApplicationsByWorkflowStage(applicationStatus);
        return ResponseEntity.ok(applications);
    }



}
