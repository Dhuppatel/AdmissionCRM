package com.admissioncrm.applicationmgmtservice.Dto.ApplicationFormRequestDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademicInfoDTO {

    private String udiseNo;
    private String abcId;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    // 12th Grade Information
    private String twelfthPassoutCountry;
    private String twelfthPassoutState;
    private String twelfthPassoutBoard;
    private String twelfthSchoolName;
    private String twelfthResultStatus;
    private String twelfthSeatNumber;
    private String twelfthStream;
    private String twelfthPassingDate;
    private String twelfthModeOfStudy;
    private String twelfthMarkingScheme;

    @DecimalMin(value = "0.0", message = "12th total marks must be positive")
    private BigDecimal twelfthTotalMarks;

    @DecimalMin(value = "0.0", message = "12th obtained marks must be positive")
    private BigDecimal twelfthObtainMarks;

    @DecimalMin(value = "0.0", message = "12th CGPA must be positive")
    @DecimalMax(value = "10.0", message = "12th CGPA cannot exceed 10.0")
    private BigDecimal twelfthObtainCgpa;

    // Course Information
    private String courseInstituteName;
    private String courseSeatNumber;
    private String courseBoardOrUniversity;
    private String courseDegreeOrBranch;
    private String courseSpecialization;
    private String coursePassingDate;
    private String courseResultStatus;
    private String courseMarkingScheme;


    @DecimalMin(value = "0.0", message = "Course maximum marks must be positive")
    private BigDecimal courseMaximumMarks;

    @DecimalMin(value = "0.0", message = "Course obtained marks must be positive")
    private BigDecimal courseObtainMarks;

    @DecimalMin(value = "0.0", message = "Course CGPA/Percentage must be positive")
    private BigDecimal courseObtainCgpaPercentage;
}
