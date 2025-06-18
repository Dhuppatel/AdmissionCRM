package com.admissioncrm.authenticationservice.Controllers;

import com.admissioncrm.authenticationservice.DTO.Jwt.JwtResponse;
import com.admissioncrm.authenticationservice.DTO.LoginRequest;
import com.admissioncrm.authenticationservice.DTO.student.StudentLoginRequest;
import com.admissioncrm.authenticationservice.DTO.student.StudentRegistrationRequest;
import com.admissioncrm.authenticationservice.Services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/auth")
@RestController
public class AuthController {
    AuthenticationService authenticationService;
    AuthController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;

    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest){

        return authenticationService.loginUser(loginRequest);
    }

    @PostMapping("/student/register")
    public ResponseEntity<?> registerStudent(@Valid  @RequestBody StudentRegistrationRequest request)
    {
        //cheak exception handeling in this method
       JwtResponse jwtResponse=authenticationService.registerStudent(request);

            return ResponseEntity.ok(jwtResponse);
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
