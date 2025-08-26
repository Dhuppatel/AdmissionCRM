package com.admissioncrm.applicationmgmtservice.Controllers;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormFullResponseDTO;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.ApplicationFormSubmissionDTO;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormSummaryDTO;
import com.admissioncrm.applicationmgmtservice.Services.ApplicationFormService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/application/student")
public class ApplicationFormController {
    @Autowired
    private ApplicationFormService applicationFormService;



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

    @PostMapping("/submit")
    public ResponseEntity<?> submitApplicationForm( @Valid @RequestBody ApplicationFormSubmissionDTO requestDto) {
        ApplicationFormSummaryDTO response=applicationFormService.createApplication(requestDto);

        return ResponseEntity.ok().body(response);
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