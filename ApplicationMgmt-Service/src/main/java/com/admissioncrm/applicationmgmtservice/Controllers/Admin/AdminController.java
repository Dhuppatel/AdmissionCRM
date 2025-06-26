package com.admissioncrm.applicationmgmtservice.Controllers.Admin;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormSummaryDTO;
import com.admissioncrm.applicationmgmtservice.Services.ApplicationFormService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/application/admin")
public class AdminController {

    private  final ApplicationFormService applicationFormService;
    public AdminController(ApplicationFormService applicationFormService) {
        this.applicationFormService = applicationFormService;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ApplicationFormSummaryDTO>> getAllSubmittedApplications() {
        List<ApplicationFormSummaryDTO> applications = applicationFormService.getAllSubmittedApplications();
        return ResponseEntity.ok(applications);

    }

}
