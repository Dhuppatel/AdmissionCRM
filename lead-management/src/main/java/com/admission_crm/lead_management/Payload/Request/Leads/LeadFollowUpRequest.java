package com.admission_crm.lead_management.Payload.Request.Leads;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class LeadFollowUpRequest {

    private String leadId;

    private LocalDateTime followUpDateTime;
    private String type;
    private String priority;
    private String notes;

    private int reminderMinutesBefore;


}
