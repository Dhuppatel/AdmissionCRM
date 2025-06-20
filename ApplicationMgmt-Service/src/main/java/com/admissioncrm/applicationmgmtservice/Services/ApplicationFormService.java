package com.admissioncrm.applicationmgmtservice.Services;


import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormFullResponseDTO;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.ApplicationFormSubmissionDTO;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormResponseDTO;
import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;
import com.admissioncrm.applicationmgmtservice.Enums.ApplicationStatus;
import com.admissioncrm.applicationmgmtservice.Exception.*;
import com.admissioncrm.applicationmgmtservice.Repositories.ApplicationFormRepository;
import com.admissioncrm.applicationmgmtservice.Utills.ApplicationFormMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ApplicationFormService  {



    // Complete the incomplete Exception Handeling



    private final Clock clock;
    private final ApplicationFormRepository applicationFormRepository;
    private final ApplicationFormMapper applicationFormMapper;
    private final ReferenceIdService referenceIdService;

    public ApplicationFormResponseDTO createApplication(ApplicationFormSubmissionDTO applicationDto) {

        //
        // Todo: add the logic to link user(from authnetication service) with Application iD
        //


        log.info("Creating new application for user: {}", applicationDto.getIdUser());

        // Validate application data
        validateApplicationData(applicationDto);

        // Check for duplicate email
        if (isEmailAlreadyRegistered(applicationDto.getPersonalInfo().getEmail())) {
            throw new DuplicateApplicationException(
                    "Application with email " + applicationDto.getPersonalInfo().getEmail() + " already exists");
        }
        //cheak for duplicate phone number
        if(isStudentMobileAlreadyRegistered(applicationDto.getPersonalInfo().getStudentMobile())){
            throw new DuplicateApplicationException(
                    "Application with student mobile " + applicationDto.getPersonalInfo().getStudentMobile() + " already exists"
            );
        }
        try {
            // Map DTO to Entity
            ApplicationForm applicationForm = applicationFormMapper.mapToEntity(applicationDto);

            String referenceId = referenceIdService.generateReferenceId();
            applicationForm.setReferenceId(referenceId);
            applicationForm.setApplicationStatus(ApplicationStatus.SUBMITTED);

            // Save the application
            ApplicationForm savedApplication = applicationFormRepository.save(applicationForm);

            log.info("Application created successfully with ID: {}", savedApplication.getApplicationId());

            return ApplicationFormResponseDTO.builder()
                    .referenceId(savedApplication.getReferenceId())
                    .applicationId(savedApplication.getApplicationId())
                    .status(ApplicationStatus.SUBMITTED)
                    .studentName(savedApplication.getFullName())
                    .courseName(savedApplication.getCourseInstituteName())
                    .email(savedApplication.getEmail())
                    .submittedDate(LocalDateTime.now(clock))
                    .build();

        } catch (Exception e) {
            log.error("Error creating application for user: {}", applicationDto.getIdUser(), e);
            throw new RuntimeException("Failed to create application", e);
        }
    }



    @Transactional(readOnly = true)
    public ApplicationFormFullResponseDTO getApplicationById(String applicationId) {
        try{
            log.info("Fetching application with ID: {}", applicationId);
            System.out.println("1");
            ApplicationForm applicationForm= applicationFormRepository.findByapplicationId(applicationId)
                    .orElseThrow(() -> new ApplicationFormNotFoundException("Application not found with ID: " + applicationId));
            System.out.println("2");
            ApplicationFormFullResponseDTO responseDTO=new ApplicationFormFullResponseDTO();

            //set metadata
            responseDTO.setApplicationId(applicationForm.getApplicationId());
            responseDTO.setStatus(applicationForm.getApplicationStatus());
            responseDTO.setSubmittedDate(applicationForm.getCreatedAt());
            System.out.println("3");
            switch (applicationForm.getApplicationStatus()) {
                case DRAFT, SUBMITTED -> responseDTO.setEditable(true);
                default -> responseDTO.setEditable(false);
            }
            //map the form to dto
            responseDTO.setFormData(
                    applicationFormMapper.mapToDTO(applicationForm)
            );
            return responseDTO;

        }catch (Exception e) {
            throw new RuntimeException("Error fetching application", e);
        }

    }


    @Transactional(readOnly = true)
    public ApplicationFormFullResponseDTO getApplicationByEmail(String email) {
        try{
            log.info("Fetching application with Email ID: {}", email);

            ApplicationForm applicationForm= applicationFormRepository.findByEmail(email)
                    .orElseThrow(() -> new ApplicationFormNotFoundException("Application not found with emialID: " + email));

            ApplicationFormFullResponseDTO responseDTO=new ApplicationFormFullResponseDTO();

            //set metadata
            responseDTO.setApplicationId(applicationForm.getApplicationId());
            responseDTO.setStatus(applicationForm.getApplicationStatus());
            responseDTO.setSubmittedDate(applicationForm.getCreatedAt());

            switch (applicationForm.getApplicationStatus()) {
                case DRAFT, SUBMITTED -> responseDTO.setEditable(true);
                default -> responseDTO.setEditable(false);
            }
            //map the form to dto
            responseDTO.setFormData(
                    applicationFormMapper.mapToDTO(applicationForm)
            );
            return responseDTO;

        }catch (Exception e) {
            throw new RuntimeException("Error fetching application", e);
        }

    }


    @Transactional(readOnly = true)
    public List<ApplicationForm> getApplicationsByUserId(String userId) {
        log.info("Fetching applications for user ID: {}", userId);

        return applicationFormRepository.findByIdUserAndDeletedAtIsNull(userId);
    }


    @Transactional(readOnly = true)
    public Page<ApplicationForm> getAllApplications(Pageable pageable) {
        log.info("Fetching all applications with pagination");

        return applicationFormRepository.findAll(pageable)
                .map(app -> app.getDeletedAt() == null ? app : null);
    }



    public ApplicationFormResponseDTO updateApplication(String applicationId, ApplicationFormSubmissionDTO applicationDto) {
        log.info("Updating application with ID: {}", applicationId);

        // Get existing application
        ApplicationForm existingApplication = applicationFormRepository.findByapplicationId(applicationId)
                .orElseThrow(() -> new ApplicationFormNotFoundException("Application not found with ID: " + applicationId));


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

            applicationFormMapper.updateEntityFromDTO(existingApplication, applicationDto);
            applicationFormRepository.save(existingApplication);

            log.info("Application updated successfully with ID: {}", existingApplication.getApplicationId());
            return ApplicationFormResponseDTO.builder()
                    .referenceId(existingApplication.getReferenceId())
                    .applicationId(existingApplication.getApplicationId())
                    .status(ApplicationStatus.SUBMITTED)
                    .studentName(existingApplication.getFullName())
                    .courseName(existingApplication.getCourseInstituteName())
                    .email(existingApplication.getEmail())
                    .submittedDate(existingApplication.getCreatedAt())
                    .updatedDate(existingApplication.getUpdatedAt())
                    .build();

        } catch (Exception e) {
            log.error("Error updating application with ID: {}", applicationId, e);
            throw new RuntimeException("Failed to update application", e);
        }
    }





    public void deleteApplication(String id) {
        log.info("Hard deleting application with ID: {}", id);

        ApplicationForm application = applicationFormRepository.findByapplicationId(id)
                .orElseThrow(() -> new ApplicationFormNotFoundException("Application not found with ID: " + id));

        applicationFormRepository.delete(application);

        log.info("Application deleted successfully with ID: {}", id);
    }


    public void softDeleteApplication(String id) {
        log.info("Soft deleting application with ID: {}", id);

        ApplicationForm application = applicationFormRepository.findByapplicationId(id)
                .orElseThrow(() -> new ApplicationFormNotFoundException("Application not found with ID: " + id));

        application.setDeletedAt(LocalDateTime.now());
        applicationFormRepository.save(application);

        log.info("Application soft deleted successfully with ID: {}", id);
    }



    @Transactional(readOnly = true)
    public Long getTotalActiveApplications() {
        return applicationFormRepository.countActiveApplications();
    }

    @Transactional(readOnly = true)
    public boolean isEmailAlreadyRegistered(String email) {
        return applicationFormRepository.findByEmailAndDeletedAtIsNull(email).isPresent();
    }
    @Transactional(readOnly = true)
    public boolean isStudentMobileAlreadyRegistered( String studentMobile) {
        return applicationFormRepository.existsByStudentMobile(studentMobile);
    }



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