package com.admissioncrm.authenticationservice.Services.Counselors;

import com.admissioncrm.authenticationservice.DTO.ApiResponse;
import com.admissioncrm.authenticationservice.DTO.Counselor.CounselorDTO;
import com.admissioncrm.authenticationservice.DTO.InstituteResponse;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;
import com.admissioncrm.authenticationservice.Entities.CounselorDetails;
import com.admissioncrm.authenticationservice.Repositories.CounselorDetailsRepository;
import com.admissioncrm.authenticationservice.Services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CounselorService {

    private final CounselorDetailsRepository counselorDetailsRepository;
    private final RestTemplate restTemplate;

    public List<CounselorDTO> getAllActiveCounselors() {
        List<CounselorDetails> counselorDetails = counselorDetailsRepository.findByUserIsActiveTrue();

        return counselorDetails.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CounselorDTO getCounselorById(String counselorId) {
        return counselorDetailsRepository.findById(counselorId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<CounselorDTO> getCounselorsByInstitute(String institute) {
        List<CounselorDetails> counselorDetails =
                counselorDetailsRepository.findByAssignedInstituteAndUserIsActiveTrue(institute);

        return counselorDetails.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CounselorDTO convertToDTO(CounselorDetails counselorDetails) {
        User user = counselorDetails.getUser();
        String instituteName = fetchInstituteName(counselorDetails.getAssignedInstitute());


        return CounselorDTO.builder()
                .id(counselorDetails.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .phone(user.getMobileNumber())
                .instituteId(counselorDetails.getAssignedInstitute())
                .instituteName(instituteName)
                .expertiseArea(counselorDetails.getExpertiseArea())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .department(counselorDetails.getExpertiseArea())
                .lastActive(user.getLastLogin())
                .joinedDate(user.getCreatedAt())
                .build();

    }

    public String fetchInstituteName(String instituteId) {
        if (instituteId == null || instituteId.trim().isEmpty()) {
            log.warn("Institute ID is null or empty, returning default value");
            return "Unknown Institute";
        }

        String url = "http://localhost:6001/api/institutions/" + instituteId;
        log.debug("Fetching institute name from URL: {}", url);

        try {
            HttpHeaders headers = new HttpHeaders();
            String jwtToken = getJwtToken();
            if (jwtToken != null) {
                headers.set("Authorization", "Bearer " + jwtToken);
            }
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<ApiResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, ApiResponse.class
            );

            log.debug("Received response with status: {}", response.getStatusCode());

            ApiResponse apiResponse = response.getBody();
            if (apiResponse != null && apiResponse.isSuccess() && apiResponse.getData() != null
                    && apiResponse.getData().getName() != null && !apiResponse.getData().getName().trim().isEmpty()) {
                log.debug("Successfully fetched institute name: {}", apiResponse.getData().getName());
                return apiResponse.getData().getName();
            } else {
                log.warn("Invalid response or empty name for ID: {}. Response: {}, falling back to instituteId",
                        instituteId, apiResponse);
                return instituteId;
            }
        } catch (HttpClientErrorException e) {
            log.error("HTTP error fetching institute name for ID: {}. Status: {}, Response: {}, falling back to instituteId",
                    instituteId, e.getStatusCode(), e.getResponseBodyAsString());
            return instituteId;
        } catch (Exception e) {
            log.error("Failed to fetch institute name for ID: {}. Error: {}, falling back to instituteId",
                    instituteId, e.getMessage());
            return instituteId;
        }
    }

    private String getJwtToken() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        } catch (Exception e) {
            log.warn("Could not retrieve JWT token: {}", e.getMessage());
            return null;
        }
    }

    private static class ApiResponse {
        private boolean success;
        private String message;
        private InstituteResponse data;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public InstituteResponse getData() {
            return data;
        }

        public void setData(InstituteResponse data) {
            this.data = data;
        }
    }
}