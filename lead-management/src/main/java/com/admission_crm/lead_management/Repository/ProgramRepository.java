package com.admission_crm.lead_management.Repository;

import com.admission_crm.lead_management.Entity.CoreEntities.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, String> {

    List<Program> findByInstitution_Id(String institutionId);
}
