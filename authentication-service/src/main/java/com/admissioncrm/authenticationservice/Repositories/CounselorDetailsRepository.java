package com.admissioncrm.authenticationservice.Repositories;

import com.admissioncrm.authenticationservice.Entities.CounselorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounselorDetailsRepository extends JpaRepository<CounselorDetails, String> {
    List<CounselorDetails> findByUserIsActiveTrue();

    List<CounselorDetails> findByAssignedInstituteAndUserIsActiveTrue(String institute);
}
