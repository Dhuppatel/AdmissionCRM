package com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParentInfoDTO {

    // Father's Information
    @NotBlank(message = "Father's salutation is required")
    private String fatherSalutation;

    @NotBlank(message = "Father's name is required")
    private String fatherName;

    @NotBlank(message = "Father's mobile is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Father's mobile number must be 10 digits")
    private String fatherMobile;

    @Email(message = "Invalid father's email format")
    private String fatherEmail;

    // Mother's Information
    @NotBlank(message = "Mother's salutation is required")
    private String motherSalutation;

    @NotBlank(message = "Mother's name is required")
    private String motherName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mother's mobile number must be 10 digits")
    private String motherMobile;

    @Email(message = "Invalid mother's email format")
    private String motherEmail;

    @NotBlank(message = "Annual income is required")
    private String annualIncome;
}