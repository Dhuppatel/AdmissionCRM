package com.admission_crm.lead_management.Payload.Request.Leads;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class LandingPageLeadRequest {

    private String firstName;
    private String lastName;

    private String email;
    private String phone;
    private String queryTitle;
    private String queryDescription;
    private String institutionId;
    private String programId;
}
