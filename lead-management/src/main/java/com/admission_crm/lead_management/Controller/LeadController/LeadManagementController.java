package com.admission_crm.lead_management.Controller.LeadController;

import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import com.admission_crm.lead_management.Exception.InvalidLeadDataException;
import com.admission_crm.lead_management.Exception.LeadNotFoundException;
import com.admission_crm.lead_management.Payload.Request.LeadRequest;
import com.admission_crm.lead_management.Payload.Request.LeadUpdateRequest;
import com.admission_crm.lead_management.Payload.Response.ApiResponse;
import com.admission_crm.lead_management.Payload.Response.LeadResponse;
import com.admission_crm.lead_management.Service.LeadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leads/management")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class LeadManagementController {

    private final LeadService leadService;

    @PostMapping
    public ResponseEntity<?> createLead(@Valid @RequestBody LeadRequest leadRequest, Authentication authentication) {
        try {
            Lead createdLead = leadService.createLead(leadRequest, authentication.getName());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Lead created successfully", LeadResponse.fromEntity(createdLead)));
        } catch (InvalidLeadDataException e) {
            log.warn("Invalid lead data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid lead data", e.getMessage()));
        } catch (Exception e) {
            log.error("Error creating lead: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create lead", "An unexpected error occurred"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLeadById(@PathVariable String id) {
        try {
            Lead lead = leadService.getLeadById(id);
            return ResponseEntity.ok(ApiResponse.success("Lead retrieved successfully", LeadResponse.fromEntity(lead)));
        } catch (LeadNotFoundException e) {
            log.warn("Lead not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Lead not found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error retrieving lead: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve lead", "An unexpected error occurred"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllLeads(
            Pageable pageable,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String institutionId,
            @RequestParam(required = false) LeadStatus status) {
        try {
            Page<Lead> leads = leadService.getLeadsByFilter(searchTerm, institutionId, status, pageable);
            Page<LeadResponse> leadResponses = leads.map(LeadResponse::fromEntity);
            return ResponseEntity.ok(ApiResponse.success("Leads retrieved successfully", leadResponses));
        } catch (Exception e) {
            log.error("Error retrieving leads: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve leads", "An unexpected error occurred"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLead(@PathVariable String id,
                                        @Valid @RequestBody LeadUpdateRequest updateRequest,
                                        Authentication authentication) {
        try {
            Lead updatedLead = leadService.updateLead(id, updateRequest, authentication.getName());
            return ResponseEntity.ok(ApiResponse.success("Lead updated successfully", LeadResponse.fromEntity(updatedLead)));
        } catch (LeadNotFoundException e) {
            log.warn("Lead not found for update: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Lead not found", e.getMessage()));
        } catch (InvalidLeadDataException e) {
            log.warn("Invalid lead update data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid lead data", e.getMessage()));
        } catch (Exception e) {
            log.error("Error updating lead: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update lead", "An unexpected error occurred"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLead(@PathVariable String id, Authentication authentication) {
        try {
            leadService.deleteLead(id, authentication.getName());
            return ResponseEntity.ok(ApiResponse.success("Lead deleted successfully", null));
        } catch (LeadNotFoundException e) {
            log.warn("Lead not found for deletion: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Lead not found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error deleting lead: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete lead", "An unexpected error occurred"));
        }
    }
}