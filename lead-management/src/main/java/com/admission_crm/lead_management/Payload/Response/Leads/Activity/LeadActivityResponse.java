package com.admission_crm.lead_management.Payload.Response.Leads.Activity;


import com.admission_crm.lead_management.Entity.LeadManagement.LeadActivity;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LeadActivityResponse {
    private String id;
    private String leadId;
    private String counselorId;
    private String description;
    private LeadActivity.ActivityType activityType;
    private LeadStatus updatedStatus;
    private LeadActivity.CallOutcome callOutcome;
    private LocalDateTime createdAt;


    private boolean important;// For notes, indicates if it's marked as important

    public static LeadActivityResponse fromEntity(LeadActivity activity) {
        return LeadActivityResponse.builder()
                .id(activity.getId())
                .leadId(activity.getLead().getId())
                .counselorId(activity.getUserId())
                .description(activity.getDescription())
                .activityType(activity.getActivityType())
                .updatedStatus(activity.getUpdatedStatus())
                .callOutcome(activity.getCallOutcome())
                .createdAt(activity.getCreatedAt())
                .important(activity.isImportant())
                .build();
    }
}
