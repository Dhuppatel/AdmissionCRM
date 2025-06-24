package com.admissioncrm.authenticationservice.Services;

import com.admissioncrm.authenticationservice.DTO.ApiResponse;
import com.admissioncrm.authenticationservice.DTO.UserCreationDTO.CreateUserRequest;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.Role;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;
import com.admissioncrm.authenticationservice.ExceptionHandling.ApiException;
import com.admissioncrm.authenticationservice.ExceptionHandling.UsernameAlreadyExistsException;
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

        //set Institiute/Dept ID if needed,,,,,
        if (request.getInstituteId() != null) {

        }


        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(true, "User created successfully"));
    }


}


