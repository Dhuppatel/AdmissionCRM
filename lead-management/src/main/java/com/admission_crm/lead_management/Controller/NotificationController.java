package com.admission_crm.lead_management.Controller;

import com.admission_crm.lead_management.Entity.Notification.Notification;
import com.admission_crm.lead_management.Payload.Response.ApiResponse;
import com.admission_crm.lead_management.Service.Notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<Notification>>> getNotifications(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success("Successfully retrived notifications",notificationService.getNotifications(userId)));
    }

    @PostMapping("/mark-read/{id}")
    public ResponseEntity<String> markAsRead(@PathVariable String id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read");
    }
}

