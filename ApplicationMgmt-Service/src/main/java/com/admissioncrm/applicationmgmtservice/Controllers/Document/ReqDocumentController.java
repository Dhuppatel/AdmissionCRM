package com.admissioncrm.applicationmgmtservice.Controllers.Document;

import com.admissioncrm.applicationmgmtservice.Entities.ApplicationFormMGMT.RequiredDocument;
import com.admissioncrm.applicationmgmtservice.Services.Document.RequiredDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application/programs/{programId}/required-documents")
public class ReqDocumentController {

    @Autowired
    private RequiredDocumentService service;

    @GetMapping
    public List<RequiredDocument> getAll(@PathVariable String programId) {
        return service.getActiveDocuments(programId);
    }

    @PostMapping
    public RequiredDocument create(@PathVariable String programId, @RequestBody RequiredDocument doc) {
        return service.createDocument(programId, doc);
    }

    @PutMapping("/{id}")
    public RequiredDocument update(@PathVariable String id, @RequestBody RequiredDocument doc) {
        return service.updateDocument(id, doc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteDocument(id);
    }


}
