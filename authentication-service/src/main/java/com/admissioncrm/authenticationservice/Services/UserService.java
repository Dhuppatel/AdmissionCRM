package com.admissioncrm.authenticationservice.Services;

import com.admissioncrm.authenticationservice.DTO.ApiResponse;
import com.admissioncrm.authenticationservice.DTO.UserCreationDTO.CreateUserRequest;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.Role;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;
import com.admissioncrm.authenticationservice.Entities.CounselorDetails;
import com.admissioncrm.authenticationservice.Entities.InstituteAdminDetails;
import com.admissioncrm.authenticationservice.ExceptionHandling.ApiException;
import com.admissioncrm.authenticationservice.ExceptionHandling.UsernameAlreadyExistsException;
import com.admissioncrm.authenticationservice.Repositories.CounselorDetailsRepository;
import com.admissioncrm.authenticationservice.Repositories.InstituteAdminDetailsRepository;
import com.admissioncrm.authenticationservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private InstituteAdminDetailsRepository instituteAdminDetailsRepository;
        @Autowired
        private CounselorDetailsRepository counsellorDetailsRepository;

    public ResponseEntity<?> createInstituteAdmin( CreateUserRequest request) {
        return createUserFromDTO(request,Role.INSTITUTE_ADMIN);
    }

    public ResponseEntity<?> createCounsellor(CreateUserRequest request) {
        return createUserFromDTO(request, Role.COUNSELOR);
    }

    private ResponseEntity<?> createUserFromDTO(CreateUserRequest request, Role role) {
        //cheak username is available or not
        if(userRepository.existsByUsername(request.getUsername())) {
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

        if(role==Role.INSTITUTE_ADMIN)
        {
            //set the institute admin details here when you add the further Fields

            InstituteAdminDetails instituteAdminDetails=InstituteAdminDetails.builder()
                    .institute(request.getInstituteId())
                    .user(user)
                    .build();

            instituteAdminDetailsRepository.save(instituteAdminDetails);

        }
        else if(role==Role.COUNSELOR)
        {

            //add the fields as in future need

            CounselorDetails counselorDetails=CounselorDetails.builder()
                    .expertiseArea(request.getExpertiseArea())
                    .assignedInstitute(request.getInstituteId())
                    .user(user)
                    .build();

            counsellorDetailsRepository.save(counselorDetails);
        }


        userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.success("User created successfully", null));
    }


}


