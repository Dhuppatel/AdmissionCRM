package com.admission_crm.lead_management.Utills;

import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Feign.AuthClient;
import com.admission_crm.lead_management.Payload.Response.LeadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeadResponseAssembler {

    private final AuthClient authClient;

    public LeadResponse toResponse(Lead lead) {
        LeadResponse response = LeadResponse.fromEntity(lead);

        // Enrich with counselor name
        if (lead.getAssignedCounselor() != null) {
            try {
                String counselorName = authClient.getCounsellorDetailsById(lead.getAssignedCounselor()).getFullName().split(" ")[0];
                response.setAssignedCounselorName(counselorName);
            } catch (Exception e) {
                // fallback in case counselor service is down
                response.setAssignedCounselorName("Unknown Counselor");
            }
        }

        return response;
    }

    public Page<LeadResponse> toResponsePage(Page<Lead> leads) {
        return leads.map(this::toResponse);
    }
}
