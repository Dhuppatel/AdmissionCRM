package com.admissioncrm.authenticationservice.Controllers;

import com.admissioncrm.authenticationservice.DTO.Jwt.JwtResponse;
import com.admissioncrm.authenticationservice.DTO.LoginRequest;
import com.admissioncrm.authenticationservice.DTO.Register.RegisterRequest;
import com.admissioncrm.authenticationservice.Services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    AuthenticationService authenticationService;
    AuthController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;

    }
    // login
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login( @RequestBody LoginRequest loginRequest){

        return authenticationService.loginUser(loginRequest);
    }

    @PostMapping("/s/register")
    public ResponseEntity<?> registerStudent( @RequestBody RegisterRequest request)
    {
        return authenticationService.registerStudent(request);


    }
    //testing endpoints

    @GetMapping("/test")
    public String test(HttpServletRequest request, Principal principal) {
        return "Logged in as: " + principal.getName();
    }

    @GetMapping("/home")
    public String studentDash()
    {
        return "Welcome to the Admission CRM Authentication Service";
    }
    @PreAuthorize("hasRole('INSTITUTE_ADMIN')")
    @GetMapping("/admin")
    public String helloADMIN(){
        return "Hello Admin";
    }
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @GetMapping("/uniadmin")
    public String uniAdmin(){
        return "Hello Super Admin";
    }
}
