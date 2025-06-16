package com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO;


import com.admissioncrm.applicationmgmtservice.Enums.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfoDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Student mobile is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String studentMobile;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Religion is required")
    private String religion;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    private String casteCategory;

    private String domicileState;

    @NotNull(message = "Differently abled status is required")
    private Boolean differentlyAbled;

    private String disability;

    private Boolean economicallyBackwardClass;
}
