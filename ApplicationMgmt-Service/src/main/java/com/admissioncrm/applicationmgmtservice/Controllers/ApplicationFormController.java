package com.admissioncrm.applicationmgmtservice.Controllers;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormFullResponseDTO;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO.ApplicationFormSubmissionDTO;
import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormResponseDTO;
import com.admissioncrm.applicationmgmtservice.Services.ApplicationFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/application/student")
public class ApplicationFormController {

    private ApplicationFormService applicationFormService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitApplicationForm(ApplicationFormSubmissionDTO requestDto) {
        ApplicationFormResponseDTO response=applicationFormService.createApplication(requestDto);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApplicationFormFullResponseDTO> getApplicationForm(@PathVariable String applicationId) {

        ApplicationFormFullResponseDTO response=applicationFormService.getApplicationById(applicationId);

        return ResponseEntity.ok().body(response);
    }

}