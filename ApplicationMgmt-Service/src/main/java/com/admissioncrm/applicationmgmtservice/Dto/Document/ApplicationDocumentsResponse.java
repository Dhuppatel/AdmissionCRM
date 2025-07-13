package com.admissioncrm.applicationmgmtservice.Dto.Document;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ApplicationDocumentsResponse {
    private String applicationId;
    private String applicationReferenceId;
    private Map<String, List<DocumentResponse>> documentsByType;
    private Integer totalDocuments;
    private LocalDateTime lastUploadDate;
}