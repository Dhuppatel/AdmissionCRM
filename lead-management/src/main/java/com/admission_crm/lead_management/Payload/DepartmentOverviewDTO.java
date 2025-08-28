package com.admission_crm.lead_management.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class DepartmentOverviewDTO {
    private String id;
    private String name;
    private String code;
    private Long totalStudents;
    private Long activeApplications;
    private Double acceptanceRate;
    private String avgProcessingTime;
    private String status; // "active", "inactive"
    private String headOfDepartment;
}
