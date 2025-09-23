package com.admissioncrm.applicationmgmtservice.Services.Document;

import com.admissioncrm.applicationmgmtservice.Entities.ApplicationFormMGMT.RequiredDocument;
import com.admissioncrm.applicationmgmtservice.Repositories.Document.RequiredDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequiredDocumentService {

    @Autowired
    private RequiredDocumentRepository repository;


    public List<RequiredDocument> getActiveDocuments(String programId) {
        return repository.findByProgramId(programId);
    }

    public RequiredDocument createDocument(String programId, RequiredDocument doc) {

        doc.setProgramId(programId);
        return repository.save(doc);
    }

    public RequiredDocument updateDocument(String id, RequiredDocument doc) {
        RequiredDocument existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        existing.setName(doc.getName());
        existing.setDescription(doc.getDescription());
        existing.setRequired(doc.isRequired());
        existing.setActive(doc.isActive());
        existing.setDefault(doc.isDefault());
        existing.setMaxFileSize(doc.getMaxFileSize());
        existing.setAllowedFormats(doc.getAllowedFormats());
        return repository.save(existing);
    }

    public void deleteDocument(String id) {
        repository.deleteById(id);
    }
}