package com.admission_crm.lead_management.Repository;

import com.admission_crm.lead_management.Entity.CoreEntities.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}
