package com.admissioncrm.authenticationservice.DTO.Jwt;

import com.admissioncrm.authenticationservice.Entities.CoreEntities.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private String jwtToken;
    private Role role;
}
