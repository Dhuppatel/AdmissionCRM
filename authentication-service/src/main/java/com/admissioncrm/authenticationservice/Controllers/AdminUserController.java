package com.admissioncrm.authenticationservice.Controllers;

import com.admissioncrm.authenticationservice.DTO.UserCreationDTO.CreateUserRequest;
import com.admissioncrm.authenticationservice.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @PostMapping("/create-institue-admin")
    public ResponseEntity<?> createInstitueAdmin (@Valid @RequestBody CreateUserRequest request){

            return ResponseEntity.ok(userService.createInstituteAdmin(request));

    }

    @PreAuthorize("hasAnyRole('UNIVERSITY_ADMIN', 'INSTITUTE_ADMIN')")
    @PostMapping("/create-counsellor")
    public ResponseEntity<?> createCounsellor(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createCounsellor(request));
    }

}
