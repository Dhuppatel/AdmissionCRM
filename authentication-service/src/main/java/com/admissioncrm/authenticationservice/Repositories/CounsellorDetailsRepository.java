package com.admissioncrm.authenticationservice.Repositories;

import com.admissioncrm.authenticationservice.Entities.CounsellorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounsellorDetailsRepository extends JpaRepository<CounsellorDetails, String> {
    List<CounsellorDetails> findByUserIsActiveTrue();

    List<CounsellorDetails> findByAssignedInstituteAndUserIsActiveTrue(String institute);
}
