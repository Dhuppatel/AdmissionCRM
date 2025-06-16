package com.admissioncrm.applicationmgmtservice.Services;

import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationFormService {

    // Create
    ApplicationForm createApplication(ApplicationFormSubmissionDTO applicationDto);

    // Read
    ApplicationForm getApplicationById(String id);
    ApplicationForm getApplicationByEmail(String email);
    List<ApplicationForm> getApplicationsByUserId(String userId);
    Page<ApplicationForm> getAllApplications(Pageable pageable);
    Page<ApplicationForm> getApplicationsByCourse(Long instituteCourseId, Pageable pageable);

    // Update
    ApplicationForm updateApplication(String id, ApplicationFormSubmissionDTO applicationDto);
    ApplicationForm partialUpdateApplication(String id, ApplicationFormSubmissionDTO applicationDto);

    // Delete
    void deleteApplication(String id);
    void softDeleteApplication(String id);

    // Search and Filter
    Page<ApplicationForm> searchApplicationsByName(String searchTerm, Pageable pageable);
    List<ApplicationForm> getApplicationsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    // Statistics
    Long getTotalActiveApplications();

    // Business Logic
    boolean isEmailAlreadyRegistered(String email);
    void validateApplicationData(ApplicationFormSubmissionDTO applicationDto);
}