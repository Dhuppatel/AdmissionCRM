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
public class EntranceExamInfoDTO {

    @NotNull(message = "Appeared for entrance exam status is required")
    private Boolean appearedForEntranceExam;

    // Entrance Exam 1
    private String entranceExam1;
    private String entrancePassingDate1;
    private String entranceResultStatus1;
    @DecimalMin(value = "0.0", message = "Entrance score must be positive")
    private BigDecimal entranceScoreRankPercentile1;
    private String entranceRollnoApplicationno1;

    // Entrance Exam 2
    private String entranceExam2;
    private String entrancePassingDate2;
    private String entranceResultStatus2;
    @DecimalMin(value = "0.0", message = "Entrance score must be positive")
    private BigDecimal entranceScoreRankPercentile2;
    private String entranceRollnoApplicationno2;

    // Entrance Exam 3
    private String entranceExam3;
    private String entrancePassingDate3;
    private String entranceResultStatus3;
    @DecimalMin(value = "0.0", message = "Entrance score must be positive")
    private BigDecimal entranceScoreRankPercentile3;
    private String entranceRollnoApplicationno3;

    // Entrance Exam 4
    private String entranceExam4;
    private String entrancePassingDate4;
    private String entranceResultStatus4;
    @DecimalMin(value = "0.0", message = "Entrance score must be positive")
    private BigDecimal entranceScoreRankPercentile4;
    private String entranceRollnoApplicationno4;

    // ACPC Information
    @NotNull(message = "ACPC registration status is required")
    private Boolean registeredInAcpcAcpdc;

    @Min(value = 1, message = "ACPC merit number must be positive")
    private Integer acpcMeritNumber;

    @DecimalMin(value = "0.0", message = "ACPC merit marks must be positive")
    private BigDecimal acpcMeritMarks;

    @Min(value = 1, message = "ACPC application number must be positive")
    private Integer acpcApplicationNumber;
}
