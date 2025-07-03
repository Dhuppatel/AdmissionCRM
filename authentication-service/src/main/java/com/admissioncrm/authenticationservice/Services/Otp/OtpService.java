package com.admissioncrm.authenticationservice.Services.Otp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${twilio.account-sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth-token}")
    private String twilioAuthToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    @Value("${spring.mail.username}")
    private String fromEmail;

    // In-memory storage for OTPs (use Redis in production)
    private final ConcurrentHashMap<String, OtpData> otpStorage = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static final int OTP_LENGTH = 4;
    private static final int OTP_EXPIRY_MINUTES = 5;


    @jakarta.annotation.PostConstruct
    public void initTwilio() {
        System.out.println("Twilio SID: " + twilioAccountSid);
        System.out.println("Twilio Token: " + twilioAuthToken);
        System.out.println("Twilio From: " + twilioPhoneNumber);
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }
    //Send SMS otp
    public String sendSmsOtp( String toPhoneNumber) {
        String otp = generateOtp();
        String key = generateKey(toPhoneNumber);
        String messageBody = "Your OTP is: " + otp + ". This OTP will expire in " + OTP_EXPIRY_MINUTES + " minutes.";

        // Store OTP with expiry time
        otpStorage.put(key, new OtpData(otp, System.currentTimeMillis() + (OTP_EXPIRY_MINUTES * 60 * 1000)));

        //  cleanup scheduling
        scheduler.schedule(() -> otpStorage.remove(key), OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);

        System.out.println("Sending OTP to: " + toPhoneNumber + " with identifier: " + key);

        try {
            // Format phone number properly
            String formattedNumber = toPhoneNumber.startsWith("+91") ? toPhoneNumber : "+91" + toPhoneNumber;

            Message message = Message.creator(
                    new PhoneNumber(formattedNumber),
                    new PhoneNumber(twilioPhoneNumber),
                    messageBody
            ).create();

            System.out.println("SMS sent successfully. SID: " + message.getSid());
            return otp; //return the otp

        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
            // Remove OTP from storage if SMS failed
            otpStorage.remove(key);
            throw new RuntimeException("Failed to send SMS OTP: " + e.getMessage());
        }
    }



    public String sendEmailOtp( String email) {
        String otp = generateOtp();

        String key = generateKey(email);

        // Store OTP with expiry
        otpStorage.put(key, new OtpData(otp, System.currentTimeMillis() + (OTP_EXPIRY_MINUTES * 60 * 1000)));

        // Schedule OTP cleanup
        scheduler.schedule(() -> otpStorage.remove(key), OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);

        // Send OTP via Email
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject("Your OTP for Login");
            message.setText("Your OTP for login is: " + otp + "\n\n" +
                    "This OTP will expire in " + OTP_EXPIRY_MINUTES + " minutes.\n\n" +
                    "If you didn't request this OTP, please ignore this email.");

            mailSender.send(message);
            System.out.println("Email sent successfully to: " + email);
            return otp;
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            // Remove OTP from storage if email failed
            otpStorage.remove(key);
            throw new RuntimeException("Failed to send email OTP: " + e.getMessage());
        }
    }

    // verify OTP || both sms and email otp would be verified here
    public boolean verifyOtp(String identifier, String providedOtp) {
        String key = generateKey(identifier);
        OtpData otpData = otpStorage.get(key);

        if (otpData == null) {
            return false; // OTP not found
        }

        if (System.currentTimeMillis() > otpData.getExpiryTime()) {
            otpStorage.remove(key); // Remove expired OTP
            return false; // OTP expired
        }

        if (otpData.getOtp().equals(providedOtp)) {
            otpStorage.remove(key); // Remove used OTP
            return true;
        }

        return false; // Invalid OTP
    }

    //utills
    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    private String generateKey(String identifier) {
        return "otp_" + identifier;
    }

    // Inner class to store OTP data
    private static class OtpData {
        private final String otp;
        private final long expiryTime;

        public OtpData(String otp, long expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        public String getOtp() {
            return otp;
        }

        public long getExpiryTime() {
            return expiryTime;
        }
    }
}