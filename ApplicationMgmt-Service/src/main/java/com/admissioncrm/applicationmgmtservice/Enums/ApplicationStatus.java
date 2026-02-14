package com.admissioncrm.applicationmgmtservice.Enums;

import lombok.Data;


public  enum ApplicationStatus {
    CREATED,
    DRAFT,
    SUBMITTED,
    UNDER_REVIEW,
    APPROVED,
    REJECTED,
    WAITLISTED,
    INCOMPLETE, PENDING_DOCUMENTS, ENROLLED, ADMITTED,
    TAKEN_ADMISSION_ELSEWHERE,
    NOT_INTERESTED
}
