package com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Academic Information Embeddable
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademicInfo {

    @Column(name = "udise_no", columnDefinition = "TEXT")
    private String udiseNo;

    @Column(name = "abc_id", columnDefinition = "TEXT")
    private String abcId;

    @Column(name = "qualification", nullable = false, columnDefinition = "TEXT")
    private String qualification;

    // 12th Grade Information
    @Column(name = "twelfth_passout_country", columnDefinition = "TEXT")
    private String twelfthPassoutCountry;

    @Column(name = "twelfth_passout_state", columnDefinition = "TEXT")
    private String twelfthPassoutState;

    @Column(name = "twelfth_passout_board", columnDefinition = "TEXT")
    private String twelfthPassoutBoard;

    @Column(name = "twelfth_school_name", columnDefinition = "TEXT")
    private String twelfthSchoolName;

    @Column(name = "twelfth_result_status", columnDefinition = "TEXT")
    private String twelfthResultStatus;

    @Column(name = "twelfth_seat_number", columnDefinition = "TEXT")
    private String twelfthSeatNumber;

    @Column(name = "twelfth_stream", columnDefinition = "TEXT")
    private String twelfthStream;

    @Column(name = "twelfth_passing_date", columnDefinition = "TEXT")
    private String twelfthPassingDate;

    @Column(name = "twelfth_mode_of_study", columnDefinition = "TEXT")
    private String twelfthModeOfStudy;

    @Column(name = "twelfth_marking_scheme", columnDefinition = "TEXT")
    private String twelfthMarkingScheme;

    @Column(name = "twelfth_total_marks", precision = 8, scale = 2)
    private java.math.BigDecimal twelfthTotalMarks;

    @Column(name = "twelfth_obtain_marks", precision = 8, scale = 2)
    private java.math.BigDecimal twelfthObtainMarks;

    @Column(name = "twelfth_obtain_cgpa", precision = 8, scale = 2)
    private java.math.BigDecimal twelfthObtainCgpa;

    // Course Information
    @Column(name = "course_institute_name", columnDefinition = "TEXT")
    private String courseInstituteName;

    @Column(name = "course_seat_number", columnDefinition = "TEXT")
    private String courseSeatNumber;

    @Column(name = "course_board_or_university", columnDefinition = "TEXT")
    private String courseBoardOrUniversity;

    @Column(name = "course_degree_or_branch", columnDefinition = "TEXT")
    private String courseDegreeOrBranch;

    @Column(name = "course_specialization", columnDefinition = "TEXT")
    private String courseSpecialization;

    @Column(name = "course_passing_date", columnDefinition = "TEXT")
    private String coursePassingDate;

    @Column(name = "course_result_status", columnDefinition = "TEXT")
    private String courseResultStatus;

    @Column(name = "course_marking_scheme", columnDefinition = "TEXT")
    private String courseMarkingScheme;

    @Column(name = "course_maximum_marks", precision = 8, scale = 2)
    private java.math.BigDecimal courseMaximumMarks;

    @Column(name = "course_obtain_marks", precision = 8, scale = 2)
    private java.math.BigDecimal courseObtainMarks;

    @Column(name = "course_obtain_cgpa_percentage", precision = 8, scale = 2)
    private java.math.BigDecimal courseObtainCgpaPercentage;
}
