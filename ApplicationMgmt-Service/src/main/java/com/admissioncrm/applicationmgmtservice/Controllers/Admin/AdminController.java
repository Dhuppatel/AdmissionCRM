package com.admissioncrm.applicationmgmtservice.Controllers.Admin;

import com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormSummaryDTO;
import com.admissioncrm.applicationmgmtservice.Dto.Counsellor.CounsellorDTO;
import com.admissioncrm.applicationmgmtservice.Services.ApplicationFormService;
import com.admissioncrm.applicationmgmtservice.Services.CounsellorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4028")
@RestController
@RequestMapping("/api/application/admin")
public class AdminController {

    private final CounsellorService counsellorService;
    private  final ApplicationFormService applicationFormService;

    public AdminController(ApplicationFormService applicationFormService,CounsellorService counsellorService) {
        this.applicationFormService = applicationFormService;
        this.counsellorService = counsellorService;
    }

    
    @GetMapping("/counsellors")
    public ResponseEntity<List<CounsellorDTO>> getAllCounsellors() {
        List<CounsellorDTO> counsellors = counsellorService.getAllCounsellors();
        return ResponseEntity.ok(counsellors);
    }

}
