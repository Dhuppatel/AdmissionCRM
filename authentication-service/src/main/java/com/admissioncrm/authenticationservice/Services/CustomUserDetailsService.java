package com.admissioncrm.authenticationservice.Services;

import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;
import com.admissioncrm.authenticationservice.Entities.UserPrinciple;
import com.admissioncrm.authenticationservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user=getUserByIdentifier(identifier);
        return new UserPrinciple(user);
    }

    private User getUserByIdentifier(String identifier) {

        if (identifier.matches("^\\d{10}$")) {
            return userRepository.findByMobileNumber(identifier)
                    .orElseThrow(() -> new UsernameNotFoundException("Mobile number not found"));
        } else if (identifier.contains("@")) {
            return userRepository.findByEmail(identifier)
                    .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        } else {
//            return userRepository.findById(identifier)
//                    .orElseThrow(()->new  UsernameNotFoundException("Username not found"));
                return userRepository.findByUsername(identifier)
                         .orElseGet(() ->
                                 // Last fallback: UUID as User ID
                                  userRepository.findById(identifier)
                                    .orElseThrow(() -> new UsernameNotFoundException("User ID not found: " + identifier))
                          );
        }
    }

}
