package com.admission_crm.lead_management.Service.Leads;

import com.admission_crm.lead_management.Entity.FollowUp.LeadFollowUp;
import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import com.admission_crm.lead_management.Payload.Request.Leads.LeadFollowUpRequest;
import com.admission_crm.lead_management.Payload.Response.LeadResponse;
import com.admission_crm.lead_management.Payload.Response.Leads.LeadFollowUpResponse;
import com.admission_crm.lead_management.Repository.Leads.LeadFollowUpRepository;
import com.admission_crm.lead_management.Repository.Leads.LeadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeadFollowUpService {

    private final LeadFollowUpRepository followUpRepository;
    private final LeadRepository leadRepository; // for updating lead status

    @Transactional
    public LeadFollowUp scheduleFollowUp(LeadFollowUpRequest followUp ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //  When scheduling a follow-up, update Lead status automatically
        Lead lead = leadRepository.findById(followUp.getLeadId())
                .orElseThrow(() -> new RuntimeException("Lead not found"));


            lead.setStatus(LeadStatus.FOLLOW_UP);
            leadRepository.save(lead);

        LeadFollowUp leadFollowUp = new LeadFollowUp();
        leadFollowUp.setLeadId(lead.getId());
        leadFollowUp.setAssignedTo(authentication.getName());
        leadFollowUp.setFollowUpDate(followUp.getFollowUpDateTime());
       leadFollowUp.setNotes(followUp.getNotes());
        leadFollowUp.setStatus(LeadFollowUp.FollowUpStatus.PENDING); // default
        leadFollowUp.setType(followUp.getType() != null ? LeadFollowUp.FollowUpType.valueOf(followUp.getType()) : LeadFollowUp.FollowUpType.CALL);

        leadFollowUp.setPriority(followUp.getPriority() != null ? LeadFollowUp.Priority.valueOf(followUp.getPriority()) : LeadFollowUp.Priority.MEDIUM);
        leadFollowUp.setReminderMinutesBefore(followUp.getReminderMinutesBefore());
        leadFollowUp.setCreatedBy(authentication.getName());

        return followUpRepository.save(leadFollowUp);
    }

    public List<LeadFollowUpResponse> getFollowUpsByLead(String leadId) {
        return followUpRepository.findByLeadId(leadId).stream().map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<LeadFollowUpResponse> getFollowUpsAssignedTo(String userId) {
        return followUpRepository.findByAssignedTo(userId).stream()
                .map(fu ->
                        {
                            LeadFollowUpResponse dto = toDTO(fu);

                            Lead lead = leadRepository.findById(fu.getLeadId()).orElse(null);
                            if (lead != null) {
                                dto.setLead(LeadResponse.fromEntity(lead));
                            }
                            return dto;

                        }
                )
                .collect(Collectors.toList());
    }

    public LeadFollowUpResponse updateFollowUpStatus(String id, LeadFollowUp.FollowUpStatus status, String outcome) {
        LeadFollowUp followUp = followUpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Follow-up not found"));
        followUp.setStatus(status);
        followUp.setOutcome(outcome);
        return toDTO(followUpRepository.save(followUp));
    }


        public LeadFollowUpResponse toDTO(LeadFollowUp followUp) {
            if (followUp == null) {
                return null;
            }

            LeadFollowUpResponse dto = new LeadFollowUpResponse();
            dto.setId(followUp.getId());
            dto.setLeadId(followUp.getLeadId());
            dto.setFollowUpDateTime(followUp.getFollowUpDate());
            dto.setType(followUp.getType().toString());
            dto.setPriority(followUp.getPriority().toString());
            dto.setNotes(followUp.getNotes());
            dto.setReminder(followUp.getReminderMinutesBefore());
            dto.setStatus(followUp.getStatus().toString());
            dto.setCreatedAt(followUp.getCreatedAt());
            dto.setUpdatedAt(followUp.getUpdatedAt());

            return dto;
        }


}
