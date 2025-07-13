package com.admissioncrm.authenticationservice.Controllers;

import com.admissioncrm.authenticationservice.DTO.ForgetPassword.ForgetPasswordDTO;
import com.admissioncrm.authenticationservice.DTO.Jwt.JwtResponse;
import com.admissioncrm.authenticationservice.DTO.LoginRequest;
import com.admissioncrm.authenticationservice.DTO.Register.RegisterRequest;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;
import com.admissioncrm.authenticationservice.Repositories.UserRepository;
import com.admissioncrm.authenticationservice.Services.AuthenticationService;
import com.admissioncrm.authenticationservice.Services.ForgetPasswordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final UserRepository userRepository;
    AuthenticationService authenticationService;
    private final ForgetPasswordService forgetPasswordService;

    AuthController(AuthenticationService authenticationService, UserRepository userRepository,ForgetPasswordService forgetPasswordService) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.forgetPasswordService = forgetPasswordService;
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.loginUser(loginRequest);
    }

    @PostMapping("/s/register")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody RegisterRequest request) {
        return authenticationService.registerStudent(request);
    }
    @PostMapping("/request-password-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
       return forgetPasswordService.passwordResetRequest(email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO) {

        return forgetPasswordService.resetPassword(forgetPasswordDTO.getToken(), forgetPasswordDTO.getNewPassword());
    }



    // Get Current User
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        String username = authentication.getName();
        System.out.println("Current user: " + username);

        // Find user from a database
        Optional<User> userOpt = userRepository.findByEmail(username);

        User user = null;

        if (userOpt.isPresent()) {
            user = userOpt.get();
        }

        userOpt = userRepository.findByMobileNumber(username);
        if(userOpt.isPresent()){
            user = userOpt.get();
        }

        userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()){
            user = userOpt.get();
        }

        // Create a safe response map
        Map<String, Object> response = new HashMap<>();
        assert user != null;
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("firstName", user.getFirstName());
        response.put("middleName", user.getMiddleName());
        response.put("lastName", user.getLastName());
        response.put("mobileNumber", user.getMobileNumber());
        response.put("fullName", (user.getFirstName() + " " +
                (user.getMiddleName() != null ? user.getMiddleName() + " " : "") +
                user.getLastName()).trim());
        response.put("role", user.getRole().name());
        response.put("createdAt", user.getCreatedAt());
        response.put("updatedAt", user.getUpdatedAt());

        return ResponseEntity.ok(response);
    }

    //testing endpoints

    @GetMapping("/test")
    public String test(HttpServletRequest request, Principal principal) {
        return "Logged in as: " + principal.getName();
    }

    @GetMapping("/home")
    public String studentDash() {
        return "Welcome to the Admission CRM Authentication Service";
    }

    @PreAuthorize("hasRole('INSTITUTE_ADMIN')")
    @GetMapping("/admin")
    public String helloADMIN() {
        return "Hello Admin";
    }

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @GetMapping("/uniadmin")
    public String uniAdmin() {
        return "Hello Super Admin";
    }
}
