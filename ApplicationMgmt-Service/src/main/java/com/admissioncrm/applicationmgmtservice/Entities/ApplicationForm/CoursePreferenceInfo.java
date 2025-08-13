package com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// Course Preference Information Embeddable
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursePreferenceInfo {

    private String couresePreference1;
    private String couresePreference2;
    private String couresePreference3;
    private String couresePreference4;
}