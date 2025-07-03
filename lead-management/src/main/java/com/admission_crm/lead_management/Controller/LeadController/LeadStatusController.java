package com.admission_crm.lead_management.Controller.LeadController;

import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import com.admission_crm.lead_management.Exception.LeadNotFoundException;
import com.admission_crm.lead_management.Payload.Response.ApiResponse;
import com.admission_crm.lead_management.Payload.Response.LeadResponse;
import com.admission_crm.lead_management.Service.LeadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/leads/status")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class LeadStatusController {

    private final LeadService leadService;

    @PostMapping("/{leadId}/complete")
    public ResponseEntity<?> completeLead(@PathVariable String leadId,
                                          @RequestParam LeadStatus finalStatus,
                                          @RequestParam(required = false) String notes,
                                          Authentication authentication) {
        try {
            Lead completedLead = leadService.completeLead(leadId, finalStatus, notes, authentication.getName());
            return ResponseEntity.ok(ApiResponse.success("Lead completed successfully", LeadResponse.fromEntity(completedLead)));
        } catch (LeadNotFoundException e) {
            log.warn("Lead not found for completion: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Lead not found", e.getMessage()));
        } catch (IllegalStateException e) {
            log.warn("Invalid lead state for completion: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid lead state", e.getMessage()));
        } catch (Exception e) {
            log.error("Error completing lead: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to complete lead", "An unexpected error occurred"));
        }
    }

    @PostMapping("/{leadId}/follow-up")
    public ResponseEntity<?> scheduleFollowUp(@PathVariable String leadId,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime followUpDate,
                                              @RequestParam(required = false) String notes,
                                              Authentication authentication) {
        try {
            Lead updatedLead = leadService.scheduleFollowUp(leadId, followUpDate, notes, authentication.getName());
            return ResponseEntity.ok(ApiResponse.success("Follow-up scheduled successfully", LeadResponse.fromEntity(updatedLead)));
        } catch (LeadNotFoundException e) {
            log.warn("Lead not found for follow-up scheduling: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Lead not found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error scheduling follow-up: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to schedule follow-up", "An unexpected error occurred"));
        }
    }

    @PutMapping("/{leadId}/change-status")
    public ResponseEntity<?> changeLeadStatus(@PathVariable String leadId,
                                              @RequestParam LeadStatus newStatus,
                                              Authentication authentication) {
        try {
            Lead updatedLead = leadService.changeLeadStatus(leadId, newStatus);
            return ResponseEntity.ok(ApiResponse.success("Lead status changed successfully", LeadResponse.fromEntity(updatedLead)));
        } catch (LeadNotFoundException e) {
            log.warn("Lead not found for status change: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Lead not found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error changing lead status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to change lead status", "An unexpected error occurred"));
        }
    }

    @PutMapping("/{leadId}/priority")
    public ResponseEntity<?> updateLeadPriority(@PathVariable String leadId,
                                                @RequestParam Lead.LeadPriority priority,
                                                Authentication authentication) {
        try {
            Lead updatedLead = leadService.updateLeadPriority(leadId, priority, authentication.getName());
            return ResponseEntity.ok(ApiResponse.success("Lead priority updated successfully", LeadResponse.fromEntity(updatedLead)));
        } catch (LeadNotFoundException e) {
            log.warn("Lead not found for priority update: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Lead not found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error updating lead priority: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update lead priority", "An unexpected error occurred"));
        }
    }
}
