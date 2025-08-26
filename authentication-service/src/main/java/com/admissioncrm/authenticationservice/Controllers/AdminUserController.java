package com.admissioncrm.authenticationservice.Controllers;

import com.admissioncrm.authenticationservice.DTO.ApiResponse;
import com.admissioncrm.authenticationservice.DTO.UserCreationDTO.CreateUserRequest;
import com.admissioncrm.authenticationservice.DTO.UserResponseDTO;
import com.admissioncrm.authenticationservice.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @PostMapping("/i/create")
    public ResponseEntity<?> createInstitueAdmin ( @RequestBody CreateUserRequest request){

            return ResponseEntity.ok(userService.createInstituteAdmin(request));

    }

    @PreAuthorize("hasAnyRole('UNIVERSITY_ADMIN', 'INSTITUTE_ADMIN')")
    @PostMapping("/c/create")
    public ResponseEntity<?> createCounsellor(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createCounsellor(request));
    }

    @GetMapping("/getall")
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllAdmins() {
        List<UserResponseDTO> admins = userService.getAdmins();
        return ResponseEntity.ok(admins);
    }

    // ✅ Get all Institute Admins
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    @GetMapping("/i-admin/getall")
    public ResponseEntity<?> getAllInstituteAdmins() {
        List<UserResponseDTO> instituteAdmins = userService.getInstituteAdmins();
        return ResponseEntity.ok().body(ApiResponse.success("Institute Admins retrived successfully", instituteAdmins));
    }

    // ✅ Get all Counsellors
    @PreAuthorize("hasAnyRole('UNIVERSITY_ADMIN','INSTITUTE_ADMIN')")
    @GetMapping("/c/getall")
    public ResponseEntity<?> getAllCounsellors() {
        List<UserResponseDTO> counsellors = userService.getCounsellors();
        return ResponseEntity.ok(ApiResponse.success("Councellors Retrived Succesfully" ,counsellors));
    }



}
