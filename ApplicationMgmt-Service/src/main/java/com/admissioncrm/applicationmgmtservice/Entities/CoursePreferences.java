package com.admissioncrm.applicationmgmtservice.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CoursePreferences {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name ="application_form_id",nullable=false,unique=true)
    private ApplicationForm applicationForm;

    private String couresePreference1;
    private String couresePreference2;
    private String couresePreference3;
    private String couresePreference4;

}
