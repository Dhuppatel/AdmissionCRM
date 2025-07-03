package com.admission_crm.lead_management.Controller.LeadController;

import com.admission_crm.lead_management.Payload.Response.ApiResponse;
import com.admission_crm.lead_management.Service.LeadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/leads/maintenance")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class LeadMaintenanceController {

    private final LeadService leadService;

    @DeleteMapping("/cleanup")
    @PostMapping("/cleanup/old-leads")
    public ResponseEntity<?> cleanupOldCompletedLeads(@RequestParam(defaultValue = "90") int daysOld,
                                                      Authentication authentication) {
        try {
            if (daysOld < 30) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Invalid days parameter", "Days must be at least 30 for safety"));
            }

            int cleanedCount = leadService.cleanupOldCompletedLeads(daysOld);

            log.info("Cleaned up {} old completed leads (older than {} days) by user: {}",
                    cleanedCount, daysOld, authentication.getName());

            Map<String, Object> result = Map.of(
                    "cleanedLeadsCount", cleanedCount,
                    "daysOld", daysOld,
                    "cleanupDate", LocalDateTime.now().toString()
            );

            return ResponseEntity.ok(ApiResponse.success("Old leads cleanup completed successfully", result));
        } catch (Exception e) {
            log.error("Error during old leads cleanup: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to cleanup old leads", "An unexpected error occurred"));
        }
    }
}
