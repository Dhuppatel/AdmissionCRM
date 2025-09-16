package com.admission_crm.lead_management.Service.Leads;

import com.admission_crm.lead_management.Entity.FollowUp.LeadFollowUp;
import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import com.admission_crm.lead_management.Repository.Leads.LeadFollowUpRepository;
import com.admission_crm.lead_management.Repository.Leads.LeadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeadFollowUpService {

    private final LeadFollowUpRepository followUpRepository;
    private final LeadRepository leadRepository; // for updating lead status

    @Transactional
    public LeadFollowUp scheduleFollowUp(LeadFollowUp followUp) {
        //  When scheduling a follow-up, update Lead status automatically
        Lead lead = leadRepository.findById(followUp.getLeadId())
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        // Move from CONTACTED → FOLLOW_UP if needed
        if (LeadStatus.FOLLOW_UP.equals(lead.getStatus())) {
            lead.setStatus(LeadStatus.FOLLOW_UP);
            leadRepository.save(lead);
        }

        followUp.setStatus(LeadFollowUp.FollowUpStatus.PENDING); // default
        return followUpRepository.save(followUp);
    }

    public List<LeadFollowUp> getFollowUpsByLead(String leadId) {
        return followUpRepository.findByLeadId(leadId);
    }

    public List<LeadFollowUp> getFollowUpsAssignedTo(String userId) {
        return followUpRepository.findByAssignedTo(userId);
    }

    public LeadFollowUp updateFollowUpStatus(String id, LeadFollowUp.FollowUpStatus status, String outcome) {
        LeadFollowUp followUp = followUpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Follow-up not found"));
        followUp.setStatus(status);
        followUp.setOutcome(outcome);
        return followUpRepository.save(followUp);
    }
}
