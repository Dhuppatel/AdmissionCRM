package com.admission_crm.lead_management.Controller.InsAdminDash;

import com.admission_crm.lead_management.Payload.DepartmentOverviewDTO;
import com.admission_crm.lead_management.Payload.Response.ApiResponse;
import com.admission_crm.lead_management.Service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leads/i-admin/dashboard")
@RequiredArgsConstructor
public class InstituteAdminDashboardController {
    private final DepartmentService departmentService;

//    @GetMapping("/overview")
//    public ResponseEntity<ApiResponse<List<DepartmentOverviewDTO>>> getDepartmentOverview() {
//        try {
//            List<DepartmentOverviewDTO> departmentOverviews = departmentService.getDepartmentOverviews();
//
//            if (departmentOverviews.isEmpty()) {
//                return ResponseEntity.ok(
//                        ApiResponse.failure("No department data found")
//                );
//            }
//
//            return ResponseEntity.ok(
//                    ApiResponse.success(departmentOverviews)
//            );
//        } catch (Exception e) {
//            // You can log this
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ApiResponse.failure("Error fetching department overview: " + e.getMessage()));
//        }
//    }
}
