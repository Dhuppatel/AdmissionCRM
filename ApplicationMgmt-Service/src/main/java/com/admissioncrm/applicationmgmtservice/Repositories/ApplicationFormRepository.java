package com.admissioncrm.applicationmgmtservice.Repositories;


import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm.ApplicationForm;
import com.admissioncrm.applicationmgmtservice.Enums.ApplicationStatus;
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

    // Find applications within date range
    @Query("SELECT af FROM ApplicationForm af WHERE af.createdAt BETWEEN :startDate AND :endDate AND af.deletedAt IS NULL")
    List<ApplicationForm> findApplicationsBetweenDates(@Param("startDate") LocalDateTime startDate,
                                                       @Param("endDate") LocalDateTime endDate);

//    // Search by name
//    @Query("SELECT af FROM ApplicationForm af WHERE " +
//            "(LOWER(af.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
//            "LOWER(af.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
//            "LOWER(af.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
//            "af.deletedAt IS NULL")
//    Page<ApplicationForm> searchByName(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Count applications by status
    @Query("SELECT COUNT(af) FROM ApplicationForm af WHERE af.deletedAt IS NULL")
    Long countActiveApplications();

    @Query("SELECT COUNT(a) FROM ApplicationForm a WHERE YEAR(a.createdAt) = :year")
    long countByCredatedAtYear(@Param("year") int year);

    boolean existsByReferenceId(String refId);

    Optional<ApplicationForm> findByEmail(String email);

    Optional<ApplicationForm> findByapplicationId(String applicationId);

    boolean existsByStudentMobile(String studentMobile);

    // Alternative approach using native query (might be more reliable)
    @Query(value = "SELECT MAX(CAST(RIGHT(reference_id, 6) AS UNSIGNED)) " +
            "FROM application_forms " +
            "WHERE reference_id LIKE CONCAT('APP-', :year, '-%')",
            nativeQuery = true)
    Long findMaxSequenceForYearNative(@Param("year") int year);

    List<ApplicationForm> findByApplicationStatusOrderByCreatedAt(ApplicationStatus status);

    List<ApplicationForm> findByDeletedAtIsNull();

    Optional<ApplicationForm> findByReferenceId(String referenceId);
}