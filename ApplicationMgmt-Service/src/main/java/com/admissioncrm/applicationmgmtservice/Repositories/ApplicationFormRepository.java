package com.admissioncrm.applicationmgmtservice.Repositories;


import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, String> {

    // Find by user ID
    List<ApplicationForm> findByIdUserAndDeletedAtIsNull(String idUser);

    // Find by email
    Optional<ApplicationForm> findByEmailAndDeletedAtIsNull(String email);

    // Find by institute course ID
    Page<ApplicationForm> findByInstituteCourseIdAndDeletedAtIsNull(Long instituteCourseId, Pageable pageable);

    // Find applications within date range
    @Query("SELECT af FROM ApplicationForm af WHERE af.createdAt BETWEEN :startDate AND :endDate AND af.deletedAt IS NULL")
    List<ApplicationForm> findApplicationsBetweenDates(@Param("startDate") LocalDateTime startDate,
                                                       @Param("endDate") LocalDateTime endDate);

    // Search by name
    @Query("SELECT af FROM ApplicationForm af WHERE " +
            "(LOWER(af.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(af.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(af.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
            "af.deletedAt IS NULL")
    Page<ApplicationForm> searchByName(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Count applications by status
    @Query("SELECT COUNT(af) FROM ApplicationForm af WHERE af.deletedAt IS NULL")
    Long countActiveApplications();

    // Find by multiple criteria
    @Query("SELECT af FROM ApplicationForm af WHERE " +
            "(:instituteCourseId IS NULL OR af.instituteCourseId = :instituteCourseId) AND " +
            "(:email IS NULL OR af.email = :email) AND " +
            "(:mobile IS NULL OR af.studentMobile = :mobile) AND " +
            "af.deletedAt IS NULL")
    Page<ApplicationForm> findByCriteria(@Param("instituteCourseId") Long instituteCourseId,
                                         @Param("email") String email,
                                         @Param("mobile") String mobile,
                                         Pageable pageable);
}