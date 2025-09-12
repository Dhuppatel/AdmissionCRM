package com.admissioncrm.authenticationservice.Entities;

import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CounselorDetails {
    @Id
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private String assignedInstitute;
    private String expertiseArea;

    private long totalLeadsAssigned;
    private long totalLeadsConverted;


    // more counsellor-specific fields
}
