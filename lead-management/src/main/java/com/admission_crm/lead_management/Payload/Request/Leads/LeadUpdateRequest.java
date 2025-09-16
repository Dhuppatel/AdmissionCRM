package com.admission_crm.lead_management.Payload.Request.Leads;

import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadUpdateRequest {

    private String firstName;
    private String lastName;
    private String phone;
    private String city;
    private String state;
    private String qualification;
    private LeadStatus status;
    private Lead.LeadPriority priority;
    private String courseInterestId;
    private String queryTitle;
    private String queryDescription;
}
