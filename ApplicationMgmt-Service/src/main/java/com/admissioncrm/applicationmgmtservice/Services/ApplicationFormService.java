package com.admissioncrm.applicationmgmtservice.Services;


import com.admissioncrm.applicationmgmtservice.Dto.*;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.ApplicationFormSubmissionDTO;
import com.admissioncrm.applicationmgmtservice.Dto.Stats.ApplicationStatsDTO;
import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm.ApplicationForm;
import com.admissioncrm.applicationmgmtservice.Enums.ApplicationStatus;
import com.admissioncrm.applicationmgmtservice.Exception.*;
import com.admissioncrm.applicationmgmtservice.Feign.LeadFeign;
import com.admissioncrm.applicationmgmtservice.Repositories.ApplicationFormRepository;
import com.admissioncrm.applicationmgmtservice.Services.Document.DocumentService;
import com.admissioncrm.applicationmgmtservice.Utills.ApplicationFormMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.security.PrivilegedAction;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional

public class ApplicationFormService  {

    private final LeadFeign leadFeign;

    // Complete the incomplete Exception Handeling



    private final Clock clock;
    private final ApplicationFormRepository applicationFormRepository;
    private final ApplicationFormMapper applicationFormMapper;
    private final ReferenceIdService referenceIdService;
    private final DocumentService documentService;

