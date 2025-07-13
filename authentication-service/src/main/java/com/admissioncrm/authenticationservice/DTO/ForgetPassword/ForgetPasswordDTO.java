package com.admissioncrm.authenticationservice.DTO.ForgetPassword;

import lombok.Data;

@Data
public class ForgetPasswordDTO {
    String token;
    String newPassword;
}
