package com.admissioncrm.applicationmgmtservice.Services.Document;

import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;
import com.admissioncrm.applicationmgmtservice.Repositories.ApplicationFormRepository;
import com.admissioncrm.applicationmgmtservice.Repositories.Document.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FileNamingService {

    @Autowired
    private ApplicationFormRepository applicationRepository;

    @Autowired
    private DocumentRepository documentRepository;

    /**
     * Generate structured filename: APP001_TRANSCRIPT_001.pdf
     * Format: {REF_ID}_{DOCUMENT_TYPE}_{SEQUENCE}.{extension}
     */
    public String generateStructuredFilename(String applicationId, String documentType, String originalFilename) {
        // Get application reference ID
        ApplicationForm application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        String refId = application.getReferenceId(); // e.g., "APP001"

        // Get next sequence number for this document type
        int sequenceNumber = getNextSequenceNumber(applicationId, documentType);

        // Extract file extension
        String extension = getFileExtension(originalFilename);

        // Generate structured filename
        String structuredFilename = String.format("%s_%s_%03d.%s",
                refId,
                documentType.toUpperCase(),
                sequenceNumber,
                extension);

        return structuredFilename;
    }

    /**
     * Generate file path with organized directory structure
     * Structure: /uploads/documents/{year}/{month}/{refId}/
     */
    public String generateFilePath(String refId, String structuredFilename) {
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());
        String month = String.format("%02d", now.getMonthValue());

        return String.format("documents/%s/%s/%s/%s", year, month, refId, structuredFilename);
    }

    int getNextSequenceNumber(String applicationId, String documentType) {
        // Get count of existing documents of this type
        int count = documentRepository.countByApplicationIdAndDocumentTypeAndIsActiveTrue(
                applicationId, documentType);
        return count + 1;
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }
}