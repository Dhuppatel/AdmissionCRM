package com.admissioncrm.authenticationservice.Services.Otp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
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

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Value("${spring.mail.username}")
    private String fromEmail;

    // In-memory storage for OTPs
    private final ConcurrentHashMap<String, OtpData> otpStorage = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static final int OTP_LENGTH = 4;
    private static final int OTP_EXPIRY_MINUTES = 5;


    @PostConstruct // postconstruct ensure that this will be called after all the dependency injections are done
    public void initTwilio() {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    public void sendOtp(String identifier, String mobileNumber, String email) {
        String otp = generateOtp();
        String key = generateKey(identifier);

        // Store OTP with expiry
        otpStorage.put(key, new OtpData(otp, System.currentTimeMillis() + (OTP_EXPIRY_MINUTES * 60 * 1000)));

        // Schedule OTP cleanup
        scheduler.schedule(() -> otpStorage.remove(key), OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);

        // Send OTP via SMS if mobile number is available
        if (mobileNumber != null && !mobileNumber.isEmpty()) {
            sendSmsOtp(mobileNumber, otp);
        }

        // Send OTP via Email if email is available
        if (email != null && !email.isEmpty()) {
            sendEmailOtp(email, otp);
        }
    }

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

    private void sendSmsOtp(String mobileNumber, String otp) {
        try {
            // Format mobile number (assuming Indian numbers, adjust as needed)
            String formattedNumber = mobileNumber.startsWith("+91") ? mobileNumber : "+91" + mobileNumber;

            Message message = Message.creator(
                    new PhoneNumber(formattedNumber),
                    new PhoneNumber(twilioPhoneNumber),
                    "Your OTP for login is: " + otp + ". This OTP will expire in " + OTP_EXPIRY_MINUTES + " minutes."
            ).create();

            System.out.println("SMS sent successfully. SID: " + message.getSid());
        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
            // Log the error but don't throw exception to allow email OTP to be sent
        }
    }

    private void sendEmailOtp(String email, String otp) {
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
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            // Log the error but don't throw exception
        }
    }

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