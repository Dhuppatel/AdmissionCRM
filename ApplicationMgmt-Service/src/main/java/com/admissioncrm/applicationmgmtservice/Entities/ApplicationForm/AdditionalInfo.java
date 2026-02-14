package com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// Additional Information Embeddable
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionalInfo {

    @Column(name = "education_loan_required")
    private Boolean educationLoanRequired;

    @Column(name = "hostel_accomodation_required")
    private Boolean hostelAccommodationRequired;

    @Column(name = "transportation_required")
    private Boolean transportationRequired;

    @Column(name = "heard_about_university_from",  columnDefinition = "TEXT")
    private String heardAboutUniversityFrom;

    @Column(name = "student_of_university", columnDefinition = "TEXT")
    private String studentOfUniversity;

    @Column(name = "enrollment_number", columnDefinition = "TEXT")
    private String enrollmentNumber;
}