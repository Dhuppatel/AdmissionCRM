package com.admissioncrm.applicationmgmtservice.Repositories.Workflow;

import com.admissioncrm.applicationmgmtservice.Entities.workflow.ApplicationStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationStatusHistoryRepository extends JpaRepository<ApplicationStatusHistory, String> {
}
