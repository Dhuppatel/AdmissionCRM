package com.admissioncrm.authenticationservice.Services;

import com.admissioncrm.authenticationservice.DTO.ApiResponse;
import com.admissioncrm.authenticationservice.DTO.Jwt.JwtResponse;
import com.admissioncrm.authenticationservice.DTO.LoginRequest;
import com.admissioncrm.authenticationservice.DTO.Register.RegisterRequest;
import com.admissioncrm.authenticationservice.DTO.Register.RegisterResponse;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.Role;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;

import com.admissioncrm.authenticationservice.ExceptionHandling.ApiException;
import com.admissioncrm.authenticationservice.ExceptionHandling.UsernameAlreadyExistsException;
import com.admissioncrm.authenticationservice.Repositories.UserRepository;
import com.admissioncrm.authenticationservice.Utilities.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<JwtResponse> loginUser(LoginRequest loginRequest) {
        String identifier = loginRequest.getIdentifier();
        String password = loginRequest.getPassword();
        //fetch the user from DB

        User user = getUserByIdentifier(loginRequest.getIdentifier());

        //authenticate using spring security
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(identifier, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials");
        }

        //generate the token
        try {
            String jwtToken = jwtUtils.generateToken(user.getUsername(), user.getRole());
            return ResponseEntity.ok(new JwtResponse(jwtToken, user.getRole()));
        } catch (Exception e) {
            throw new ApiException("Failed to generate JWT token");
        }
    }


    //register Student

    @Transactional
    public ResponseEntity<ApiResponse<RegisterResponse>> registerStudent(RegisterRequest request) {

        if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new ApiException("Mobile number already registered");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("Email already registered");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username " + request.getUsername() + " is already taken.! ");
        }
        User user = new User();
        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            user.setUsername(request.getUsername());
        } else user.setUsername(request.getMobileNumber()); // set the mobile as the Username if Username not exists

        user.setMobileNumber(request.getMobileNumber());
        user.setFirstName(request.getFirstName());
        user.setMiddleName(request.getMiddleName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.STUDENT);

        userRepository.save(user);
        //Also add save the Student Details Entity in future for more user specific details

        System.out.println("User registered successfully: " + user.getUsername());

        RegisterResponse response = RegisterResponse.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .build();

        return ResponseEntity.ok(ApiResponse.success("Student registered successfully", response));

    }


    // Utility methods

    private User getUserByIdentifier(String identifier) {
        if (identifier.matches("^\\d{10}$")) {
            return userRepository.findByMobileNumber(identifier)
                    .orElseThrow(() -> new UsernameNotFoundException("Mobile number not found"));
        } else if (identifier.contains("@")) {
            return userRepository.findByEmail(identifier)
                    .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        } else {
            return userRepository.findByUsername(identifier)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        }
    }
}
