package com.admission_crm.lead_management.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private String id;
    private String name;
    private String code;
    private String description;
    private String programId;
    private String headOfDepartment;

    private String duration; // e.g., 4 years
    private BigDecimal fees; // program fee
    private Integer intakeCapacity;// e.g., 120 seats

    private Boolean isActive;
}
