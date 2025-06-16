//package com.admissioncrm.applicationmgmtservice.Controllers;
//
//import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.ApplicationFormSubmissionDTO;
//import com.admissioncrm.applicationmgmtservice.Services.ApplicationFormService;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/v1/applications")
//@RequiredArgsConstructor
//@Slf4j
//public class ApplicationFormController {
//
//    private final ApplicationFormService applicationFormService;
//
//    @PostMapping
//    @Operation(summary = "Create new application", description = "Submit a new application form")
//    public ResponseEntity<ApiResponse<ApplicationFormResponseDTO>> createApplication(
//            @Valid @RequestBody ApplicationFormSubmissionDTO applicationDto) {
//
//        log.info("Received request to create application for user: {}", applicationDto.getIdUser());
//
//        ApplicationForm createdApplication = applicationFormService.createApplication(applicationDto);
//        ApplicationFormResponseDTO responseDto = mapToResponseDto(createdApplication);
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(ApiResponse.success(responseDto, "Application created successfully"));
//    }
//
//    @GetMapping("/{id}")
//    @Operation(summary = "Get application by ID", description = "Retrieve application form by ID")
//    public ResponseEntity<ApiResponse<ApplicationFormResponseDTO>> getApplicationById(
//            @Parameter(description = "Application ID") @PathVariable Long id) {
//
//        log.info("Received request to get application with ID: {}", id);
//
//        ApplicationForm application = applicationFormService.getApplicationById(id);
//        ApplicationFormResponseDTO responseDto = mapToResponseDto(application);
//
//        return ResponseEntity.ok(ApiResponse.success(responseDto, "Application retrieved successfully"));
//    }
//
//    @GetMapping("/email/{email}")
//    @Operation(summary = "Get application by email", description = "Retrieve application form by email")
//    public ResponseEntity<ApiResponse<ApplicationFormResponseDTO>> getApplicationByEmail(
//            @Parameter(description = "Email address") @PathVariable String email) {
//
//        log.info("Received request to get application with email: {}", email);
//
//        ApplicationForm application = applicationFormService.getApplicationByEmail(email);
//        ApplicationFormResponseDTO responseDto = mapToResponseDto(application);
//
//        return ResponseEntity.ok(ApiResponse.success(responseDto, "Application retrieved successfully"));
//    }
//
//    @GetMapping("/user/{userId}")
//    @Operation(summary = "Get applications by user ID", description = "Retrieve all applications for a user")
//    public ResponseEntity<ApiResponse<List<ApplicationFormResponseDTO>>> getApplicationsByUserId(
//            @Parameter(description = "User ID") @PathVariable String userId) {
//
//        log.info("Received request to get applications for user: {}", userId);
//
//        List<ApplicationForm> applications = applicationFormService.getApplicationsByUserId(userId);
//        List<ApplicationFormResponseDTO> responseDtos = applications.stream()
//                .map(this::mapToResponseDto)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(ApiResponse.success(responseDtos, "Applications retrieved successfully"));
//    }
//
//    @GetMapping
//    @Operation(summary = "Get all applications", description = "Retrieve all applications with pagination")
//    public ResponseEntity<ApiResponse<Page<ApplicationFormResponseDTO>>> getAllApplications(
//            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
//            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
//            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "createdAt") String sortBy,
//            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
//
//        log.info("Received request to get all applications - Page: {}, Size: {}", page, size);
//
//        Sort sort = sortDir.equalsIgnoreCase("desc") ?
//                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        Page<ApplicationForm> applications = applicationFormService.getAllApplications(pageable);
//        Page<ApplicationFormResponseDTO> responseDtos = applications.map(this::mapToResponseDto);
//
//        return ResponseEntity.ok(ApiResponse.success(responseDtos, "Applications retrieved successfully"));
//    }
//
//    @GetMapping("/course/{courseId}")
//    @Operation(summary = "Get applications by course", description = "Retrieve applications for specific course")
//    public ResponseEntity<ApiResponse<Page<ApplicationFormResponseDTO>>> getApplicationsByCourse(
//            @Parameter(description = "Course ID") @PathVariable Long courseId,
//            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
//            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
//
//        log.info("Received request to get applications for course: {}", courseId);
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        Page<ApplicationForm> applications = applicationFormService.getApplicationsByCourse(courseId, pageable);
//        Page<ApplicationFormResponseDTO> responseDtos = applications.map(this::mapToResponseDto);
//
//        return ResponseEntity.ok(ApiResponse.success(responseDtos, "Applications retrieved successfully"));
//    }
//
//    @PutMapping("/{id}")
//    @Operation(summary = "Update application", description = "Update existing application form")
//    public ResponseEntity<ApiResponse<ApplicationFormResponseDTO>> updateApplication(
//            @Parameter(description = "Application ID") @PathVariable Long id,
//            @Valid @RequestBody ApplicationFormSubmissionDTO applicationDto) {
//
//        log.info("Received request to update application with ID: {}", id);
//
//        ApplicationForm updatedApplication = applicationFormService.updateApplication(id, applicationDto);
//        ApplicationFormResponseDTO responseDto = mapToResponseDto(updatedApplication);
//
//        return ResponseEntity.ok(ApiResponse.success(responseDto, "Application updated successfully"));
//    }
//
//    @DeleteMapping("/{id}")
//    @Operation(summary = "Delete application", description = "Soft delete application form")
//    public ResponseEntity<ApiResponse<Void>> deleteApplication(
//            @Parameter(description = "Application ID") @PathVariable Long id) {
//
//        log.info("Received request to delete application with ID: {}", id);
//
//        applicationFormService.softDeleteApplication(id);
//
//        return ResponseEntity.ok(ApiResponse.success(null, "Application deleted successfully"));
//    }
//
//    @DeleteMapping("/{id}/hard")
//    @Operation(summary = "Hard delete application", description = "Permanently delete application form")
//    public ResponseEntity<ApiResponse<Void>> hardDeleteApplication(
//            @Parameter(description = "Application ID") @PathVariable Long id) {
//
//        log.info("Received request to hard delete application with ID: {}", id);
//
//        applicationFormService.deleteApplication(id);
//
//        return ResponseEntity.ok(ApiResponse.success(null, "Application permanently deleted"));
//    }
//
//    @GetMapping("/search")
//    @Operation(summary = "Search applications", description = "Search applications by name")
//    public ResponseEntity<ApiResponse<Page<ApplicationFormResponseDTO>>> searchApplications(
//            @Parameter(description = "Search term") @RequestParam String searchTerm,
//            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
//            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
//
//        log.info("Received request to search applications with term: {}", searchTerm);
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        Page<ApplicationForm> applications = applicationFormService.searchApplicationsByName(searchTerm, pageable);
//        Page<ApplicationFormResponseDTO> responseDtos = applications.map(this::mapToResponseDto);
//
//        return ResponseEntity.ok(ApiResponse.success(responseDtos, "Search completed successfully"));
//    }
//
//    @GetMapping("/filter")
//    @Operation(summary = "Filter applications", description = "Filter applications by multiple criteria")
//    public ResponseEntity<ApiResponse<Page<ApplicationFormResponseDTO>>> filterApplications(
//            @Parameter(description = "Course ID") @RequestParam(required = false) Long courseId,
//            @Parameter(description = "Email") @RequestParam(required = false) String email,
//            @Parameter(description = "Mobile") @RequestParam(required = false) String mobile,
//            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
//            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
//
//        log.info("Received request to filter applications");
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        Page<ApplicationForm> applications = applicationFormService.filterApplications(courseId, email, mobile, pageable);
//        Page<ApplicationFormResponseDTO> responseDtos = applications.map(this::mapToResponseDto);
//
//        return ResponseEntity.ok(ApiResponse.success(responseDtos, "Filter applied successfully"));
//    }
//
//    @GetMapping("/reports/date-range")
//    @Operation(summary = "Get applications by date range", description = "Retrieve applications within date range")
//    public ResponseEntity<ApiResponse<List<ApplicationFormResponseDTO>>> getApplicationsByDateRange(
//            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
//
//        log.info("Received request to get applications between {} and {}", startDate, endDate);
//
//        List<ApplicationForm> applications = applicationFormService.getApplicationsBetweenDates(startDate, endDate);
//        List<ApplicationFormResponseDTO> responseDtos = applications.stream()
//                .map(this::mapToResponseDto)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(ApiResponse.success(responseDtos, "Applications retrieved successfully"));
//    }
//
//    @GetMapping("/stats/count")
//    @Operation(summary = "Get application count", description = "Get total count of active applications")
//    public ResponseEntity<ApiResponse<Long>> getTotalApplicationCount() {
//
//        log.info("Received request to get total application count");
//
//        Long count = applicationFormService.getTotalActiveApplications();
//
//        return ResponseEntity.ok(ApiResponse.success(count, "Application count retrieved successfully"));
//    }
//
//    @GetMapping("/check-email/{email}")
//    @Operation(summary = "Check email availability", description = "Check if email is already registered")
//    public ResponseEntity<ApiResponse<Boolean>> checkEmailAvailability(
//            @Parameter(description = "Email to check") @PathVariable String email) {
//
//        log.info("Received request to check email availability: {}", email);
//
//        boolean isRegistered = applicationFormService.isEmailAlreadyRegistered(email);
//
//        return ResponseEntity.ok(ApiResponse.success(
//                !isRegistered,
//                isRegistered ? "Email is already registered" : "Email is available"));
//    }
//
//    // Helper method to map Entity to Response DTO
//    private ApplicationFormResponseDTO mapToResponseDto(ApplicationForm application) {
//        return ApplicationFormResponseDTO.builder()
//                .id(application.getApplicationFormId())
//                .idUser(application.getIdUser())
//                .fullName(application.getFullName())
//                .email(application.getEmail())
//                .studentMobile(application.getStudentMobile())
//                .instituteCourseId(application.getInstituteCourseId())
//                .createdAt(application.getCreatedAt().toString())
//                .updatedAt(application.getUpdatedAt() != null ? application.getUpdatedAt().toString() : null)
//                .build();
//    }
//}