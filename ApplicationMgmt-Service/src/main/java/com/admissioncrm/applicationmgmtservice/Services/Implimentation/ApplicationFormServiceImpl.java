package com.admissioncrm.applicationmgmtservice.Services.Implimentation;


import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.*;
import com.admissioncrm.applicationmgmtservice.Exception.*;
import com.admissioncrm.applicationmgmtservice.Repositories.ApplicationFormRepository;
import com.admissioncrm.applicationmgmtservice.Services.ApplicationFormService;
import com.admissioncrm.applicationmgmtservice.Utills.ApplicationFormMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ApplicationFormServiceImpl implements ApplicationFormService {



    // Complete the incomplete Exception Handeling




    private final ApplicationFormRepository applicationFormRepository;
    private final ApplicationFormMapper applicationFormMapper;

    @Override
    public ApplicationForm createApplication(ApplicationFormSubmissionDTO applicationDto) {
        log.info("Creating new application for user: {}", applicationDto.getIdUser());

        // Validate application data
        validateApplicationData(applicationDto);

        // Check for duplicate email
        if (isEmailAlreadyRegistered(applicationDto.getPersonalInfo().getEmail())) {
            throw new DuplicateApplicationException(
                    "Application with email " + applicationDto.getPersonalInfo().getEmail() + " already exists");
        }

        try {
            // Map DTO to Entity
            ApplicationForm applicationForm = applicationFormMapper.mapToEntity(applicationDto);

            // Save the application
            ApplicationForm savedApplication = applicationFormRepository.save(applicationForm);

            log.info("Application created successfully with ID: {}", savedApplication.getApplicationFormId());
            return savedApplication;

        } catch (Exception e) {
            log.error("Error creating application for user: {}", applicationDto.getIdUser(), e);
            throw new RuntimeException("Failed to create application", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationForm getApplicationById(String id) {
        log.info("Fetching application with ID: {}", id);

        return applicationFormRepository.findById(id)
                .filter(app -> app.getDeletedAt() == null)
                .orElseThrow(() -> new ApplicationFormNotFoundException(
                        "Application not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationForm getApplicationByEmail(String email) {
        log.info("Fetching application with email: {}", email);

        return applicationFormRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new ApplicationFormNotFoundException(
                        "Application not found with email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationForm> getApplicationsByUserId(String userId) {
        log.info("Fetching applications for user ID: {}", userId);

        return applicationFormRepository.findByIdUserAndDeletedAtIsNull(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationForm> getAllApplications(Pageable pageable) {
        log.info("Fetching all applications with pagination");

        return applicationFormRepository.findAll(pageable)
                .map(app -> app.getDeletedAt() == null ? app : null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationForm> getApplicationsByCourse(Long instituteCourseId, Pageable pageable) {
        log.info("Fetching applications for course ID: {}", instituteCourseId);

        return applicationFormRepository.findByInstituteCourseIdAndDeletedAtIsNull(instituteCourseId, pageable);
    }

    @Override
    public ApplicationForm updateApplication(String id, ApplicationFormSubmissionDTO applicationDto) {
        log.info("Updating application with ID: {}", id);

        // Get existing application
        ApplicationForm existingApplication = getApplicationById(id);

        // Validate new data
        validateApplicationData(applicationDto);

        // Check email uniqueness (if email is being changed)
        if (!existingApplication.getEmail().equals(applicationDto.getPersonalInfo().getEmail()) &&
                isEmailAlreadyRegistered(applicationDto.getPersonalInfo().getEmail())) {
            throw new DuplicateApplicationException(
                    "Application with email " + applicationDto.getPersonalInfo().getEmail() + " already exists");
        }

        try {
            // Map new data to existing entity
            ApplicationForm updatedApplication = applicationFormMapper.mapToEntity(applicationDto);
            updatedApplication.setApplicationFormId(existingApplication.getApplicationFormId());
            updatedApplication.setCreatedAt(existingApplication.getCreatedAt());

            ApplicationForm savedApplication = applicationFormRepository.save(updatedApplication);

            log.info("Application updated successfully with ID: {}", savedApplication.getApplicationFormId());
            return savedApplication;

        } catch (Exception e) {
            log.error("Error updating application with ID: {}", id, e);
            throw new RuntimeException("Failed to update application", e);
        }
    }

    @Override
    public ApplicationForm partialUpdateApplication(String id, ApplicationFormSubmissionDTO applicationDto) {
        log.info("Partially updating application with ID: {}", id);

        ApplicationForm existingApplication = getApplicationById(id);

        // Here you would implement partial update logic
        // For simplicity, we'll do a full update
        return updateApplication(id, applicationDto);
    }

    @Override
    public void deleteApplication(String id) {
        log.info("Hard deleting application with ID: {}", id);

        ApplicationForm application = getApplicationById(id);
        applicationFormRepository.delete(application);

        log.info("Application deleted successfully with ID: {}", id);
    }

    @Override
    public void softDeleteApplication(String id) {
        log.info("Soft deleting application with ID: {}", id);

        ApplicationForm application = getApplicationById(id);
        application.setDeletedAt(LocalDateTime.now());
        applicationFormRepository.save(application);

        log.info("Application soft deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicationForm> searchApplicationsByName(String searchTerm, Pageable pageable) {
        log.info("Searching applications by name: {}", searchTerm);

        if (!StringUtils.hasText(searchTerm)) {
            return getAllApplications(pageable);
        }

        return applicationFormRepository.searchByName(searchTerm.trim(), pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ApplicationForm> getApplicationsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching applications between dates: {} and {}", startDate, endDate);

        return applicationFormRepository.findApplicationsBetweenDates(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTotalActiveApplications() {
        return applicationFormRepository.countActiveApplications();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmailAlreadyRegistered(String email) {
        return applicationFormRepository.findByEmailAndDeletedAtIsNull(email).isPresent();
    }

    @Override
    public void validateApplicationData(ApplicationFormSubmissionDTO applicationDto) {
        if (applicationDto == null) {
            throw new InvalidApplicationDataException("Application data cannot be null");
        }

        if (!StringUtils.hasText(applicationDto.getIdUser())) {
            throw new InvalidApplicationDataException("User ID is required");
        }

        if (applicationDto.getPersonalInfo() == null) {
            throw new InvalidApplicationDataException("Personal information is required");
        }

        if (!StringUtils.hasText(applicationDto.getPersonalInfo().getEmail())) {
            throw new InvalidApplicationDataException("Email is required");
        }

        // Add more validation logic as needed
    }
}