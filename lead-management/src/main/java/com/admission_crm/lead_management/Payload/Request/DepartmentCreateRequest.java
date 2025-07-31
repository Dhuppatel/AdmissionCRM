package com.admission_crm.lead_management.Payload.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCreateRequest {
    private String name;
    private String code;
    private String institutionId;
    private String headOfDepartment;
    private Boolean isActive = true;
}
