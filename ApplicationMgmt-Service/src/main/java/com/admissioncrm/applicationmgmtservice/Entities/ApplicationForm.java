package com.admissioncrm.applicationmgmtservice.Entities;

import com.admissioncrm.applicationmgmtservice.Enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "application_forms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_form_id")
    private Long applicationFormId;

    @Column(name = "id_user", nullable = false)
    private String idUser;

    @Column(name = "institute_course_id", nullable = false)
    private Long instituteCourseId;

    @Column(name = "full_name", nullable = false, columnDefinition = "TEXT")
    private String fullName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "middle_name", columnDefinition = "TEXT")
    private String middleName;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "student_mobile", nullable = false, columnDefinition = "TEXT")
    private String studentMobile;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "gender", nullable = false, columnDefinition = "TEXT")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "religion", nullable = false, columnDefinition = "TEXT")
    private String religion;

    @Column(name = "nationality", nullable = false, columnDefinition = "TEXT")
    private String nationality;

    @Column(name = "caste_category", columnDefinition = "TEXT")
    private String casteCategory;

    @Column(name = "domicile_state", columnDefinition = "TEXT")
    private String domicileState;

    @Column(name = "differently_abled", nullable = false)
    private Boolean differentlyAbled;

    @Column(name = "disability", columnDefinition = "TEXT")
    private String disability;

    @Column(name = "economically_backward_class")
    private Boolean economicallyBackwardClass;

    // Father's Information
    @Column(name = "father_salutation", nullable = false, columnDefinition = "TEXT")
    private String fatherSalutation;

    @Column(name = "father_name", nullable = false, columnDefinition = "TEXT")
    private String fatherName;

    @Column(name = "father_mobile", nullable = false, columnDefinition = "TEXT")
    private String fatherMobile;

    @Column(name = "father_email", columnDefinition = "TEXT")
    private String fatherEmail;

    // Mother's Information
    @Column(name = "mother_salutation", nullable = false, columnDefinition = "TEXT")
    private String motherSalutation;

    @Column(name = "mother_name", nullable = false, columnDefinition = "TEXT")
    private String motherName;

    @Column(name = "mother_mobile", columnDefinition = "TEXT")
    private String motherMobile;

    @Column(name = "mother_email", columnDefinition = "TEXT")
    private String motherEmail;

    @Column(name = "annual_income", nullable = false, columnDefinition = "TEXT")
    private String annualIncome;

    // Correspondence Address
    @Column(name = "country", nullable = false, columnDefinition = "TEXT")
    private String country;

    @Column(name = "state", columnDefinition = "TEXT")
    private String state;

    @Column(name = "district", columnDefinition = "TEXT")
    private String district;

    @Column(name = "city_taluka", columnDefinition = "TEXT")
    private String cityTaluka;

    @Column(name = "village_town", columnDefinition = "TEXT")
    private String villageTown;

    @Column(name = "address_line1", nullable = false, columnDefinition = "TEXT")
    private String addressLine1;

    @Column(name = "address_line2", columnDefinition = "TEXT")
    private String addressLine2;

    @Column(name = "pincode", nullable = false)
    private String pincode;

    @Column(name = "permanent_address_same_as_correspondence", nullable = false)
    private Boolean permanentAddressSameAsCorrespondence;

    // Permanent Address
    @Column(name = "country_permanent", nullable = false, columnDefinition = "TEXT")
    private String countryPermanent;

    @Column(name = "state_permanent", columnDefinition = "TEXT")
    private String statePermanent;

    @Column(name = "district_permanent", columnDefinition = "TEXT")
    private String districtPermanent;

    @Column(name = "city_taluka_permanent", columnDefinition = "TEXT")
    private String cityTalukaPermanent;

    @Column(name = "village_town_permanent", columnDefinition = "TEXT")
    private String villageTownPermanent;

    @Column(name = "address_line1_permanent", nullable = false, columnDefinition = "TEXT")
    private String addressLine1Permanent;

    @Column(name = "address_line2_permanent", columnDefinition = "TEXT")
    private String addressLine2Permanent;

    @Column(name = "pincode_permanent", nullable = false)
    private String pincodePermanent;

    // Academic Information
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
    private BigDecimal twelfthTotalMarks;

    @Column(name = "twelfth_obtain_marks", precision = 8, scale = 2)
    private BigDecimal twelfthObtainMarks;

    @Column(name = "twelfth_obtain_cgpa", precision = 8, scale = 2)
    private BigDecimal twelfthObtainCgpa;

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
    private BigDecimal courseMaximumMarks;

    @Column(name = "course_obtain_marks", precision = 8, scale = 2)
    private BigDecimal courseObtainMarks;

    @Column(name = "course_obtain_cgpa_percentage", precision = 8, scale = 2)
    private BigDecimal courseObtainCgpaPercentage;

    // Entrance Exam Information
    @Column(name = "appeared_for_entrance_exam", nullable = false)
    private Boolean appearedForEntranceExam;

    // Entrance Exam 1
    @Column(name = "entrance_exam_1", columnDefinition = "TEXT")
    private String entranceExam1;

    @Column(name = "entrance_passing_date_1", columnDefinition = "TEXT")
    private String entrancePassingDate1;

    @Column(name = "entrance_result_status_1", columnDefinition = "TEXT")
    private String entranceResultStatus1;

    @Column(name = "entrace_score_rank_persactile_1", precision = 8, scale = 2)
    private BigDecimal entranceScoreRankPercentile1;

    @Column(name = "entrace_rollno_applicationno_1", columnDefinition = "TEXT")
    private String entranceRollnoApplicationno1;

    // Entrance Exam 2
    @Column(name = "entrance_exam_2", columnDefinition = "TEXT")
    private String entranceExam2;

    @Column(name = "entrance_passing_date_2", columnDefinition = "TEXT")
    private String entrancePassingDate2;

    @Column(name = "entrance_result_status_2", columnDefinition = "TEXT")
    private String entranceResultStatus2;

    @Column(name = "entrace_score_rank_persactile_2", precision = 8, scale = 2)
    private BigDecimal entranceScoreRankPercentile2;

    @Column(name = "entrace_rollno_applicationno_2", columnDefinition = "TEXT")
    private String entranceRollnoApplicationno2;

    // Entrance Exam 3
    @Column(name = "entrance_exam_3", columnDefinition = "TEXT")
    private String entranceExam3;

    @Column(name = "entrance_passing_date_3", columnDefinition = "TEXT")
    private String entrancePassingDate3;

    @Column(name = "entrance_result_status_3", columnDefinition = "TEXT")
    private String entranceResultStatus3;

    @Column(name = "entrace_score_rank_persactile_3", precision = 8, scale = 2)
    private BigDecimal entranceScoreRankPercentile3;

    @Column(name = "entrace_rollno_applicationno_3", columnDefinition = "TEXT")
    private String entranceRollnoApplicationno3;

    // Entrance Exam 4
    @Column(name = "entrance_exam_4", columnDefinition = "TEXT")
    private String entranceExam4;

    @Column(name = "entrance_passing_date_4", columnDefinition = "TEXT")
    private String entrancePassingDate4;

    @Column(name = "entrance_result_status_4", columnDefinition = "TEXT")
    private String entranceResultStatus4;

    @Column(name = "entrace_score_rank_persactile_4", precision = 8, scale = 2)
    private BigDecimal entranceScoreRankPercentile4;

    @Column(name = "entrace_rollno_applicationno_4", columnDefinition = "TEXT")
    private String entranceRollnoApplicationno4;

    // ACPC Information
    @Column(name = "registered_in_acpc_acpdc", nullable = false)
    private Boolean registeredInAcpcAcpdc;

    @Column(name = "acpc_merit_number")
    private Integer acpcMeritNumber;

    @Column(name = "acpc_merit_marks", precision = 8, scale = 2)
    private BigDecimal acpcMeritMarks;

    @Column(name = "acpc_application_number")
    private Integer acpcApplicationNumber;

    // Additional Information
    @Column(name = "education_loan_required")
    private Boolean educationLoanRequired;

    @Column(name = "hostel_accomodation_required")
    private Boolean hostelAccommodationRequired;

    @Column(name = "transportation_required")
    private Boolean transportationRequired;

    @Column(name = "heard_about_university_from", nullable = false, columnDefinition = "TEXT")
    private String heardAboutUniversityFrom;

    @Column(name = "student_of_university", nullable = false, columnDefinition = "TEXT")
    private String studentOfUniversity;

    @Column(name = "enrollment_number", columnDefinition = "TEXT")
    private String enrollmentNumber;

    // Audit Fields
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Foreign Key Relationships (if you want to map them)
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_course_id", referencedColumnName = "institute_course_id", insertable = false, updatable = false)
    private InstituteCourse instituteCourse;
    */
}