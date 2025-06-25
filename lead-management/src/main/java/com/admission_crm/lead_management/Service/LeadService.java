package com.admission_crm.lead_management.Service;

import com.admission_crm.lead_management.Entity.AnalyticsAndReporting.AuditLog;
import com.admission_crm.lead_management.Entity.CoreEntities.Institution;
import com.admission_crm.lead_management.Entity.CoreEntities.User;
import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import com.admission_crm.lead_management.Repository.AuditLogRepository;
import com.admission_crm.lead_management.Repository.InstitutionRepository;
import com.admission_crm.lead_management.Repository.LeadRepository;
import com.admission_crm.lead_management.Repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Deque;
import java.util.Optional;

@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private EmailService emailService;

//    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Transactional
    public Lead createLead(Lead lead, String userEmail) {
        lead.setStatus(LeadStatus.NEW);
        if (lead.getPriority() == null) {
            lead.setPriority(Lead.Priority.MEDIUM);
        }

        Lead savedLead = leadRepository.save(lead);

        Optional<Institution> institutionOpt = institutionRepository.findById(lead.getInstitutionId());
        if (institutionOpt.isPresent()) {
            Institution institution = institutionOpt.get();
            institution.getQueuedLeads().addLast(savedLead.getId());
            institutionRepository.save(institution);
        }

        logAudit(userEmail, "Created Lead", savedLead.getId(), "Lead", "Created lead: " + lead.getEmail());

        try {
            emailService.sendWelcomeMail(savedLead);
        } catch (MessagingException e) {
            logAudit(userEmail, "Email Failed", savedLead.getId(), "Lead", "Failed to send welcome email: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return savedLead;
    }

    @Transactional
    public Lead assignLeadToCounselor(String counselorId) {
        User counselor = userRepository.findById(counselorId)
                .orElseThrow(() -> new RuntimeException("Counselor not found"));

        // Check if counselor already has an active lead
        if (counselor.getCurrentLeadsCount() >= counselor.getMaxLeadsAssignment()) {
            throw new RuntimeException("Counselor already has maximum assigned leads");
        }

        // Check if user is a counselor and get their institution
        if (!counselor.isCounselor()) {
            throw new RuntimeException("User is not a counselor");
        }

        Optional<Institution> institutionOpt = institutionRepository.findById(counselor.getInstitutionId());
        if (institutionOpt.isEmpty()) {
            throw new RuntimeException("Institution not found");
        }

        Institution institution = institutionOpt.get();
        Deque<String> queuedLeads = institution.getQueuedLeads();

        // Get the first lead from the queue (FIFO)
        String leadId = queuedLeads.pollFirst();
        if (leadId == null) {
            throw new RuntimeException("No leads available in the queue");
        }

        // Update lead with counselor assignment
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        lead.setAssignedCounselor(counselorId);
        lead.setStatus(LeadStatus.ASSIGNED);

        // Update counselor's lead count
        counselor.setCurrentLeadsCount(counselor.getCurrentLeadsCount() + 1);
        counselor.getAssignedLeads().add(leadId);

        // Save changes
        leadRepository.save(lead);
        userRepository.save(counselor);
        institutionRepository.save(institution);

        logAudit(counselor.getEmail(), "Assigned Lead", leadId, "Lead", "Assigned lead to counselor: " + counselorId);

        try {
            emailService.sendLeadAssignmentEmail(lead, counselor.getFirstName());
        } catch (MessagingException e) {
            logAudit(counselor.getEmail(), "Email Failed", leadId, "Lead", "Failed to send assignment email: " + e.getMessage());
        }

        return lead;
    }

    @Transactional
    public void requeueLead(String leadId, Lead.Priority priority) {
        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new RuntimeException("Lead not found"));

        User currentUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!currentUser.getInstitutionId().equals(lead.getInstitutionId()) ||
                (!currentUser.isCounselor() && !currentUser.isInstituteAdmin())) {
            throw new RuntimeException("Unauthorized to requeue this lead");
        }

        if (lead.getAssignedCounselor() != null) {
            User counselor = userRepository.findById(lead.getAssignedCounselor())
                    .orElseThrow(() -> new RuntimeException("Counselor not found"));
            counselor.setCurrentLeadsCount(counselor.getCurrentLeadsCount() - 1);
            counselor.getAssignedLeads().remove(leadId);
            userRepository.save(counselor);

            lead.setAssignedCounselor(null);
            lead.setStatus(LeadStatus.NEW);
        }

        lead.setPriority(priority);
        Optional<Institution> institutionOpt = institutionRepository.findById(lead.getInstitutionId());
        if (institutionOpt.isPresent()) {
            Institution institution = institutionOpt.get();
            institution.getQueuedLeads().addLast(leadId);
            institutionRepository.save(institution);
        }

        leadRepository.save(lead);

        logAudit(currentUser.getEmail(), "Requeued Lead", leadId, "Lead", "Requeued lead with priority: " + priority);

        try {
            emailService.sendStatusUpdateEmail(lead, lead.getStatus(), LeadStatus.NEW);
        } catch (MessagingException e) {
            logAudit(currentUser.getEmail(), "Email Failed", leadId, "Lead", "Failed to send status update email: " + e.getMessage());
        }
    }

    @Transactional
    public Lead getLead(String leadId) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        // Verify user has permission to access this lead
        User currentUser = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(() -> new RuntimeException("User not found"));

        if (!currentUser.getInstitutionId().equals(lead.getInstitutionId()) ||
                (!currentUser.isCounselor() && !currentUser.isInstituteAdmin())) {
            throw new RuntimeException("Unauthorized to access this lead");
        }

        // Verify the lead is in the institution's queuedLeads or assigned to the counselor
        Optional<Institution> institutionOpt = institutionRepository.findById(lead.getInstitutionId());
        if (institutionOpt.isEmpty()) {
            throw new RuntimeException("Institution not found");
        }

        Institution institution = institutionOpt.get();
        if (!institution.getQueuedLeads().contains(leadId) &&
                !currentUser.getAssignedLeads().contains(leadId)) {
            throw new RuntimeException("Lead is not available in the institution's queue or assigned to you");
        }

        return lead;
    }

    private void logAudit(String userEmail, String action, String entityId, String entityType, String details) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setEntityId(entityId);
        auditLog.setEntityType(entityType);
        auditLogRepository.save(auditLog);
    }

    @Transactional
    public Lead changeLeadStatus(String leadId, LeadStatus newStatus) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        User currentUser = userRepository.findByUsername(
                        SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!currentUser.getInstitutionId().equals(lead.getInstitutionId()) ||
                (!currentUser.isCounselor() && !currentUser.isInstituteAdmin())) {
            throw new RuntimeException("Unauthorized to change lead status");
        }

        if (!institutionRepository.existsById(lead.getInstitutionId())) {
            throw new RuntimeException("Institution not found");
        }

        LeadStatus oldStatus = lead.getStatus();
        lead.setStatus(newStatus);
        leadRepository.save(lead);

        logAudit(currentUser.getEmail(), "Changed Lead Status", leadId, "Lead",
                String.format("Changed lead status from %s to %s", oldStatus, newStatus));

        try {
            emailService.sendStatusUpdateEmail(lead, oldStatus, newStatus);
        } catch (MessagingException e) {
            logAudit(currentUser.getEmail(), "Email Failed", leadId, "Lead", "Failed to send status update email: " + e.getMessage());
        }

        return lead;
    }
}
