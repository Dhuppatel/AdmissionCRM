package com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursePreferenceDTO {

    @NotNull(message = "Institute course ID is required")
    private Long instituteCourseId;

    // Add other course preference fields if you have them
}