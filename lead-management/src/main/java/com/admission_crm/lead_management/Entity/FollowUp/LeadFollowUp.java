package com.admission_crm.lead_management.Entity.FollowUp;

import com.admission_crm.lead_management.Entity.CoreEntities.User;
import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "lead_follow_ups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadFollowUp {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "lead_id")
    private String leadId;

    private String assignedTo;

    private LocalDateTime followUpDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FollowUpType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FollowUpStatus status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private Integer reminderMinutesBefore;

    @Column(columnDefinition = "TEXT")
    private String outcome;

    private LocalDateTime nextFollowUpDate;

    private String createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum FollowUpType {
        PHONE_CALL,
        EMAIL,
        VIDEO_CALL,
        IN_PERSON_MEETING
    }

    public enum FollowUpStatus {
        PENDING, COMPLETED, CANCELLED, POSTPONED
    }

    public enum Priority {
        LOW, MEDIUM, HIGH, URGENT
    }
}