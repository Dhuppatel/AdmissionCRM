package com.admissioncrm.applicationmgmtservice.Feign;

import com.admissioncrm.applicationmgmtservice.Dto.Counsellor.CounsellorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "LEAD-SERVICE",configuration = FeignConfig.class)
public interface LeadFeign {
    @GetMapping("/api/leads/institutions/{id}/getname")
    public String getInstituteName(@PathVariable  String id);

    @GetMapping("/api/leads/programs/{id}/getname")
    public String getProgramName(@PathVariable String id);
}