    public CreateApplicationDTO createApplication(CreateApplicationDTO applicationDto) {
        log.info("Creating new application for user: {}", applicationDto.getApplicantId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = authentication.getName();

        //cheak if the application is already exists for the user
        if(applicationFormRepository.existsByIdUserAndSelectedProgram(userId, applicationDto.getProgramId())) {
            throw new DuplicateApplicationException("Application already exists");
        }

        String referenceId = referenceIdService.generateReferenceId();

        ApplicationForm applicationForm = new ApplicationForm();
        applicationForm.setIdUser(userId);
        applicationForm.setReferenceId(referenceId);
        applicationForm.setEmail(applicationDto.getEmail());
        applicationForm.setApplicationStatus(ApplicationStatus.DRAFT);
        applicationForm.setSelectedProgram(applicationDto.getProgramId());
        applicationForm.setSelectedInstitute(applicationDto.getInstituteId());

        applicationFormRepository.save(applicationForm);


        return applicationDto;
    }

    public List<CreateApplicationResponseDTO> getMyApplications() {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Fetching applications for user ID: {}", userId);
        List<ApplicationForm> applications = applicationFormRepository.findByIdUserAndDeletedAtIsNull(userId);
        List<CreateApplicationResponseDTO> responseDTOs = new ArrayList<>();

        responseDTOs=applications.stream()
                .map(app ->CreateApplicationResponseDTO.builder()
                        .id(app.getApplicationId())
                        .email(app.getEmail())
                        .referenceId(app.getReferenceId())
                        .createdAt(app.getCreatedAt())
                        .updatedAt(app.getUpdatedAt())
                        .instituteName(leadFeign.getInstituteName(app.getSelectedInstitute()))
                        .programName(leadFeign.getProgramName(app.getSelectedProgram()))
                        .status(app.getApplicationStatus().toString())
                        .instituteId(app.getSelectedInstitute())
                        .programId(app.getSelectedProgram())
                        .build())
                .collect(Collectors.toList());

        return responseDTOs;
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
    public List<CreateApplicationDTO> getApplicationsByUserId(String userId) {
        log.info("Fetching applications for user ID: {}", userId);


        List<CreateApplicationDTO> applications = applicationFormRepository.findByIdUserAndDeletedAtIsNull(userId)
                .stream()
                .map(app -> CreateApplicationDTO.builder()
                        .applicantId(app.getIdUser())
                        .referenceId(app.getReferenceId())
                        .programId(app.getSelectedProgram())
                        .instituteId(app.getSelectedInstitute())
                        .build())
                .toList();

        return null;
    }
    
    public ApplicationFormSummaryDTO updateApplication(String applicationId, ApplicationFormSubmissionDTO applicationDto) {
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
            return ApplicationFormSummaryDTO.builder()
                    .referenceId(existingApplication.getReferenceId())
                    .status(ApplicationStatus.SUBMITTED)
                    .studentFullName(existingApplication.getPersonalInfo().getFullName())
                    .courseAppliedFor(existingApplication.getSelectedInstitute())
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

    public List<ApplicationFormSummaryDTO> getAllSubmittedApplications() {
        log.info("Fetching all submitted applications");
        try {
            List<ApplicationForm> applications = applicationFormRepository.findByDeletedAtIsNull();

            return applications.stream()
                    .map(app -> ApplicationFormSummaryDTO.builder()
                            .referenceId(app.getReferenceId())
                            .status(app.getApplicationStatus())
                            .studentFullName(app.getPersonalInfo().getFullName())
                            .courseAppliedFor(app.getSelectedInstitute())
                            .email(app.getEmail())
                            .submittedDate(app.getCreatedAt())
                            .updatedDate(app.getUpdatedAt())
                            .courseAppliedFor(app.getSelectedInstitute())
                            .DocumentsSubmitted(app.getDocumentsSubmitted())
                            .daysSinceSubmission(ChronoUnit.DAYS.between(app.getCreatedAt().toLocalDate(), LocalDate.now(clock)))
                            .build())
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching all applications", e);
            throw new RuntimeException("Failed to fetch applications", e);
        }
    }

    @Transactional(readOnly = true)
    public boolean isApplicationAlreadyExistsForProgram(
            String userId,
            String programId,
            String applicationId // for edit case
    ) {

        if (applicationId == null) {
            // New application
            return applicationFormRepository
                    .existsByIdUserAndSelectedProgramAndDeletedAtIsNull(userId, programId);
        }

        // Editing existing application → exclude itself
        return applicationFormRepository
                .existsByIdUserAndSelectedProgramAndApplicationIdNotAndDeletedAtIsNull(
                        userId,
                        programId,
                        applicationId
                );
    }
    //Application Stats

    public ApplicationStatsDTO getApplicationStats() {
        long total = applicationFormRepository.count();
        long pending = applicationFormRepository.countByApplicationStatus(ApplicationStatus.SUBMITTED);
        long approved = applicationFormRepository.countByApplicationStatus(ApplicationStatus.APPROVED);
        long rejected = applicationFormRepository.countByApplicationStatus(ApplicationStatus.REJECTED);

        return new ApplicationStatsDTO(total, pending, approved, rejected);
    }
    @Transactional
    public ApplicationResponseDTO submitApplication(
            String applicationId,
            ApplicationFormSubmissionDTO dto,
            Map<String, MultipartFile> documents) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        ApplicationForm applicationForm = applicationFormRepository
                .findByApplicationIdAndIdUser(applicationId, userId);
                if(applicationForm==null) throw new RuntimeException("Application not found");

        if (applicationForm.getApplicationStatus() == ApplicationStatus.SUBMITTED) {
            throw new IllegalStateException("Application already submitted");
        }

        // Update latest values
        applicationFormMapper.updateEntityFromDTO( applicationForm,dto);

        // Strict validation
//        validateApplicationData(dto);

        // Duplicate checks only here
        if (isApplicationAlreadyExistsForProgram(userId, applicationForm.getSelectedProgram(), applicationId)) {
            throw new DuplicateApplicationException(
                    "You have already applied for this program."
            );
        }

        applicationForm.setApplicationStatus(ApplicationStatus.SUBMITTED);
        applicationForm.setSubmittedAt(LocalDateTime.now());

        ApplicationForm savedApplication = applicationFormRepository.save(applicationForm);

        if (!documents.isEmpty()) {
            documentService.saveDocuments(savedApplication.getReferenceId(), documents);
        }

        return ApplicationResponseDTO.builder()
                .applicationId(savedApplication.getApplicationId())
                .referenceId(savedApplication.getReferenceId())
                .status(ApplicationStatus.SUBMITTED)
                .success(true)
                .message("Application submitted successfully")
                .build();
    }



    @Transactional
    public ApplicationResponseDTO saveDraft (
            String applicationId,
            ApplicationFormSubmissionDTO dto,
            Map<String, MultipartFile> documents) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        ApplicationForm applicationForm = applicationFormRepository
                .findByApplicationIdAndIdUser(applicationId, userId);

        if(applicationForm==null) throw  new RuntimeException("Application not found");


        // Prevent editing after submit
        if (applicationForm.getApplicationStatus() == ApplicationStatus.SUBMITTED) {
            throw new IllegalStateException("Application already submitted");
        }

        // Update entity
        applicationFormMapper.updateEntityFromDTO(applicationForm, dto);

        // If first time filling, move CREATED → DRAFT
        if (applicationForm.getApplicationStatus() == ApplicationStatus.CREATED) {
            applicationForm.setApplicationStatus(ApplicationStatus.DRAFT);
        }

        ApplicationForm savedApplication = applicationFormRepository.save(applicationForm);

        if (!documents.isEmpty()) {
            documentService.saveDocuments(savedApplication.getReferenceId(), documents);
        }

        return ApplicationResponseDTO.builder()
                .applicationId(savedApplication.getApplicationId())
                .referenceId(savedApplication.getReferenceId())
                .status(savedApplication.getApplicationStatus())
                .success(true)
                .message("Draft saved successfully")
                .build();
    }




//    public ApplicationResponseDTO submitApplication(ApplicationFormSubmissionDTO applicationDto, Map<String, MultipartFile> documents) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = authentication.getName();
//        applicationDto.setIdUser(userId);
//        log.info("Creating new application for user: {}", userId);
//
//        // Validate application data
////        validateApplicationData(applicationDto);
//
//        // Check for duplicate email
//        if (isEmailAlreadyRegistered(applicationDto.getPersonalInfo().getEmail())) {
//            throw new DuplicateApplicationException(
//                    "Application with email " + applicationDto.getPersonalInfo().getEmail() + " already exists");
//        }
//        //cheak for duplicate phone number
//        if(isStudentMobileAlreadyRegistered(applicationDto.getPersonalInfo().getStudentMobile())){
//            throw new DuplicateApplicationException(
//                    "Application with student mobile " + applicationDto.getPersonalInfo().getStudentMobile() + " already exists"
//            );
//        }
//        try {
//            // Map DTO to Entity
//            ApplicationForm applicationForm = applicationFormMapper.mapToEntity(applicationDto);
//
//            String referenceId = referenceIdService.generateReferenceId();
//            applicationForm.setReferenceId(referenceId);
//            applicationForm.setApplicationStatus(ApplicationStatus.SUBMITTED);
//
//
//
//           //Now save the documents
//
//
//            ApplicationForm savedApplication = applicationFormRepository.save(applicationForm);
//
//
//            documentService.saveDocuments(referenceId, documents);
//
//           // Save the application
//
//
//            log.info("Application created successfully with ID: {}", savedApplication.getApplicationId());
//
//
//
//            return ApplicationResponseDTO.builder()
//                    .applicationId(savedApplication.getApplicationId())
//                    .referenceId(savedApplication.getReferenceId())
//                    .status(ApplicationStatus.SUBMITTED)
//                  //add this urls
////                    .documentUrls(savedApplication.getDocumentsSubmitted())
//
//
//
////                    .studentFullName(savedApplication.getPersonalInfo().getFullName())
////                    .courseAppliedFor(savedApplication.getSelectedInstitute())
////                    .email(savedApplication.getEmail())
////                    .submittedDate(LocalDateTime.now(clock))
//                    .build();
//        } catch (Exception e) {
//            log.error("Error creating application for user: {}", applicationDto.getIdUser(), e);
//            throw new RuntimeException("Failed to create application", e);
//        }
//    }



}