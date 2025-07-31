package com.admission_crm.lead_management.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String institutionId;
    private String headOfDepartment;
    private Boolean isActive;
    private List<String> courses = new ArrayList<>();
}
