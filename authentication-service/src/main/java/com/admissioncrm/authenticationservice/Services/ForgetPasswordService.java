package com.admissioncrm.authenticationservice.Services;

import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;
import com.admissioncrm.authenticationservice.Entities.PasswordResetToken;
import com.admissioncrm.authenticationservice.Repositories.PasswordResetTokenRepository;
import com.admissioncrm.authenticationservice.Repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Service
public class ForgetPasswordService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${frontend.url}")
    private String frontendUrl;

    public ResponseEntity<String> passwordResetRequest(@RequestParam String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Delete any existing tokens for this user
            tokenRepository.deleteAll(tokenRepository.findByUser(user));
            
            String token = UUID.randomUUID().toString();

            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
            tokenRepository.save(resetToken);

            String resetLink = frontendUrl+"/reset-password?token=" + token;

            sendEmail(email,resetLink);


            return ResponseEntity.ok("Reset link sent to your email.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        }
    }

    private void sendEmail(String toEmail, String resetLink) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setFrom(fromEmail);
            helper.setSubject("🔐 Password Reset - AdmissionCRM");

            String content = "<p>Hello,</p>" +
                    "<p>We received a request to reset your password.</p>" +
                    "<p><a href=\"" + resetLink + "\">Click here to reset your password</a></p>" +
                    "<p>This link will expire in 15 minutes.</p>" +
                    "<br><p>If you did not request a reset, please ignore this email.</p>";

            helper.setText(content, true); // true enables HTML

            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }


    public ResponseEntity<?> resetPassword(String token,  String newPassword) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);

        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }

        PasswordResetToken resetToken = tokenOptional.get();

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Optional: delete token after use
        tokenRepository.delete(resetToken);

        return ResponseEntity.ok("Password reset successful.");
    }


}
