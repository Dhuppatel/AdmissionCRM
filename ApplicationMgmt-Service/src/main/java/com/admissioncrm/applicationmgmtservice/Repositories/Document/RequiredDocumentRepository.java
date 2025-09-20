package com.admissioncrm.applicationmgmtservice.Repositories.Document;

import com.admissioncrm.applicationmgmtservice.Entities.ApplicationFormMGMT.RequiredDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequiredDocumentRepository extends JpaRepository<RequiredDocument, String> {
    List<RequiredDocument> findByProgramIdAndActiveTrue(String instituteId);
}