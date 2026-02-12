package com.admission_crm.lead_management.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CounsellorLeadStatsDTO {
    private long assignedLeads;
    private long activeLeads;
    private long completedToday;
    private long pendingFollowUp;
}
