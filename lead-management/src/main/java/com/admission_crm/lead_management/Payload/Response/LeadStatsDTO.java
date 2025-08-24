package com.admission_crm.lead_management.Payload.Response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeadStatsDTO {
    private long totalLeads;
    private long activeLeads;
    private long convertedLeads;
    private long pendingLeads;
}