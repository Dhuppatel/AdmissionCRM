package com.admission_crm.lead_management.Payload.Request;

import com.admission_crm.lead_management.Entity.LeadManagement.LeadSource;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeadRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String gender;
    private String country;
    private String city;
    private String state;
    private String qualification;
    private String institutionId;
    private String departmentId;

    private LeadSource leadSource;
    private LeadStatus status;
    private String queryTitle;
    private String queryDescription;

}
