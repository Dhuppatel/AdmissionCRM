package com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationFormSubmissionDTO {

    @NotBlank(message = "User ID is required")
    private String idUser;

    @Valid
    @NotNull(message = "Personal information is required")
    private PersonalInfoDTO personalInfo;

    @Valid
    @NotNull(message = "Parent information is required")
    private ParentInfoDTO parentInfo;

    @Valid
    @NotNull(message = "Address information is required")
    private AddressInfoDTO addressInfo;

    @Valid
    @NotNull(message = "Academic information is required")
    private AcademicInfoDTO academicInfo;

    @Valid
    @NotNull(message = "Entrance exam information is required")
    private EntranceExamInfoDTO entranceExamInfo;

    @Valid
    @NotNull(message = "Additional information is required")
    private AdditionalInfoDTO additionalInfo;

    @Valid
    @NotNull(message = "Course preference is required")
    private CoursePreferenceDTO coursePreference;
}