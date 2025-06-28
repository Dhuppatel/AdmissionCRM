package com.admission_crm.lead_management.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.type.PhoneNumber;

@Service
public class WhatsAppService {
    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    public WhatsAppService() {
        Twilio.init(accountSid, authToken);
    }

    public void sendWhatsAppMessage(String toPhoneNumber, String message) {
        Message.creator(
                new PhoneNumber("whatsapp : " + toPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                message
        ).create();
    }
}
