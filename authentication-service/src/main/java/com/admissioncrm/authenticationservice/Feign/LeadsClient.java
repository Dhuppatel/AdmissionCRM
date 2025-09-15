package com.admissioncrm.authenticationservice.Feign;


import com.admissioncrm.authenticationservice.DTO.AdminAssignment.AssignAdminRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "LEAD-SERVICE")
public interface LeadsClient {

    @PostMapping("/api/leads/institutions/{institutionId}/assign-admin")
    ResponseEntity<String> assignAdminToInstitute(@PathVariable("institutionId") String institutionId,
                                       @RequestBody AssignAdminRequest request );
    @PostMapping("/api/leads/institutions/{institutionId}/assign-counsellor")

    ResponseEntity<String> assingCounsellorToInstitute(@PathVariable ("institutionId") String institutionId,
                                                       @RequestBody AssignAdminRequest request );


}
