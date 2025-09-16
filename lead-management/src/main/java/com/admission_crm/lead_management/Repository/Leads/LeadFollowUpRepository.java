package com.admission_crm.lead_management.Repository.Leads;

import com.admission_crm.lead_management.Entity.FollowUp.LeadFollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadFollowUpRepository extends JpaRepository<LeadFollowUp, String> {
    List<LeadFollowUp> findByLeadId(String leadId);
    List<LeadFollowUp> findByAssignedTo(String userId);
}
