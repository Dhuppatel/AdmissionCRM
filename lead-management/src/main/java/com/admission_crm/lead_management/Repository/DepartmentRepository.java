package com.admission_crm.lead_management.Repository;

import com.admission_crm.lead_management.Entity.Academic.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    List<Department> findByProgramId(String ProgramId);
    boolean existsByCode(String code);

    List<Department> findByProgram_Id(String programId);

    boolean existsByProgram_IdAndCode(String programId, String code);
}
