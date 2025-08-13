package com.admissioncrm.applicationmgmtservice.Controllers;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormSummaryDTO;
import com.admissioncrm.applicationmgmtservice.Services.ApplicationFormService;
import com.admissioncrm.applicationmgmtservice.Services.CounsellorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
    private final CounsellorService counsellorService;
    private  final ApplicationFormService applicationFormService;

    public ApplicationController(ApplicationFormService applicationFormService,CounsellorService counsellorService) {
        this.applicationFormService = applicationFormService;
        this.counsellorService = counsellorService;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ApplicationFormSummaryDTO>> getAllSubmittedApplications() {
        List<ApplicationFormSummaryDTO> applications = applicationFormService.getAllSubmittedApplications();
        return ResponseEntity.ok(applications);

    }
}
