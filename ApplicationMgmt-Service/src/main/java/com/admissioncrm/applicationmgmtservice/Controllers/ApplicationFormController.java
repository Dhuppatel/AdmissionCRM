package com.admissioncrm.applicationmgmtservice.Controllers;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormFullResponseDTO;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.ApplicationFormSubmissionDTO;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormResponseDTO;
import com.admissioncrm.applicationmgmtservice.Services.ApplicationFormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/application/student")
public class ApplicationFormController {

    private ApplicationFormService applicationFormService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitApplicationForm(@Valid @RequestBody ApplicationFormSubmissionDTO requestDto) {
        ApplicationFormResponseDTO response=applicationFormService.createApplication(requestDto);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get/{applicationId}")
    public ResponseEntity<ApplicationFormFullResponseDTO> getApplicationForm(@PathVariable String applicationId) {

        ApplicationFormFullResponseDTO response=applicationFormService.getApplicationById(applicationId);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/update/{applicationId}")
    public ResponseEntity<ApplicationFormResponseDTO> updateApplication(@PathVariable String applicationId,
            @Valid @RequestBody ApplicationFormSubmissionDTO requestDto) {

        ApplicationFormResponseDTO response = applicationFormService.updateApplication(applicationId, requestDto);
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