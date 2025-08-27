package com.admissioncrm.authenticationservice.Controllers;

import com.admissioncrm.authenticationservice.DTO.InstituteAdminDTO;
import com.admissioncrm.authenticationservice.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth/institute_admin")
public class InstituteAdminController {
    private final UserService userService;

    public InstituteAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getall")
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN') or hasRole('INSTITUTE_ADMIN')")
    public ResponseEntity<List<InstituteAdminDTO>> getAllInstituteAdmins() {
        List<InstituteAdminDTO> instituteAdmins = userService.getAllInstituteAdmins();
        return ResponseEntity.ok(instituteAdmins);
    }

}
