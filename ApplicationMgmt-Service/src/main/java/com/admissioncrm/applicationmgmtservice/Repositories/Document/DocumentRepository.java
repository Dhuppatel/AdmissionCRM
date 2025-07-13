package com.admissioncrm.applicationmgmtservice.Repositories.Document;

import com.admissioncrm.applicationmgmtservice.Entities.Document.Document;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

    List<Document> findByApplicationIdAndIsActiveTrue(String applicationId);

    List<Document> findByApplicationIdAndDocumentTypeAndIsActiveTrue(String applicationId, String documentType);

    Optional<Document> findByStructuredFilenameAndIsActiveTrue(String structuredFilename);

    Optional<Document> findTopByApplicationIdAndDocumentTypeOrderBySequenceNumberDesc (String applicationId, String documentType);

    int countByApplicationIdAndDocumentTypeAndIsActiveTrue(String applicationId, String documentType);

    // Find documents by reference ID pattern
    @Query("SELECT d FROM Document d JOIN d.application a WHERE a.referenceId = :refId AND d.isActive = true")
    List<Document> findByApplicationReferenceId(@Param("refId") String refId);

    // Find documents by structured filename pattern
    @Query("SELECT d FROM Document d WHERE d.structuredFilename LIKE :pattern AND d.isActive = true")
    List<Document> findByStructuredFilenamePattern(@Param("pattern") String pattern);
}