package com.admissioncrm.applicationmgmtservice.Feign;

import com.admissioncrm.applicationmgmtservice.Dto.Counsellor.CounsellorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "AUTHENTICATION-SERVICE",configuration = FeignConfig.class)
public interface AuthFeign {
    @GetMapping("/api/counsellor/getall")
    public List<CounsellorDTO> getAllCounsellors();
}
