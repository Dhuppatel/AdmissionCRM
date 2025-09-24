package com.admission_crm.lead_management.Payload.Request.Leads.Activity;

import com.admission_crm.lead_management.Entity.LeadManagement.LeadActivity;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import lombok.Data;

@Data
public class LeadActivityRequest {
    private String leadId;
    private String counselorId;
    private ActivityType type;
    private String description;
    private LeadStatus updatedStatus;

    private boolean important; // For notes, indicates if it's marked as important

    private LeadActivity.CallOutcome callOutcome; // INTERESTED, NOT_INTERESTED, etc.

    public enum ActivityType {
        CALL, EMAIL, MEETING, NOTE, FOLLOW_UP_SCHEDULED, STATUS_UPDATE
    }
}
