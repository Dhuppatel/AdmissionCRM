package com.admission_crm.lead_management.Entity.LeadManagement;

import com.admission_crm.lead_management.Entity.CoreEntities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
// Entity to track activities related to a lead
//this entity will be used to log all the actions done by the counsellor on a lead
@Entity
@Table(name = "lead_activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "lead_id")
    private Lead lead;

    private String userId;

    @Column(name = "activity_type", nullable = false, length = 50)
    private ActivityType activityType; //call , email, meeting, note, follow-up scheduled, status update

    @Column(columnDefinition = "TEXT")
    private String description;// Details about the activity

    @Enumerated(EnumType.STRING)
    private LeadStatus updatedStatus; //Optional==> New status of the lead after the activity, if applicable


    //only for call activity
    @Enumerated(EnumType.STRING)
    private CallOutcome callOutcome;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum ActivityType {
        CALL, EMAIL, MEETING, NOTE, FOLLOW_UP_SCHEDULED, STATUS_UPDATE
    }

    public enum CallOutcome {
        INTERESTED,
        NOT_INTERESTED,
        NO_ANSWER,
        WRONG_NUMBER,
        CALLBACK_REQUESTED
    }

}