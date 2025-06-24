package com.admissioncrm.applicationmgmtservice.Services.workflow;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormSummaryDTO;
import com.admissioncrm.applicationmgmtservice.Dto.WorkflowDTO.ApplicationWorkflowRequestDTO;
import com.admissioncrm.applicationmgmtservice.Dto.WorkflowDTO.ApplicationWorkflowResponseDTO;
import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;
import com.admissioncrm.applicationmgmtservice.Entities.workflow.ApplicationStatusHistory;
import com.admissioncrm.applicationmgmtservice.Enums.ApplicationStatus;
import com.admissioncrm.applicationmgmtservice.Exception.ApplicationFormNotFoundException;
import com.admissioncrm.applicationmgmtservice.Exception.InvalidWorkflowTransitionException;
import com.admissioncrm.applicationmgmtservice.Repositories.ApplicationFormRepository;
import com.admissioncrm.applicationmgmtservice.Repositories.Workflow.ApplicationStatusHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class WorkflowService {

    private final ApplicationFormRepository applicationFormRepository;
    private final ApplicationStatusHistoryRepository statusHistoryRepository;


    public WorkflowService(ApplicationFormRepository applicationFormRepository,
                                      ApplicationStatusHistoryRepository statusHistoryRepository) {
        this.applicationFormRepository = applicationFormRepository;
        this.statusHistoryRepository = statusHistoryRepository;
    }

    // Move application to next stage in workflow
    public ApplicationWorkflowResponseDTO processApplicationWorkflow(String applicationId,
                                                                     ApplicationWorkflowRequestDTO request) {
        log.info("Processing workflow for application: {} to status: {}", applicationId, request.getNewStatus());

        ApplicationForm application = getApplicationById(applicationId);
        ApplicationStatus currentStatus = application.getApplicationStatus();
        ApplicationStatus newStatus = ApplicationStatus.valueOf(request.getNewStatus().toUpperCase());

        // Validate workflow transition
        validateWorkflowTransition(currentStatus, newStatus);

        // Execute workflow actions
        executeWorkflowActions(application, currentStatus, newStatus, request);

        // Update application status
        application.setApplicationStatus(newStatus);
        application.setUpdatedAt(LocalDateTime.now());
        application.setRemarks(request.getRemarks());

        // Save status history
        saveStatusHistory(application, currentStatus, newStatus, request.getActionBy(), request.getRemarks());

        ApplicationForm updatedApplication = applicationFormRepository.save(application);

        // Send notifications


        return ApplicationWorkflowResponseDTO.builder()
                .applicationId(applicationId)
                .previousStatus(currentStatus.toString())
                .currentStatus(newStatus.toString())
                .actionBy(request.getActionBy())
                .actionDate(LocalDateTime.now())
                .remarks(request.getRemarks())
                .nextPossibleActions(getNextPossibleActions(newStatus))
                .build();
    }



    // Get applications by workflow stage
    public List<ApplicationFormSummaryDTO> getApplicationsByWorkflowStage(ApplicationStatus status) {
        List<ApplicationForm> applications =
                applicationFormRepository.findByApplicationStatusOrderByCreatedAt(status);

        return applications.stream()
                .map(this::mapToSummaryDTO)
                .collect(Collectors.toList());
    }



    // Private helper methods
    private void validateWorkflowTransition(ApplicationStatus currentStatus, ApplicationStatus newStatus) {
        Map<ApplicationStatus, Set<ApplicationStatus>> allowedTransitions = getWorkflowTransitions();

        if (!allowedTransitions.get(currentStatus).contains(newStatus)) {
            throw new InvalidWorkflowTransitionException(
                    String.format("Invalid transition from %s to %s", currentStatus, newStatus));
        }
    }

    private Map<ApplicationStatus, Set<ApplicationStatus>> getWorkflowTransitions() {
        Map<ApplicationStatus, Set<ApplicationStatus>> transitions = new HashMap<>();

        //this defines from which state we can go to only which state and validates that
        // change this as our need and workflow
        transitions.put(ApplicationStatus.SUBMITTED,
                Set.of(ApplicationStatus.UNDER_REVIEW, ApplicationStatus.REJECTED, ApplicationStatus.INCOMPLETE));

        transitions.put(ApplicationStatus.UNDER_REVIEW,
                Set.of(ApplicationStatus.PENDING_DOCUMENTS, ApplicationStatus.APPROVED, ApplicationStatus.REJECTED));

        transitions.put(ApplicationStatus.PENDING_DOCUMENTS,
                Set.of(ApplicationStatus.UNDER_REVIEW, ApplicationStatus.REJECTED));

        transitions.put(ApplicationStatus.APPROVED,
                Set.of(ApplicationStatus.ENROLLED, ApplicationStatus.REJECTED));

        transitions.put(ApplicationStatus.INCOMPLETE,
                Set.of(ApplicationStatus.SUBMITTED, ApplicationStatus.REJECTED));


        return transitions;
    }

    private void executeWorkflowActions(ApplicationForm application, ApplicationStatus currentStatus,
                                        ApplicationStatus newStatus, ApplicationWorkflowRequestDTO request) {
        // Execute specific actions based on status transitions
        switch (newStatus) {
            case UNDER_REVIEW:
                assignReviewer(application, request.getAssignedTo());
                break;
            case APPROVED:
                //generate Admission Latter
                break;
            case REJECTED:
                // send notification that Application is rejected
                break;
            case ENROLLED:
                createStudentRecord(application);
                break;
            // Add more actions as needed
        }
    }

    private void saveStatusHistory(ApplicationForm application, ApplicationStatus oldStatus,
                                   ApplicationStatus newStatus, String actionBy, String remarks) {
        ApplicationStatusHistory history = ApplicationStatusHistory.builder()
                .applicationForm(application)
                .previousStatus(oldStatus)
                .newStatus(newStatus)
                .actionBy(actionBy)
                .remarks(remarks)
                .createdDate(LocalDateTime.now())
                .build();

        statusHistoryRepository.save(history);
    }



    private List<String> getNextPossibleActions(ApplicationStatus currentStatus) {
        Map<ApplicationStatus, Set<ApplicationStatus>> transitions = getWorkflowTransitions();
        return transitions.get(currentStatus).stream()
                .map(ApplicationStatus::toString)
                .collect(Collectors.toList());
    }

    private ApplicationForm getApplicationById(String applicationId) {
        return applicationFormRepository.findByapplicationId(applicationId)
                .orElseThrow(() -> new ApplicationFormNotFoundException("Application not found: " + applicationId));
    }

    // Helper methods for workflow actions
    private void assignReviewer(ApplicationForm application, String reviewerId) {
        if (reviewerId != null) {
            application.setAssignedReviewerId(reviewerId);
            log.info("Assigned reviewer {} to application {}", reviewerId, application.getReferenceId());
        }
    }

//    private void generateAdmissionLetter(ApplicationForm application) {
//        // Logic to generate admission letter
//        log.info("Generating admission letter for application {}", application.getApplicationFormId());
//    }

    private void createStudentRecord(ApplicationForm application) {


        //add the Record to the finally Enrolled student In the DB of StudenMGMt System


        log.info("Creating student record for application {}", application.getApplicationId());
    }

//    private ApplicationStatusHistoryDTO mapToHistoryDTO(ApplicationStatusHistory history) {
//        return ApplicationStatusHistoryDTO.builder()
//                .id(history.getId())
//                .applicationId(history.getApplicationForm().getApplicationFormId())
//                .previousStatus(history.getPreviousStatus().toString())
//                .newStatus(history.getNewStatus().toString())
//                .actionBy(history.getActionBy())
//                .remarks(history.getRemarks())
//                .createdDate(history.getCreatedDate())
//                .build();
//    }

    private ApplicationFormSummaryDTO mapToSummaryDTO(ApplicationForm application) {
        return ApplicationFormSummaryDTO.builder()
                .applicationId(application.getApplicationId())
                .studentFullName(application.getFullName())
                .email(application.getEmail())
                .courseAppliedFor(application.getCourseInstituteName())
                .status(application.getApplicationStatus())
                .submittedDate(application.getCreatedAt())
                .daysSinceSubmission(ChronoUnit.DAYS.between(application.getCreatedAt(), LocalDateTime.now()))
                .build();
    }
}