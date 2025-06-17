package com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionalInfoDTO {

    private Boolean educationLoanRequired;
    private Boolean hostelAccommodationRequired;
    private Boolean transportationRequired;

    @NotBlank(message = "How you heard about university is required")
    private String heardAboutUniversityFrom;

    @NotBlank(message = "Student of university status is required")
    private String studentOfUniversity;

    private String enrollmentNumber;
}
