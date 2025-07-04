package com.admissioncrm.authenticationservice.Services.Otp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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


    public String sendEmailOtp(String email) {
        String otp = generateOtp();
        String key = generateKey(email);

        // Store OTP with expiry
        otpStorage.put(key, new OtpData(otp, System.currentTimeMillis() + (OTP_EXPIRY_MINUTES * 60 * 1000)));

        // Schedule OTP cleanup
        scheduler.schedule(() -> otpStorage.remove(key), OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);

        // Send OTP via Email with creative HTML template
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject("🔐 Your AdmissionCRM Security Code");

            helper.setText(createOtpEmailTemplate(otp), true);

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


    private String createOtpEmailTemplate(String otp) {
        return String.format("""
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body { 
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            margin: 0; 
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%);
            min-height: 100vh;
        }
        .container { 
            max-width: 480px; 
            margin: 0 auto; 
            background: white;
            border-radius: 16px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        .header { 
            background: linear-gradient(135deg, #4f46e5 0%%, #06b6d4 100%%);
            padding: 30px 20px;
            text-align: center;
        }
        .brand { 
            color: white;
            font-size: 24px;
            font-weight: 700;
            margin: 0;
        }
        .content { 
            padding: 40px 30px;
            text-align: center;
        }
        .title { 
            font-size: 20px;
            color: #1f2937;
            margin: 0 0 10px;
            font-weight: 600;
        }
        .subtitle { 
            color: #6b7280;
            font-size: 14px;
            margin: 0 0 30px;
        }
        .otp-box { 
            background: linear-gradient(135deg, #f0f9ff 0%%, #e0f2fe 100%%);
            border: 2px solid #0ea5e9;
            border-radius: 12px;
            padding: 25px;
            margin: 20px 0;
        }
        .otp-code { 
            font-size: 48px;
            font-weight: 800;
            color: #0c4a6e;
            font-family: 'Courier New', monospace;
            letter-spacing: 6px;
            margin: 0;
            text-shadow: 0 2px 4px rgba(12,74,110,0.1);
        }
        .expiry { 
            background: #fef3c7;
            color: #92400e;
            padding: 12px 20px;
            border-radius: 8px;
            font-size: 13px;
            font-weight: 500;
            margin: 20px 0;
            border-left: 4px solid #f59e0b;
        }
        .footer { 
            background: #f9fafb;
            padding: 20px;
            text-align: center;
            color: #6b7280;
            font-size: 12px;
            border-top: 1px solid #e5e7eb;
        }
        .highlight { 
            color: #4f46e5;
            font-weight: 600;
        }
        @media (max-width: 500px) {
            .container { margin: 10px; }
            .otp-code { font-size: 40px; letter-spacing: 4px; }
            .content { padding: 30px 20px; }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1 class="brand">🎓 AdmissionCRM</h1>
        </div>
        
        <div class="content">
            <h2 class="title">Your Login Code</h2>
            <p class="subtitle">Enter this code to access your account</p>
            
            <div class="otp-box">
                <div class="otp-code">%s</div>
            </div>
            
            <div class="expiry">
                ⏰ Expires in %d minutes
            </div>
            
            <p style="color: #6b7280; font-size: 13px; margin-top: 25px;">
                Didn't request this? <span class="highlight">Ignore this email</span>
            </p>
        </div>
        
        <div class="footer">
            © 2024 <strong>AdmissionCRM</strong> • Secure Authentication
        </div>
    </div>
</body>
</html>
""", otp, (int) OTP_EXPIRY_MINUTES);
    }

}