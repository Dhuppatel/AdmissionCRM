package com.admissioncrm.authenticationservice.Controllers.Stats;

import com.admissioncrm.authenticationservice.DTO.ApiResponse;
import com.admissioncrm.authenticationservice.DTO.Stats.UserStatsDTO;
import com.admissioncrm.authenticationservice.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/stats")
@RequiredArgsConstructor
public class StatsController {
        private final UserService userService;

        //user stats
        @GetMapping("/users")
        public ResponseEntity<ApiResponse<UserStatsDTO>> getUserStats() {
            UserStatsDTO stats = userService.getUserStats();
            return ResponseEntity.ok(ApiResponse.success("User stats fetched successfully", stats));
        }
    }