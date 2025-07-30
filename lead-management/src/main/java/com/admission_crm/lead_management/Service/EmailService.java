package com.admission_crm.lead_management.Service;

import com.admission_crm.lead_management.Entity.LeadManagement.Lead;
import com.admission_crm.lead_management.Entity.LeadManagement.LeadStatus;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final WhatsAppService whatsAppService;

    @Value("${spring.mail.from}")
    private String fromEmail;

    public void sendWelcomeMail(Lead lead) throws MessagingException {
        String template = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Welcome to Our Admission Process</title><style type=\"text/css\">body { margin: 0; padding: 0; font-family: 'Helvetica', Arial, sans-serif; background-color: #f9f9f9; }.container { width: 100%; max-width: 650px; margin: 0 auto; background-color: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }.header { background: linear-gradient(45deg, #FF6F61, #00C4CC); color: #ffffff; padding: 30px 20px; text-align: center; }.timeline { position: relative; padding: 20px; }.timeline::before { content: ''; position: absolute; left: 20px; top: 0; bottom: 0; width: 2px; background-color: #00C4CC; }.timeline-item { position: relative; padding-left: 40px; margin-bottom: 20px; }.timeline-item::before { content: '✔'; position: absolute; left: 10px; top: 0; color: #FF6F61; font-size: 18px; }.content { color: #333333; padding: 0 20px 20px; }.button { display: inline-block; padding: 12px 25px; background-color: #00C4CC; color: #ffffff; text-decoration: none; border-radius: 25px; font-weight: bold; transition: background-color 0.3s; }.button:hover { background-color: #009BA7; }.footer { background-color: #f9f9f9; padding: 15px; text-align: center; font-size: 12px; color: #666666; }@media only screen and (max-width: 600px) { .container { width: 100% !important; }.timeline { padding: 10px; }.timeline::before { left: 10px; }.timeline-item { padding-left: 30px; }.timeline-item::before { left: 0; }.content { padding: 0 10px 10px; }}</style></head><body><table class=\"container\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"header\"><h1>Admission Journey</h1></td></tr><tr><td class=\"timeline\"><div class=\"timeline-item\"><h3>Welcome to Your Journey</h3><p>Your application has been successfully received!</p></div></td></tr><tr><td class=\"content\"><p><strong>Hello, " + lead.getFullName() + "!</strong></p><p>Thank you for choosing us. We’re excited to guide you through the admission process. Stay tuned for updates!</p><p><strong>Details:</strong><br>Email: " + lead.getEmail() + "<br>Phone: " + lead.getPhone() + "</p></td></tr><tr><td class=\"footer\"><p>© 2025 Admission CRM. All rights reserved.<br><a href=\"mailto:support@admissioncrm.com\">support@admissioncrm.com</a></p></td></tr></table></body></html>";
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        helper.setFrom(fromEmail);
        helper.setTo(lead.getEmail());
        helper.setSubject("Welcome to Our Admission Process");
        String body = String.format(
                "<h1>Welcome, %s!</h1>" +
                        "<p>Thank you for your interest in our institution. Your application has been received, and we will keep you updated on the next steps.</p>" +
                        "<p>Contact us at %s for any questions.</p>",
                lead.getFullName(), fromEmail);
        helper.setText(template, true);
        mailSender.send(msg);

//        whatsAppService.sendWhatsAppMessage(lead.getPhone(), "Hello " + lead.getFullName() + "Thank you for your interest in our institution. Your application has been received, and we will keep you updated on the next steps." + lead.getId());
    }

    public void sendLeadAssignmentEmail(Lead lead, String counselorEmail) throws MessagingException {
        String template = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Your Application: Counselor Assigned</title><style type=\"text/css\">body { margin: 0; padding: 0; font-family: 'Helvetica', Arial, sans-serif; background-color: #f9f9f9; }.container { width: 100%; max-width: 650px; margin: 0 auto; background-color: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }.header { background: linear-gradient(45deg, #FF6F61, #00C4CC); color: #ffffff; padding: 30px 20px; text-align: center; }.timeline { position: relative; padding: 20px; }.timeline::before { content: ''; position: absolute; left: 20px; top: 0; bottom: 0; width: 2px; background-color: #00C4CC; }.timeline-item { position: relative; padding-left: 40px; margin-bottom: 20px; }.timeline-item::before { content: '👤'; position: absolute; left: 10px; top: 0; color: #FF6F61; font-size: 18px; }.content { color: #333333; padding: 0 20px 20px; }.button { display: inline-block; padding: 12px 25px; background-color: #00C4CC; color: #ffffff; text-decoration: none; border-radius: 25px; font-weight: bold; transition: background-color 0.3s; }.button:hover { background-color: #009BA7; }.footer { background-color: #f9f9f9; padding: 15px; text-align: center; font-size: 12px; color: #666666; }@media only screen and (max-width: 600px) { .container { width: 100% !important; }.timeline { padding: 10px; }.timeline::before { left: 10px; }.timeline-item { padding-left: 30px; }.timeline-item::before { left: 0; }.content { padding: 0 10px 10px; }}</style></head><body><table class=\"container\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"header\"><h1>Admission Journey</h1></td></tr><tr><td class=\"timeline\"><div class=\"timeline-item\"><h3>Counselor Assigned</h3><p>Your application is now with a dedicated counselor!</p></div></td></tr><tr><td class=\"content\"><p><strong>Hello, " + lead.getFullName() + "!</strong></p><p>Your application has been assigned to <a href=\"mailto:" + counselorEmail + "\">" + counselorEmail + "</a>. They will contact you soon.</p><p><strong>Details:</strong><br>Email: " + lead.getEmail() + "<br>Phone: " + lead.getPhone() + "</p></td></tr><tr><td class=\"footer\"><p>© 2025 Admission CRM. All rights reserved.<br><a href=\"mailto:support@admissioncrm.com\">support@admissioncrm.com</a></p></td></tr></table></body></html>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(lead.getEmail());
        helper.setSubject("Your Application: Counselor Assigned");
        String body = String.format(
                "<h1>Hello, %s!</h1>" +
                        "<p>Your application has been assigned to a counselor (%s). They will reach out to you soon to discuss your application.</p>" +
                        "<p>Contact us at %s for any questions.</p>",
                lead.getFullName(), counselorEmail, fromEmail);
        helper.setText(template, true);
        mailSender.send(message);

        whatsAppService.sendWhatsAppMessage(lead.getPhone(), "Hello " + lead.getFullName() + "! Your application is assigned to " + counselorEmail + "." + lead.getId());
    }

    public void sendStatusUpdateEmail(Lead lead, LeadStatus oldStatus, LeadStatus newStatus) throws MessagingException {
        String template = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Update: Your Application Status</title><style type=\"text/css\">body { margin: 0; padding: 0; font-family: 'Helvetica', Arial, sans-serif; background-color: #f9f9f9; }.container { width: 100%; max-width: 650px; margin: 0 auto; background-color: #ffffff; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }.header { background: linear-gradient(45deg, #FF6F61, #00C4CC); color: #ffffff; padding: 30px 20px; text-align: center; }.timeline { position: relative; padding: 20px; }.timeline::before { content: ''; position: absolute; left: 20px; top: 0; bottom: 0; width: 2px; background-color: #00C4CC; }.timeline-item { position: relative; padding-left: 40px; margin-bottom: 20px; }.timeline-item::before { content: '⏳'; position: absolute; left: 10px; top: 0; color: #FF6F61; font-size: 18px; }.content { color: #333333; padding: 0 20px 20px; }.button { display: inline-block; padding: 12px 25px; background-color: #00C4CC; color: #ffffff; text-decoration: none; border-radius: 25px; font-weight: bold; transition: background-color 0.3s; }.button:hover { background-color: #009BA7; }.footer { background-color: #f9f9f9; padding: 15px; text-align: center; font-size: 12px; color: #666666; }@media only screen and (max-width: 600px) { .container { width: 100% !important; }.timeline { padding: 10px; }.timeline::before { left: 10px; }.timeline-item { padding-left: 30px; }.timeline-item::before { left: 0; }.content { padding: 0 10px 10px; }}</style></head><body><table class=\"container\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"header\"><h1>Admission Journey</h1></td></tr><tr><td class=\"timeline\"><div class=\"timeline-item\"><h3>Status Updated</h3><p>Your application status has changed!</p></div></td></tr><tr><td class=\"content\"><p><strong>Hello, " + lead.getFullName() + "!</strong></p><p>Your application status has updated from <strong>" + oldStatus + "</strong> to <strong>" + newStatus + "</strong>.</p><p><strong>Details:</strong><br>Email: " + lead.getEmail() + "<br>Phone: " + lead.getPhone() + "</p></td></tr><tr><td class=\"footer\"><p>© 2025 Admission CRM. All rights reserved.<br><a href=\"mailto:support@admissioncrm.com\">support@admissioncrm.com</a></p></td></tr></table></body></html>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(lead.getEmail());
        helper.setSubject("Update: Your Application Status");
        String body = String.format(
                "<h1>Hello, %s!</h1>" +
                        "<p>Your application status has changed from <strong>%s</strong> to <strong>%s</strong>.</p>" +
                        "<p>Contact us at %s for any questions.</p>",
                lead.getFullName(), oldStatus, newStatus, fromEmail);
        helper.setText(template, true);
        mailSender.send(message);

        whatsAppService.sendWhatsAppMessage(lead.getPhone(), "Hello " + lead.getFullName() + "! Your status changed from " + oldStatus + " to " + newStatus + "." + lead.getId());
    }
}
