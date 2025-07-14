package com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entrance Exam Information Embeddable
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntranceExamInfo {

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
    private java.math.BigDecimal entranceScoreRankPercentile1;

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
    private java.math.BigDecimal entranceScoreRankPercentile2;

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
    private java.math.BigDecimal entranceScoreRankPercentile3;

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
    private java.math.BigDecimal entranceScoreRankPercentile4;

    @Column(name = "entrace_rollno_applicationno_4", columnDefinition = "TEXT")
    private String entranceRollnoApplicationno4;

    // ACPC Information
    @Column(name = "registered_in_acpc_acpdc", nullable = false)
    private Boolean registeredInAcpcAcpdc;

    @Column(name = "acpc_merit_number")
    private Integer acpcMeritNumber;

    @Column(name = "acpc_merit_marks", precision = 8, scale = 2)
    private java.math.BigDecimal acpcMeritMarks;

    @Column(name = "acpc_application_number")
    private String acpcApplicationNumber;
}