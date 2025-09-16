package com.admission_crm.lead_management.Controller.Leads;

import com.admission_crm.lead_management.Entity.FollowUp.LeadFollowUp;
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
    public ResponseEntity<LeadFollowUp> createFollowUp(@RequestBody LeadFollowUp followUp) {
        return ResponseEntity.ok(followUpService.scheduleFollowUp(followUp));
    }

    @GetMapping("/lead/{leadId}")
    public ResponseEntity<List<LeadFollowUp>> getFollowUpsByLead(@PathVariable String leadId) {
        return ResponseEntity.ok(followUpService.getFollowUpsByLead(leadId));
    }

    @GetMapping("/assigned/{userId}")
    public ResponseEntity<List<LeadFollowUp>> getFollowUpsAssignedTo(@PathVariable String userId) {
        return ResponseEntity.ok(followUpService.getFollowUpsAssignedTo(userId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<LeadFollowUp> updateFollowUpStatus(
            @PathVariable String id,
            @RequestParam LeadFollowUp.FollowUpStatus status,
            @RequestParam(required = false) String outcome) {
        return ResponseEntity.ok(followUpService.updateFollowUpStatus(id, status, outcome));
    }
}
