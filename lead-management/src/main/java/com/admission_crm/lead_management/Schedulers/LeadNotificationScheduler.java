package com.admission_crm.lead_management.Schedulers;


import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import com.admission_crm.lead_management.Entity.Notification.Notification;
import com.admission_crm.lead_management.Repository.Leads.LeadRepository;
import com.admission_crm.lead_management.Repository.Leads.Activity.LeadActivityRepository;
import com.admission_crm.lead_management.Service.Notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LeadNotificationScheduler {

    private final LeadRepository leadRepository;
    private final LeadActivityRepository leadActivityRepository;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 9 * * *") // runs every day at 9 AM for test:"*/10 * * * * *
    public void notifyUntouchedLeads() {
        List<Lead> assignedLeads = leadRepository.findByStatus(LeadStatus.ASSIGNED);

        for (Lead lead : assignedLeads) {

            LocalDateTime lastActivity = leadActivityRepository.findLastActivityDateByLeadId(lead.getId());

            boolean untouched = (lastActivity == null && lead.getAssignedAt().isBefore(LocalDateTime.now().minusDays(2)))
                    || (lastActivity != null && lastActivity.isBefore(LocalDateTime.now().minusDays(7)));

            if (untouched) {
                String msg = "Lead " + lead.getFullName() +
                        " has been untouched for 2+ days. Please take action.";

                notificationService.sendNotification(
                        lead.getAssignedCounselor(),
                        "Untouched Lead Reminder",
                        msg,
                        Notification.NotificationType.REMINDER,
                        Notification.NotificationPriority.HIGH
                );
            }
        }
    }
}
