package com.admission_crm.lead_management.Payload.Request;

import lombok.Data;

import java.util.List;

@Data
public class AutoAssignRequest {
    private List<String> leadIds;
    private String strategy; // ROUND_ROBIN / PRIORITY_BASED / AVAILABILITY_BASED
    private String institutionId;
}
