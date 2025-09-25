package com.admission_crm.lead_management.Repository.Leads.Activity;

import com.admission_crm.lead_management.Entity.LeadManagement.LeadActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LeadActivityRepository extends JpaRepository<LeadActivity, String> {
    List<LeadActivity> findByLeadIdOrderByCreatedAtDesc(String leadId);

    List<LeadActivity> findByLeadIdAndActivityTypeOrderByCreatedAtDesc(
            String leadId,
            LeadActivity.ActivityType activityType
    );

    @Query("SELECT MAX(a.createdAt) FROM LeadActivity a WHERE a.lead.id = :leadId")
    LocalDateTime findLastActivityDateByLeadId(@Param("leadId") String leadId);
}
