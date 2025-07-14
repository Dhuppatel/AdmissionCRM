package com.admissioncrm.applicationmgmtservice.Entities.workflow;

import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm.ApplicationForm;
import com.admissioncrm.applicationmgmtservice.Enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_status_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_form_id", nullable = false)
    private ApplicationForm applicationForm;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status")
    private ApplicationStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private ApplicationStatus newStatus;

    @Column(name = "action_by", nullable = false)
    private String actionBy;

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}