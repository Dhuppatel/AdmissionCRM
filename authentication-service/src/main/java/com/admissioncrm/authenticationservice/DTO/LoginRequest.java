package com.admissioncrm.authenticationservice.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    @NotBlank(message = "identifeir should not be blank")
    String identifier;

    @NotNull
    @NotBlank(message = "password should not be blank")
    String password;
}
