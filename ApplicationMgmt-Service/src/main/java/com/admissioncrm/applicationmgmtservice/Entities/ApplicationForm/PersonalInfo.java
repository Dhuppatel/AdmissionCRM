package com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfo {

    @Column(name = "full_name", nullable = false, columnDefinition = "TEXT")
    private String fullName;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "middle_name", columnDefinition = "TEXT")
    private String middleName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "student_mobile", nullable = false, columnDefinition = "TEXT")
    private String studentMobile;

    @Column(name = "dob", nullable = false)
    private java.time.LocalDate dob;

    @Column(name = "gender", nullable = false, columnDefinition = "TEXT")
    @Enumerated(EnumType.STRING)
    private com.admissioncrm.applicationmgmtservice.Enums.Gender gender;

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
}