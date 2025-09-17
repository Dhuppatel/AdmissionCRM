package com.admission_crm.lead_management.Controller.Leads;

import com.admission_crm.lead_management.Entity.FollowUp.LeadFollowUp;
import com.admission_crm.lead_management.Payload.Request.Leads.LeadFollowUpRequest;
import com.admission_crm.lead_management.Payload.Response.Leads.LeadFollowUpResponse;
import com.admission_crm.lead_management.Service.Leads.LeadFollowUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads/follow-ups")
@RequiredArgsConstructor
public class LeadFollowUpController {

    private final LeadFollowUpService followUpService;

    @PostMapping
    public ResponseEntity<LeadFollowUp> createFollowUp(@RequestBody LeadFollowUpRequest followUp) {
        return ResponseEntity.ok(followUpService.scheduleFollowUp(followUp));
    }

    @GetMapping("/lead/{leadId}")
    public ResponseEntity<List<LeadFollowUpResponse>> getFollowUpsByLead(@PathVariable String leadId) {
        return ResponseEntity.ok(followUpService.getFollowUpsByLead(leadId));
    }

    @GetMapping("/assigned/{counsellorId}")
    public ResponseEntity<List<LeadFollowUpResponse>> getFollowUpsAssignedTo(@PathVariable String counsellorId) {
        return ResponseEntity.ok(followUpService.getFollowUpsAssignedTo(counsellorId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<LeadFollowUpResponse> updateFollowUpStatus(
            @PathVariable String id,
            @RequestParam LeadFollowUp.FollowUpStatus status,
            @RequestParam(required = false) String outcome) {
        return ResponseEntity.ok(followUpService.updateFollowUpStatus(id, status, outcome));
    }
}
