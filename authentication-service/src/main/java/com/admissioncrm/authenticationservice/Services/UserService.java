package com.admissioncrm.authenticationservice.Services;

import com.admissioncrm.authenticationservice.DTO.AdminAssignment.AssignAdminRequest;
import com.admissioncrm.authenticationservice.DTO.ApiResponse;
import com.admissioncrm.authenticationservice.DTO.InstituteResponse;
import com.admissioncrm.authenticationservice.DTO.Stats.UserStatsDTO;
import com.admissioncrm.authenticationservice.DTO.UserCreationDTO.CreateUserRequest;
import com.admissioncrm.authenticationservice.DTO.InstituteAdminDTO;
import com.admissioncrm.authenticationservice.DTO.UserResponseDTO;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.Role;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;
import com.admissioncrm.authenticationservice.Entities.CounselorDetails;
import com.admissioncrm.authenticationservice.Entities.InstituteAdminDetails;
import com.admissioncrm.authenticationservice.ExceptionHandling.ApiException;
import com.admissioncrm.authenticationservice.ExceptionHandling.UsernameAlreadyExistsException;
import com.admissioncrm.authenticationservice.Repositories.CounselorDetailsRepository;
import com.admissioncrm.authenticationservice.Repositories.InstituteAdminDetailsRepository;
import com.admissioncrm.authenticationservice.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InstituteAdminDetailsRepository instituteAdminDetailsRepository;
    @Autowired
    private CounselorDetailsRepository counsellorDetailsRepository;
    private final RestTemplate restTemplate;

    @Value("${lead-service.base-url}")
    private String leadServiceBaseUrl;


    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> createInstituteAdmin(CreateUserRequest request) {
        return createUserFromDTO(request, Role.INSTITUTE_ADMIN);
    }

    public ResponseEntity<?> createCounsellor(CreateUserRequest request) {
        return createUserFromDTO(request, Role.COUNSELOR);
    }

    private ResponseEntity<?> createUserFromDTO(CreateUserRequest request, Role role) {
        //cheak username is available or not
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username " + request.getUsername() + " is already taken");
        }
        //cheak duplicate Email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("Email " + request.getEmail() + " is already taken");
        }

        User user = new User();
        user.setRole(role);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        //spilt the full name into the first and last name
        String[] nameParts = request.getFullName().trim().split("\\s+", 2);

        user.setFirstName(nameParts[0]);
        user.setLastName(nameParts.length > 1 ? nameParts[1] : "");
        user.setExpertiseArea(request.getExpertiseArea());
        user.setMobileNumber(request.getMobileNumber());
        user.setInstitutionId(request.getInstituteId());

        if (role == Role.INSTITUTE_ADMIN) {
            //set the institute admin details here when you add the further Fields

            InstituteAdminDetails instituteAdminDetails = InstituteAdminDetails.builder()
                    .instituteId(request.getInstituteId())
                    .user(user)
                    .build();

            instituteAdminDetailsRepository.save(instituteAdminDetails);


            // ✅ After saving in Auth DB, notify Lead Service
            notifyLeadServiceAssignAdmin(request.getInstituteId(), user.getId());

        } else if (role == Role.COUNSELOR) {

            //add the fields as in future need

            CounselorDetails counselorDetails = CounselorDetails.builder()
                    .expertiseArea(request.getExpertiseArea())
                    .assignedInstitute(request.getInstituteId())
                    .user(user)
                    .build();

            counsellorDetailsRepository.save(counselorDetails);
        }


        userRepository.save(user);

        return ResponseEntity.ok("done");
    }

    //notify lead service to assign admin

    private void notifyLeadServiceAssignAdmin(String instituteId, String userId) {
        String url = leadServiceBaseUrl + "/institutions/" + instituteId + "/assign-admin";
        log.debug("Calling Lead Service to assign admin: userId={}, instituteId={}", userId, instituteId);
        System.out.println("Calling Lead Service to assign admin: userId=" + userId + ", instituteId=" + instituteId);
        try {
            HttpHeaders headers = new HttpHeaders();
            String jwtToken = getJwtToken();
            if (jwtToken != null) {
                headers.set("Authorization", "Bearer " + jwtToken);
            }
            headers.set("Content-Type", "application/json");

            AssignAdminRequest request = new AssignAdminRequest(userId);
            HttpEntity<AssignAdminRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class
            );

            log.info("Lead service assign-admin response: {}", response.getBody());
        } catch (Exception e) {
            log.error("Failed to call Lead Service for assigning admin. InstituteId={}, userId={}, error={}",
                    instituteId, userId, e.getMessage());
        }
    }



    public List<InstituteAdminDTO> getAllInstituteAdmins() {
        List<InstituteAdminDetails> instituteAdmins = instituteAdminDetailsRepository.findAll();
        return instituteAdmins.stream().map(this::convertToInstituteAdminDTO).collect(Collectors.toList());
    }

    private InstituteAdminDTO convertToInstituteAdminDTO(InstituteAdminDetails instituteAdminDetails) {
        User user = instituteAdminDetails.getUser();
        String instituteName = fetchInstituteName(instituteAdminDetails.getInstituteId());
        return InstituteAdminDTO.builder()
                .id(instituteAdminDetails.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .instituteId(instituteAdminDetails.getInstituteId())
                .instituteName(instituteName)
                .expertiseArea(user.getExpertiseArea())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .lastLogin(user.getLastLogin())
                .build();
    }

    public List<UserResponseDTO> getAdmins() {
        List<User> admins = userRepository.findByRole(Role.UNIVERSITY_ADMIN);
        return admins.stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }

    private UserResponseDTO convertToUserDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFirstName() + " " + user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setExpertiseArea(user.getExpertiseArea());
        dto.setRole(user.getRole().name());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setLastLogin(user.getLastLogin());
        return dto;
    }

    public String fetchInstituteName(String instituteId) {
        if (instituteId == null || instituteId.trim().isEmpty()) {
            log.warn("Institute ID is null or empty, returning default value");
            return "Unknown Institute";
        }

        String url = leadServiceBaseUrl+"/institutions/" + instituteId;
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


    public List<UserResponseDTO> getInstituteAdmins() {
        // Fetch all users having role INSTITUTE_ADMIN
        List<User> instituteAdmins = userRepository.findByRole(Role.INSTITUTE_ADMIN);
        return instituteAdmins.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    public List<UserResponseDTO> getCounsellors() {
        // Fetch all users having role COUNSELOR
        List<User> counsellors = userRepository.findByRole(Role.COUNSELOR);
        return counsellors.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    public UserStatsDTO getUserStats() {
        long totalCounselors = userRepository.countByRole(Role.COUNSELOR);
        long totalStudents = userRepository.countByRole(Role.STUDENT);
        long totalInstituteAdmins = userRepository.countByRole(Role.INSTITUTE_ADMIN);
        long totalAdmins = userRepository.countByRole(Role.UNIVERSITY_ADMIN)+totalInstituteAdmins;

        long activeCounselors = userRepository.countByRoleAndIsActive(Role.COUNSELOR, true);
        long activeStudents = userRepository.countByRoleAndIsActive(Role.STUDENT, true);

        return new UserStatsDTO(totalCounselors, totalStudents, totalAdmins, totalInstituteAdmins, activeCounselors, activeStudents);
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


