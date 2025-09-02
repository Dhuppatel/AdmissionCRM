package com.admission_crm.lead_management.Repository;

import com.admission_crm.lead_management.Entity.Academic.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    List<Department> findByProgramId(String ProgramId);
    boolean existsByCode(String code);

    List<Department> findByProgram_Id(String programId);

    boolean existsByProgram_IdAndCode(String programId, String code);

    List<Department> findByProgramInstitutionId(String instituteId);



    //this will count unique depts only , if the one dept is there in 2 programs it will count as 1
    @Query("SELECT COUNT(DISTINCT d.code) " +
            "FROM Department d " +
            "WHERE d.program.institution.id = :institutionId")
    long countUniqueDepartmentCodesByInstitution(@Param("institutionId") String institutionId);

}
