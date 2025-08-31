package com.admission_crm.lead_management.Payload.Request;

import lombok.Builder;
import lombok.Data;

import java.awt.*;

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
