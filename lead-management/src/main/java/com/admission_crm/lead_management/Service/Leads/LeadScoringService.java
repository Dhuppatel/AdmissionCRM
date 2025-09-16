package com.admission_crm.lead_management.Service.Leads;

import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class LeadScoringService {

    public Double calculateLeadScore(Lead lead) {
        double score = 0.0;

        if (lead == null) {
            System.out.println("Lead is null in calculateLeadScore");
            throw new IllegalArgumentException("Lead cannot be null");
        }

        // Source scoring
        score += getSourceScore(lead.getLeadSource());

        // Priority scoring
        score += lead.getPriority().getValue() * 10;

        // Urgency based on creation time
        if (lead.getCreatedAt() != null) {
            long hoursOld = ChronoUnit.HOURS.between(lead.getCreatedAt(), LocalDateTime.now());
            if (hoursOld > 24) score += 15; // Older leads get higher priority
            if (hoursOld > 72) score += 25; // Very old leads get much higher priority
        } else {
            System.out.println("createdAt is null for lead: {}"+ lead.getId());
            score += 0; // Default score or handle differently
        }
//        score += getBudgetScore(lead.getBudgetRange());

        score += getQualificationScore(lead.getQualification());

        return Math.min(score, 100.0);
    }

    private double getSourceScore(LeadSource source) {
        return switch (source) {
            case REFERRAL -> 30.0;
            case WEBSITE -> 25.0;
            case WALK_IN -> 20.0;
            case PHONE_CALL -> 15.0;
            case SOCIAL_MEDIA -> 10.0;
            case ADVERTISEMENT -> 8.0;
            case EMAIL_CAMPAIGN -> 5.0;
            default -> 0.0;
        };
    }

    private double getBudgetScore(String budgetRange) {
        if (budgetRange == null) return 0.0;

        return switch (budgetRange.toLowerCase()) {
            case "high", "premium" -> 25.0;
            case "medium" -> 15.0;
            case "low" -> 5.0;
            default -> 0.0;
        };
    }

    private double getQualificationScore(String qualification) {
        if (qualification == null) return 0.0;

        String qual = qualification.toLowerCase();
        if (qual.contains("graduate") || qual.contains("bachelor")) return 10.0;
        if (qual.contains("master") || qual.contains("post")) return 15.0;
        if (qual.contains("phd") || qual.contains("doctorate")) return 20.0;
        return 5.0;
    }
}
