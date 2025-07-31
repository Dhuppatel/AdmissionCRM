package com.admission_crm.lead_management.Payload.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateRequest {
    private String name;
    private String code;
    private String description;
    private String duration;
    private String institutionId;
    private String departmentId;
    private Boolean isActive = true;
    private BigDecimal fees;
}