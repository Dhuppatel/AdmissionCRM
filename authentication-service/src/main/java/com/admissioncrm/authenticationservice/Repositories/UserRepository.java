package com.admissioncrm.authenticationservice.Repositories;

import com.admissioncrm.authenticationservice.Entities.CoreEntities.Role;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByMobileNumber(String mobileNumber);

    boolean existsByMobileNumber(String mobileNumber);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email);

    List<User> findByRole(Role role);
}
