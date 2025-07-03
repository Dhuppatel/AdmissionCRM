package com.admission_crm.lead_management.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeadStatistics {
    private Long totalLeads;
    private Long newLeads;
    private Long queuedLeads;
    private Long assignedLeads;
    private Long convertedLeads;
    private Long rejectedLeads;
    private Double conversionRate;
    private Double averageLeadScore;
    private Double averageProcessingTime; // in hours
    private Map<String, Long> leadsBySource;
    private Map<String, Long> leadsByPriority;
    private Map<String, Long> leadsByStatus;
    private LocalDateTime calculatedAt;
}
