package com.admission_crm.lead_management.Entity.Academic;


import com.admission_crm.lead_management.Entity.CoreEntities.Institution;
import com.admission_crm.lead_management.Entity.CoreEntities.Program;
import com.admission_crm.lead_management.Entity.CoreEntities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"program_id", "name"}),
                @UniqueConstraint(columnNames = {"program_id", "code"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;


    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String code;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Fees and Duration move here ⬇
    @Column(length = 50)
    private String duration;   // e.g., 4 years

    @Column(precision = 10, scale = 2)
    private BigDecimal fees;   // program fee

    private Integer intakeCapacity;  // e.g., 120 seats


    private String headOfDepartment;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
