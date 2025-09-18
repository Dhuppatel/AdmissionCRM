package com.admission_crm.lead_management.Payload.Request.Leads;

import com.admission_crm.lead_management.Entity.FollowUp.LeadFollowUp;
import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadUpdateRequest {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private LocalDate dob;
    private String city;
    private String state;
    private String qualification;
    private LeadStatus status;
    private Lead.LeadPriority priority;
    private String courseInterestId;
    private String queryTitle;
    private String queryDescription;

    private Lead.LeadPriority leadPriority;
}
