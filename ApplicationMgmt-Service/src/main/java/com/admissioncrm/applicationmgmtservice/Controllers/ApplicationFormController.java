package com.admissioncrm.applicationmgmtservice.Controllers;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormFullResponseDTO;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.ApplicationFormSubmissionDTO;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormSummaryDTO;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationResponseDTO;
import com.admissioncrm.applicationmgmtservice.Dto.CreateApplicationDTO;
import com.admissioncrm.applicationmgmtservice.Services.ApplicationFormService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/application")
public class ApplicationFormController {
    @Autowired
    private ApplicationFormService applicationFormService;
    @Autowired
    private Collection<Validator> validators;
    @Qualifier("defaultValidator")
    @Autowired
    private Validator validator;


    @GetMapping("/auth")
    public Map<String, Object> getAuthInfo(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("principal", authentication != null ? authentication.getPrincipal() : null);
        response.put("authorities", authentication != null ? authentication.getAuthorities() : null);
        response.put("authenticated", authentication != null && authentication.isAuthenticated());
        return response;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/greet")
    public String greet(){
        return "Greetings from AppMGMT service!";
    }

//    @PostMapping("/submit")
//    public ResponseEntity<?> submitApplicationForm( @Valid @RequestBody ApplicationFormSubmissionDTO requestDto) {
//        ApplicationFormSummaryDTO response=applicationFormService.createApplication(requestDto);
//
//        return ResponseEntity.ok().body(response);
//    }



    @PostMapping("/create")
    public ResponseEntity<?> createApplicationForm( @Valid @RequestBody CreateApplicationDTO requestDto) {
        CreateApplicationDTO response=applicationFormService.createApplication(requestDto);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/created/{userId}")
    public ResponseEntity<?> getApplicationsByUserId(@PathVariable String userId) {
        var response=applicationFormService.getApplicationsByUserId(userId);

        return ResponseEntity.ok().body(response);
    }


    /**
     * Submit Application Form with Documents
     * Handles both JSON data and file uploads in a single request
     */
    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApplicationResponseDTO> submitApplication(
            @RequestParam("applicationData") String applicationDataJson,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
//            @RequestParam(value = "signature", required = false) MultipartFile signature,
//            @RequestParam(value = "tenthMarksheet", required = false) MultipartFile tenthMarksheet,
//            @RequestParam(value = "twelfthMarksheet", required = false) MultipartFile twelfthMarksheet,
//            @RequestParam(value = "graduationMarksheet", required = false) MultipartFile graduationMarksheet,
//            @RequestParam(value = "entranceExamScorecard", required = false) MultipartFile entranceExamScorecard,
//            @RequestParam(value = "casteCertificate", required = false) MultipartFile casteCertificate,
//            @RequestParam(value = "incomeCertificate", required = false) MultipartFile incomeCertificate,
//            @RequestParam(value = "domicileCertificate", required = false) MultipartFile domicileCertificate,
//            @RequestParam(value = "migrationCertificate", required = false) MultipartFile migrationCertificate,
//            @RequestParam(value = "characterCertificate", required = false) MultipartFile characterCertificate,
//            @RequestParam(value = "gapCertificate", required = false) MultipartFile gapCertificate,
//            @RequestParam(value = "aadharCard", required = false) MultipartFile aadharCard,
            HttpServletRequest request) {

        try {
            // Parse JSON data
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            ApplicationFormSubmissionDTO applicationDTO;
//                    = objectMapper.readValue(
//                    applicationDataJson, ApplicationFormSubmissionDTO.class);

            try {
                applicationDTO = objectMapper.readValue(applicationDataJson, ApplicationFormSubmissionDTO.class);
            } catch (JsonProcessingException jsonError) {
                System.err.println("JSON parsing error: " + jsonError.getMessage());
                System.err.println("Problematic JSON: " + applicationDataJson);
                return ResponseEntity.badRequest()
                        .body(ApplicationResponseDTO.builder()
                                .success(false)
                                .message("Invalid JSON format: " + jsonError.getMessage())
                                .build());
            }


            // Validate the DTO
//            Set<ConstraintViolation<ApplicationFormSubmissionDTO>> violations =
//                    validators.validate(applicationDTO);
//
//            if (!violations.isEmpty()) {
//                List<String> errors = violations.stream()
//                        .map(ConstraintViolation::getMessage)
//                        .collect(Collectors.toList());
//                return ResponseEntity.badRequest()
//                        .body(ApplicationResponseDTO.builder()
//                                .success(false)
//                                .message("Validation failed")
//                                .errors(errors)
//                                .build());
//            }

            // Create document map
            Map<String, MultipartFile> documents = new HashMap<>();
            if (photo != null) documents.put("photo", photo);
//            if (signature != null) documents.put("signature", signature);
//            if (tenthMarksheet != null) documents.put("tenthMarksheet", tenthMarksheet);
//            if (twelfthMarksheet != null) documents.put("twelfthMarksheet", twelfthMarksheet);
//            if (graduationMarksheet != null) documents.put("graduationMarksheet", graduationMarksheet);
//            if (entranceExamScorecard != null) documents.put("entranceExamScorecard", entranceExamScorecard);
//            if (casteCertificate != null) documents.put("casteCertificate", casteCertificate);
//            if (incomeCertificate != null) documents.put("incomeCertificate", incomeCertificate);
//            if (domicileCertificate != null) documents.put("domicileCertificate", domicileCertificate);
//            if (migrationCertificate != null) documents.put("migrationCertificate", migrationCertificate);
//            if (characterCertificate != null) documents.put("characterCertificate", characterCertificate);
//            if (gapCertificate != null) documents.put("gapCertificate", gapCertificate);
//            if (aadharCard != null) documents.put("aadharCard", aadharCard);

            // Process application submission
            ApplicationResponseDTO response = applicationFormService.submitApplication(applicationDTO, documents);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApplicationResponseDTO.builder()
                            .success(false)
                            .message("Internal server error occurred")
                            .build());
        }
    }


    @GetMapping("/get/{applicationId}")
    public ResponseEntity<ApplicationFormFullResponseDTO> getApplicationForm(@PathVariable String applicationId) {

        ApplicationFormFullResponseDTO response=applicationFormService.getApplicationById(applicationId);

        return ResponseEntity.ok().body(response);
    }
    //
     //
     //
     //

    //Replace the Application Id logic with RefId

    //
     //
     //
     //
    @PostMapping("/update/{applicationId}")
    public ResponseEntity<ApplicationFormSummaryDTO> updateApplication(@PathVariable String applicationId,
            @Valid @RequestBody ApplicationFormSubmissionDTO requestDto) {

        ApplicationFormSummaryDTO response = applicationFormService.updateApplication(applicationId, requestDto);
        return ResponseEntity.ok(response);
    }

    // Hard Delete
    @DeleteMapping("/hard-delete/{applicationId}")
    public ResponseEntity<?> deleteApplication(@PathVariable String applicationId) {
        applicationFormService.deleteApplication(applicationId);
        return ResponseEntity.ok().body(Map.of("message", "Application hard deleted successfully"));
    }

    // Soft Delete
    @DeleteMapping("/soft-delete/{applicationId}")
    public ResponseEntity<?> softDeleteApplication(@PathVariable String applicationId) {
        applicationFormService.softDeleteApplication(applicationId);
        return ResponseEntity.ok().body(Map.of("message", "Application soft deleted successfully"));
    }
}