package com.admission_crm.lead_management.Controller.LeadController;

import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Payload.Response.ApiResponse;
import com.admission_crm.lead_management.Payload.Response.LeadResponse;
import com.admission_crm.lead_management.Service.LeadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leads/institution")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class InstitutionLeadController {

    private final LeadService leadService;

    @GetMapping("/{institutionId}")
    public ResponseEntity<?> getLeadsByInstitution(@PathVariable String institutionId, Pageable pageable) {
        try {
            Page<Lead> leads = leadService.getLeadsByInstitution(institutionId, pageable);
            Page<LeadResponse> leadResponses = leads.map(LeadResponse::fromEntity);
            return ResponseEntity.ok(ApiResponse.success("Institution leads retrieved successfully", leadResponses));
        } catch (Exception e) {
            log.error("Error retrieving institution leads: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve institution leads", "An unexpected error occurred"));
        }
    }
}
