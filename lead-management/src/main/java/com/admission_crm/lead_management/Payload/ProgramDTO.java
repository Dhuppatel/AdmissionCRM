package com.admission_crm.lead_management.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProgramDTO {
    private String id;             // UUID of Program
    private String name;           // e.g., B.Tech, M.Tech
    private String code;           // e.g., BTECH01, MTECH01
    private String description;    // Optional program details
    private String institutionId;      // Parent Faculty ID
    private Boolean isActive = true;
}
