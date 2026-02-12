package com.admission_crm.lead_management.Controller.Leads;

import com.admission_crm.lead_management.Entity.LeadManagement.LeadActivity;
import com.admission_crm.lead_management.Payload.Request.Leads.Activity.LeadActivityRequest;
import com.admission_crm.lead_management.Payload.Response.ApiResponse;
import com.admission_crm.lead_management.Payload.Response.Leads.Activity.LeadActivityResponse;
import com.admission_crm.lead_management.Service.Leads.Activity.LeadActivityService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads/activities")
@RequiredArgsConstructor
public class LeadActivityController {

    private final LeadActivityService leadActivityService;

    @PostMapping
    public ResponseEntity<?> logActivity(@RequestBody LeadActivityRequest request) {
        LeadActivityResponse response = leadActivityService.logActivity(request);
        return ResponseEntity.ok(ApiResponse.success("Activity logged successfully", response));
    }

    @GetMapping("/{leadId}")
    public ResponseEntity<?> getActivitiesByLead(@PathVariable String leadId) {
        List<LeadActivityResponse> responses = leadActivityService.getActivitiesByLead(leadId);
        return ResponseEntity.ok(ApiResponse.success("Activities fetched successfully", responses));
    }

    @GetMapping("/{leadId}/notes")
    public ResponseEntity<?> getNotesByLead(@PathVariable String leadId) {
        List<LeadActivityResponse> responses = leadActivityService.getNotesByLead(leadId);
        return ResponseEntity.ok(ApiResponse.success("Notes fetched successfully", responses));
    }

}
