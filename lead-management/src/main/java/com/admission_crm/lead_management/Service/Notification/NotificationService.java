package com.admission_crm.lead_management.Service.Notification;


import com.admission_crm.lead_management.Entity.Notification.Notification;
import com.admission_crm.lead_management.Feign.AuthClient;
import com.admission_crm.lead_management.Repository.NotificationRepository;
import com.admission_crm.lead_management.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final AuthClient authClient;
    private final EmailService emailService;

    public Notification sendNotification(String userId, String title, String message,
                                         Notification.NotificationType type,
                                         Notification.NotificationPriority priority) {

        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setPriority(priority);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        Notification saved = notificationRepository.save(notification);

        // Send Email (optional)
        try {
//            var counselor = authClient.getCounsellorDetailsById(userId);
//            emailService.sendEmail(
//                    counselor.getEmail(),
//                    title,
//                    message
//            );
        } catch (Exception e) {
            System.err.println("Failed to send email notification: " + e.getMessage());
        }

        return saved;
    }

    public List<Notification> getNotifications(String userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public void markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }
}
