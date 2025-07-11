package com.admissioncrm.applicationmgmtservice.Services.Document;

import com.admissioncrm.applicationmgmtservice.Dto.Document.ApplicationDocumentsResponse;
import com.admissioncrm.applicationmgmtservice.Dto.Document.DocumentResponse;
import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm;
import com.admissioncrm.applicationmgmtservice.Entities.Document.Document;
import com.admissioncrm.applicationmgmtservice.Exception.Document.DocumentNotFoundException;
import com.admissioncrm.applicationmgmtservice.Repositories.ApplicationFormRepository;
import com.admissioncrm.applicationmgmtservice.Repositories.Document.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FileNamingService fileNamingService;

    @Autowired
    private ApplicationFormRepository applicationRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public DocumentResponse uploadDocument(MultipartFile file, String applicationId, String documentType) {
        try {
            // Validate file
            validateFile(file);

            // Get application for reference ID
            ApplicationForm application = applicationRepository.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            // Generate structured filename
            String structuredFilename = fileNamingService.generateStructuredFilename(
                    applicationId, documentType, file.getOriginalFilename());

            // Generate file path
            String relativePath = fileNamingService.generateFilePath(
                    application.getReferenceId(), structuredFilename);

            String fullPath = uploadDir + "/" + relativePath;

            // Create directory structure
            Path directoryPath = Paths.get(fullPath).getParent();
            Files.createDirectories(directoryPath);

            // Save file with structured name
            Path filePath = Paths.get(fullPath);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Get sequence number
            int sequenceNumber = fileNamingService.getNextSequenceNumber(applicationId, documentType);

            // Save metadata to database
            Document document = Document.builder()
                    .applicationId(applicationId)
                    .documentType(documentType)
                    .originalFilename(file.getOriginalFilename())
                    .structuredFilename(structuredFilename)
                    .filePath(relativePath)
                    .fileSize(file.getSize())
                    .mimeType(file.getContentType())
                    .sequenceNumber(sequenceNumber)
                    .verificationStatus(Document.VerificationStatus.PENDING)
                    .uploadDate(LocalDateTime.now())
                    .isActive(true)
                    .build();

            Document savedDocument = documentRepository.save(document);

            return convertToResponse(savedDocument);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    private DocumentResponse convertToResponse(Document document) {
        return DocumentResponse.builder()
                .id(document.getId())
                .applicationId(document.getApplicationId())
                .documentType(document.getDocumentType())
                .originalFilename(document.getOriginalFilename())
                .fileSize(document.getFileSize())
                .mimeType(document.getMimeType())
                .uploadDate(document.getUploadDate())
                .verificationStatus(document.getVerificationStatus())
                .verificationComments(document.getVerificationComments())
                .build();
    }

    // Download by structured filename
    public Resource downloadDocumentByStructuredName(String structuredFilename) {
        Document document = documentRepository.findByStructuredFilenameAndIsActiveTrue(structuredFilename)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        return downloadDocument(document.getId());
    }

    public Resource downloadDocument(String documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        // Fixed: changed from document.getIsActive() to document.isActive()
        if (!document.getIsActive()) {
            throw new DocumentNotFoundException("Document is not active");
        }

        try {
            String fullPath = uploadDir + "/" + document.getFilePath();
            Path filePath = Paths.get(fullPath);

            if (!Files.exists(filePath)) {
                throw new DocumentNotFoundException("File not found on disk");
            }

            // Fixed: removed org.springframework.core.io prefix since we import it
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new DocumentNotFoundException("File not readable");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file", e);
        }
    }

    // Get all documents for an application with organized structure
    public ApplicationDocumentsResponse getApplicationDocuments(String applicationId) {
        List<Document> documents = documentRepository.findByApplicationIdAndIsActiveTrue(applicationId);

        // Group by document type
        Map<String, List<DocumentResponse>> documentsByType = documents.stream()
                .collect(Collectors.groupingBy(
                        Document::getDocumentType,
                        Collectors.mapping(this::convertToResponse, Collectors.toList())
                ));

        return ApplicationDocumentsResponse.builder()
                .applicationId(applicationId)
                .documentsByType(documentsByType)
                .totalDocuments(documents.size())
                .build();
    }
    //download latest document
    public Resource downloadLatestDocumentFile(String applicationId, String documentType) {
        Document document = documentRepository
                .findTopByApplicationIdAndDocumentTypeOrderBySequenceNumberDesc(applicationId, documentType).orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        return new FileSystemResource(uploadDir +"/"+ document.getFilePath());
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Validate file size (10MB max)
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("File size too large. Max 10MB allowed");
        }

        // Validate file type
        String contentType = file.getContentType();
        if (!isValidFileType(contentType)) {
            throw new IllegalArgumentException("Invalid file type. Only PDF, JPG, PNG allowed");
        }
    }

    private boolean isValidFileType(String contentType) {
        return contentType != null && (
                contentType.equals("application/pdf") ||
                        contentType.equals("image/jpeg") ||
                        contentType.equals("image/png")
        );
    }
}