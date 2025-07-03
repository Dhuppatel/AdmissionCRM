package com.admission_crm.lead_management.Controller.LeadController;

import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Exception.CounselorNotFoundException;
import com.admission_crm.lead_management.Exception.CounselorUnavailableException;
import com.admission_crm.lead_management.Exception.LeadNotFoundException;
import com.admission_crm.lead_management.Payload.Request.BulkAssignRequest;
import com.admission_crm.lead_management.Payload.Response.ApiResponse;
import com.admission_crm.lead_management.Payload.Response.LeadResponse;
import com.admission_crm.lead_management.Service.LeadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leads/assignment")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class LeadAssignmentController {

    private final LeadService leadService;

    @PostMapping("/{leadId}/assign/{counselorId}")
    public ResponseEntity<?> assignLeadToCounselor(@PathVariable String leadId,
                                                   @PathVariable String counselorId,
                                                   Authentication authentication) {
        try {
            Lead assignedLead = leadService.assignLeadToCounselor(leadId, counselorId, authentication.getName());
            return ResponseEntity.ok(ApiResponse.success("Lead assigned successfully", LeadResponse.fromEntity(assignedLead)));
        } catch (LeadNotFoundException e) {
            log.warn("Lead not found for assignment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Lead not found", e.getMessage()));
        } catch (CounselorNotFoundException e) {
            log.warn("Counselor not found for assignment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Counselor not found", e.getMessage()));
        } catch (CounselorUnavailableException e) {
            log.warn("Counselor unavailable for assignment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error("Counselor unavailable", e.getMessage()));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid assignment request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid assignment", e.getMessage()));
        } catch (Exception e) {
            log.error("Error assigning lead: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to assign lead", "An unexpected error occurred"));
        }
    }

    @PostMapping("/bulk-assign")
    public ResponseEntity<?> bulkAssignLeads(@RequestBody BulkAssignRequest request,
                                             Authentication authentication) {
        try {
            List<Lead> assignedLeads = leadService.bulkAssignLeads(
                    request.getLeadIds(),
                    request.getCounselorId(),
                    authentication.getName()
            );
            List<LeadResponse> leadResponses = assignedLeads.stream()
                    .map(LeadResponse::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success("Leads assigned successfully", leadResponses));
        } catch (CounselorNotFoundException e) {
            log.warn("Counselor not found for bulk assignment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Counselor not found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error in bulk assignment: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to assign leads", "An unexpected error occurred"));
        }
    }

    @PostMapping("/{leadId}/transfer")
    public ResponseEntity<?> transferLead(@PathVariable String leadId,
                                          @RequestParam String fromCounselorId,
                                          @RequestParam String toCounselorId,
                                          @RequestParam(required = false) String reason,
                                          Authentication authentication) {
        try {
            Lead transferredLead = leadService.transferLead(leadId, fromCounselorId, toCounselorId, reason, authentication.getName());
            return ResponseEntity.ok(ApiResponse.success("Lead transferred successfully", LeadResponse.fromEntity(transferredLead)));
        } catch (LeadNotFoundException e) {
            log.warn("Lead not found for transfer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Lead not found", e.getMessage()));
        } catch (CounselorNotFoundException e) {
            log.warn("Counselor not found for transfer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Counselor not found", e.getMessage()));
        } catch (CounselorUnavailableException e) {
            log.warn("Target counselor unavailable: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error("Counselor unavailable", e.getMessage()));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid transfer request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid transfer", e.getMessage()));
        } catch (Exception e) {
            log.error("Error transferring lead: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to transfer lead", "An unexpected error occurred"));
        }
    }

    @PostMapping("/queue/next")
    public ResponseEntity<?> getNextLeadForCounselor(@RequestParam String counselorId,
                                                     @RequestParam String institutionId,
                                                     Authentication authentication) {
        try {
            Lead nextLead = leadService.getNextLeadForCounselor(counselorId, institutionId, authentication.getName());
            if (nextLead == null) {
                return ResponseEntity.ok(ApiResponse.success("No leads available in queue", null));
            }
            return ResponseEntity.ok(ApiResponse.success("Next lead assigned", LeadResponse.fromEntity(nextLead)));
        } catch (CounselorUnavailableException e) {
            log.warn("Counselor unavailable for next lead: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error("Counselor unavailable", e.getMessage()));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid counselor for institution: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid request", e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting next lead: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to get next lead", "An unexpected error occurred"));
        }
    }
}