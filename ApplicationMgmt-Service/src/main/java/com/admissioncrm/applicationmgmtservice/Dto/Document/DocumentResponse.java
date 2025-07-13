package com.admissioncrm.applicationmgmtservice.Dto.Document;

import com.admissioncrm.applicationmgmtservice.Entities.Document.Document;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class DocumentResponse {
    private String id;
    private String applicationId;
    private String documentType;
    private String originalFilename;
    private String structuredFilename;
    private Long fileSize;
    private String mimeType;
    private Integer sequenceNumber;
    private LocalDateTime uploadDate;
    private Document.VerificationStatus verificationStatus;
    private String verificationComments;
    private String downloadUrl;
    private String previewUrl;
}
