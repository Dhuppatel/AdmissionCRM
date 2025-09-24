package com.admission_crm.lead_management.Service.Leads.Activity;

import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadActivity;
import com.admission_crm.lead_management.Feign.AuthClient;
import com.admission_crm.lead_management.Payload.Request.Leads.Activity.LeadActivityRequest;
import com.admission_crm.lead_management.Payload.Response.Leads.Activity.LeadActivityResponse;
import com.admission_crm.lead_management.Repository.Leads.Activity.LeadActivityRepository;
import com.admission_crm.lead_management.Repository.Leads.LeadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeadActivityService {

    private final LeadRepository leadRepository;
    private final LeadActivityRepository leadActivityRepository;
    private final AuthClient authClient;

    @Transactional
    public LeadActivityResponse logActivity(LeadActivityRequest request) {
        Lead lead = leadRepository.findById(request.getLeadId())
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        LeadActivity activity = new LeadActivity();
        activity.setLead(lead);
        activity.setImportant(request.isImportant());
        activity.setUserId(request.getCounselorId());
        activity.setActivityType(LeadActivity.ActivityType.valueOf(request.getType().name()));
        activity.setDescription(request.getDescription());
        activity.setUpdatedStatus(request.getUpdatedStatus());

        if (request.getType() == LeadActivityRequest.ActivityType.CALL) {
            activity.setCallOutcome(request.getCallOutcome());
        }

        if (request.getUpdatedStatus() != null) {
            lead.setStatus(request.getUpdatedStatus());
            leadRepository.save(lead);
        }

        LeadActivity saved = leadActivityRepository.save(activity);

        String counselorName = fetchCounselorName(saved.getUserId());

        return LeadActivityResponse.fromEntity(saved, counselorName);
    }

    public List<LeadActivityResponse> getActivitiesByLead(String leadId) {
        return leadActivityRepository.findByLeadIdOrderByCreatedAtDesc(leadId)
                .stream()
                .map(activity -> LeadActivityResponse.fromEntity(activity, fetchCounselorName(activity.getUserId())))
                .toList();
    }

    public List<LeadActivityResponse> getNotesByLead(String leadId) {
        return leadActivityRepository
                .findByLeadIdAndActivityTypeOrderByCreatedAtDesc(leadId, LeadActivity.ActivityType.NOTE)
                .stream()
                .map(activity -> LeadActivityResponse.fromEntity(activity, fetchCounselorName(activity.getUserId())))
                .toList();
    }

    private String fetchCounselorName(String counselorId) {
        try {
            return authClient.getCounsellorDetailsById(counselorId).getFullName();
        } catch (Exception e) {
            System.err.println("Failed to fetch counselor: " + e.getMessage());
            return "Unknown";
        }
    }
}
