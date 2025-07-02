package com.admissioncrm.applicationmgmtservice.Services;

import com.admissioncrm.applicationmgmtservice.Dto.Counsellor.CounsellorDTO;
import com.admissioncrm.applicationmgmtservice.Feign.AuthFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CounsellorService {
    private final AuthFeign authFeign;
    private final RestTemplate restTemplate;
    private final ApplicationFormService applicationService; // To get application counts



    @Value("${auth.service.url}")
    private String authServiceUrl;

    public List<CounsellorDTO> getAllCounsellors() {

          List<CounsellorDTO> counsellors = authFeign.getAllCounsellors();

          return counsellors;

    }

    public CounsellorDTO getCounsellorById(String counsellorId) {
        try {
            CounsellorDTO counsellor = restTemplate.getForObject(
                    authServiceUrl + "/api/counsellors/" + counsellorId,
                    CounsellorDTO.class
            );



            return counsellor;
        } catch (Exception e) {
            log.error("Error fetching counsellor by id: " + counsellorId, e);
            return null;
        }
    }

}