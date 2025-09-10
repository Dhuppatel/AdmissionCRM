package com.admission_crm.lead_management.Feign;

import com.admission_crm.lead_management.Payload.CounsellorDTO;
import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AUTHENTICATION-SERVICE")
public interface AuthClient {

    @GetMapping("api/auth/counsellor/{counsellorId}")
    CounsellorDTO getCounsellorDetailsById(@PathVariable String counsellorId);
}
