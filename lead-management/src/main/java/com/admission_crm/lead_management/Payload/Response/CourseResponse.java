package com.admission_crm.lead_management.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private String id;
    private String name;
    private String code;
    private String description;
    private String duration;
    private BigDecimal fees;
    private String institutionId;
    private String departmentId;
    private Boolean isActive;
    private List<String> interestedLeads;
    private List<String> applications;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}