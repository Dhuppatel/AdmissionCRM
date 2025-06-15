package com.admissioncrm.applicationmgmtservice.Dto;

import com.admissioncrm.applicationmgmtservice.Enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationFormRequestDto {

    // Basic Info
    @NotBlank(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Student mobile is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian mobile number")
    private String studentMobile;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotNull(message = "Gender is required")
    private Gender gender;

    private String religion;
    private String nationality;
    private String casteCategory;
    private String domicileState;

    private Boolean differentlyAbled;
    private String disability;
    private Boolean economicallyBackwardClass;

    // Guardian / Parent Info
    @NotBlank(message = "Father name is required")
    private String fatherName;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian mobile number")
    private String fatherMobile;

    @Email(message = "Invalid email")
    private String fatherEmail;

    private String fatherSalutation;

    private String motherName;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian mobile number")
    private String motherMobile;

    @Email(message = "Invalid email")
    private String motherEmail;

    private String motherSalutation;

    private String annualIncome;

    // Correspondence Address
    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "City/Taluka is required")
    private String cityTaluka;

    private String villageTown;

    @NotBlank(message = "Address line 1 is required")
    private String addressLine1;

    private String addressLine2;

    @NotNull(message = "Pincode is required")
    @Pattern(regexp = "^\\d{6}$", message = "Pincode should be 6 digits")
    private String pincode;

    private Boolean permanentAddressSameAsCorrespondence;

    // Permanent Address
    private String countryPermanent;
    private String statePermanent;
    private String districtPermanent;
    private String cityTalukaPermanent;
    private String villageTownPermanent;
    private String addressLine1Permanent;
    private String addressLine2Permanent;

    @Pattern(regexp = "^\\d{6}$", message = "Pincode should be 6 digits")
    private String pincodePermanent;

    // Academic Info
    private String udiseNo;
    private String abcId;
    private String qualification;

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

    @DecimalMin(value = "0.0", message = "Total marks must be at least 0")
    private BigDecimal twelfthTotalMarks;

    @DecimalMin(value = "0.0", message = "Obtained marks must be at least 0")
    private BigDecimal twelfthObtainMarks;

    @DecimalMin(value = "0.0", message = "CGPA must be at least 0")
    private BigDecimal twelfthObtainCgpa;

    // Course Info
    private String courseInstituteName;
    private String courseSeatNumber;
    private String courseBoardOrUniversity;
    private String courseDegreeOrBranch;
    private String courseSpecialization;
    private String coursePassingDate;
    private String courseResultStatus;
    private String courseMarkingScheme;

    private BigDecimal courseMaximumMarks;
    private BigDecimal courseObtainMarks;
    private BigDecimal courseObtainCgpaPercentage;

    // Entrance Exams
    private Boolean appearedForEntranceExam;

    private String entranceExam1;
    private String entrancePassingDate1;
    private String entranceResultStatus1;
    private BigDecimal entranceScoreRankPercentile1;
    private String entranceRollnoApplicationno1;

    private String entranceExam2;
    private String entrancePassingDate2;
    private String entranceResultStatus2;
    private BigDecimal entranceScoreRankPercentile2;
    private String entranceRollnoApplicationno2;

    private String entranceExam3;
    private String entrancePassingDate3;
    private String entranceResultStatus3;
    private BigDecimal entranceScoreRankPercentile3;
    private String entranceRollnoApplicationno3;

    private String entranceExam4;
    private String entrancePassingDate4;
    private String entranceResultStatus4;
    private BigDecimal entranceScoreRankPercentile4;
    private String entranceRollnoApplicationno4;

    // ACPC
    private Boolean registeredInAcpcAcpdc;
    private Integer acpcMeritNumber;
    private BigDecimal acpcMeritMarks;
    private Integer acpcApplicationNumber;

    // Additional Info
    private Boolean educationLoanRequired;
    private Boolean hostelAccommodationRequired;
    private Boolean transportationRequired;
    private String heardAboutUniversityFrom;
    private String studentOfUniversity;
    private String enrollmentNumber;

    // Course Mapping
    @NotNull(message = "Institute course ID is required")
    private Long instituteCourseId;

    @NotNull(message = "User ID is required")
    private String idUser;

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }
}
