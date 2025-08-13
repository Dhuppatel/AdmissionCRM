package com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// Parents Information Embeddable
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParentsInfo {

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
}