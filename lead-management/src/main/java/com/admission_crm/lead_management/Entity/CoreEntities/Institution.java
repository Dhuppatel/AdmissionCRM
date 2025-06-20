package com.admission_crm.lead_management.Entity.CoreEntities;

import com.admission_crm.lead_management.Entity.Academic.Course;
import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Entity
@Table(name = "institutions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, length = 10, name = "institute_code")
    private String instituteCode;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 15)
    private String phone;

    @Column(length = 100)
    private String email;

    private String website;

    @Column(name = "logo_url")
    private String logoUrl;

    private String universityId;

    @ElementCollection
    private List<String> instituteAdmin = new ArrayList<>();

    @Column(name = "max_counselors")
    private Integer maxCounselors = 5;

    @Column(name = "current_counselors")
    private Integer currentCounselors = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ElementCollection
    private List<String> counselors = new ArrayList<>();

    @ElementCollection
    private List<String> courses = new ArrayList<>();

    @ElementCollection
    private List<String> leads = new ArrayList<>();

    @ElementCollection
    private Deque<String> queuedLeads = new ArrayDeque<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
