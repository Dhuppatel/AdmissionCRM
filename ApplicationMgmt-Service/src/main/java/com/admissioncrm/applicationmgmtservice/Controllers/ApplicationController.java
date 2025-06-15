package com.admissioncrm.applicationmgmtservice.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/application")
public class ApplicationController {


    @GetMapping("/student/check")
    public String check() {
        return "Success";
    }
}
