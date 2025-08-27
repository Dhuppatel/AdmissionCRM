package com.admission_crm.lead_management.Payload.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignAdminRequest {
    private String userId;       // ID of the Admin (from Auth Service)
}