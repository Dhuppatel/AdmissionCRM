package com.admission_crm.lead_management.Controller;

import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import com.admission_crm.lead_management.Service.LeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadService leadService;

    @Autowired
    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public ResponseEntity<Lead> createLead(@RequestBody Lead lead, Authentication authentication) {
        return ResponseEntity.ok(leadService.createLead(lead, authentication.getName()));
    }

    @PostMapping("/assign/{counselorId}")
    public ResponseEntity<Lead> assignLeadToCounselor(@PathVariable String counselorId) {
        Lead assignedLead = leadService.assignLeadToCounselor(counselorId);
        return ResponseEntity.ok(assignedLead);
    }

    @PostMapping("/requeue/{leadId}")
    public ResponseEntity<Void> requeueLead(@PathVariable String leadId, @RequestParam("priority") Lead.Priority priority) {
        leadService.requeueLead(leadId, priority);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{leadId}")
    public ResponseEntity<Lead> getLead(@PathVariable String leadId) {
        Lead lead = leadService.getLead(leadId);
        return ResponseEntity.ok(lead);
    }

    @PatchMapping("/{leadId}/status")
    public ResponseEntity<Lead> changeLeadStatus(@PathVariable String leadId, @RequestParam("status") LeadStatus status) {
        Lead updatedLead = leadService.changeLeadStatus(leadId, status);
        return ResponseEntity.ok(updatedLead);
    }

}
