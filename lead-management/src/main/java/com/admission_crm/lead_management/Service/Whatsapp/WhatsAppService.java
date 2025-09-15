package com.admission_crm.lead_management.Service.Whatsapp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class WhatsAppService {

    private static final Logger logger = LoggerFactory.getLogger(WhatsAppService.class);


    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.whatsapp-number}")
    private String fromWhatsAppNumber; // e.g. "whatsapp:+14155238886"

    @Value("${twilio.lead.creation_acknowledgement}")
    private String leadCreatio_ack_sid; // template Content SID from Twilio

    @Value("${twilio.lead.assignment_notification}")
    private String assignmentNotification_sid;

    public WhatsAppService() {
        // Empty constructor to prevent initialization issues
    }
    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
        logger.info("✅ Twilio initialized with SID {}", accountSid);
    }

    public void sendLeadAcknowledgement(String toNumber, String name, String program,
                                        String institution) {


        String university_Contact_number = "+1234567890"; // Replace with actual contact number
        // Variables in same order as your template
        List<String> templateVariables = Arrays.asList(
                name,
                program,
                institution,
                university_Contact_number//use counsellor contact number here in fututure
        );

        Message message = Message.creator(
                        new PhoneNumber("whatsapp:" +"+91" +toNumber),  // recipient (lead)
                        new PhoneNumber("whatsapp:"+ fromWhatsAppNumber),      // your Twilio WhatsApp number
                        ""                                        // body is empty when using template
                )
                .setContentSid(leadCreatio_ack_sid)                       // approved template SID
                .setContentVariables(buildVariablesJson(templateVariables))
                .create();

        System.out.println("WhatsApp Template message sent with SID: " + message.getSid());
    }

    private String buildVariablesJson(List<String> vars) {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < vars.size(); i++) {
            sb.append("\"").append(i + 1).append("\":\"").append(vars.get(i)).append("\"");
            if (i < vars.size() - 1) sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }


    public void sendWhatsAppMessage(String to, String message) {
        try {
            if (accountSid.isEmpty() || authToken.isEmpty() || fromWhatsAppNumber.isEmpty()) {
                logger.error("Twilio configuration missing: SID={}, Token={}, From={}",
                        accountSid.isEmpty() ? "missing" : "present",
                        authToken.isEmpty() ? "missing" : "present",
                        fromWhatsAppNumber.isEmpty() ? "missing" : "present");
                throw new IllegalStateException("Twilio configuration is not properly set in application.properties");
            }

            Twilio.init(accountSid, authToken);
            Message.creator(
                    new PhoneNumber("whatsapp:+91" + to),
                    new PhoneNumber("whatsapp:" + fromWhatsAppNumber),
                    message
            ).create();
            logger.info("WhatsApp message sent to {}", to);
        } catch (Exception e) {
            logger.error("Failed to send WhatsApp message to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send WhatsApp message", e);
        }
    }

    public void sendLeadAssignmentNotification(String toNumber,
                                               String studentName,
                                               String programName,
                                               String institutionName,
                                               String counselorName,
                                               String counselorContact) {

        // List of variables in order of template placeholders {{1}}, {{2}}, ...
        List<String> templateVariables = Arrays.asList(
                studentName,        // {{1}}
                programName,        // {{2}}
                institutionName,    // {{3}}
                counselorName,      // {{4}}
                counselorContact    // {{5}}
        );

        try {
            if (accountSid.isEmpty() || authToken.isEmpty() || fromWhatsAppNumber.isEmpty()) {
                logger.error("Twilio configuration missing: SID={}, Token={}, From={}",
                        accountSid.isEmpty() ? "missing" : "present",
                        authToken.isEmpty() ? "missing" : "present",
                        fromWhatsAppNumber.isEmpty() ? "missing" : "present");
                throw new IllegalStateException("Twilio configuration is not properly set in application.properties");
            }

            Twilio.init(accountSid, authToken);

            Message message = Message.creator(
                            new PhoneNumber("whatsapp:+91" + toNumber),  // student number
                            new PhoneNumber("whatsapp:"+fromWhatsAppNumber),      // your Twilio WhatsApp number
                            ""                                        // body empty for template
                    )
                    .setContentSid(assignmentNotification_sid) // replace with Twilio template SID
                    .setContentVariables(buildVariablesJson(templateVariables))
                    .create();

            logger.info("WhatsApp lead assignment message sent to {} with SID: {}", toNumber, message.getSid());

        } catch (Exception e) {
            logger.error("Failed to send lead assignment WhatsApp message to {}: {}", toNumber, e.getMessage());
            throw new RuntimeException("Failed to send WhatsApp message", e);
        }
    }



}