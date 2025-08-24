package com.admissioncrm.applicationmgmtservice.Controllers.Stats;

import com.admissioncrm.applicationmgmtservice.Dto.ApiResponse;
import com.admissioncrm.applicationmgmtservice.Dto.Stats.ApplicationStatsDTO;
import com.admissioncrm.applicationmgmtservice.Services.ApplicationFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class StatsController {
    private final ApplicationFormService applicationService;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<ApplicationStatsDTO>> getApplicationStats() {
        try {
            ApplicationStatsDTO stats = applicationService.getApplicationStats();
            return ResponseEntity.ok(ApiResponse.success("Application stats fetched successfully", stats));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Failed to fetch application stats: " + e.getMessage(), null));
        }
    }


}
