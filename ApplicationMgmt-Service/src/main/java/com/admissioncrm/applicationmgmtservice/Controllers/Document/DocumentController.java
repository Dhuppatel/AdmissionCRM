package com.admissioncrm.applicationmgmtservice.Controllers.Document;

import com.admissioncrm.applicationmgmtservice.Dto.Document.ApplicationDocumentsResponse;
import com.admissioncrm.applicationmgmtservice.Dto.Document.DocumentResponse;
import com.admissioncrm.applicationmgmtservice.Services.Document.DocumentService;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/application/document/")
public class DocumentController {

    @Autowired
    private DocumentService documentService;


    @PostMapping("/s/upload/{applicationId}")
    public ResponseEntity<DocumentResponse> uploadDocument(
            @PathVariable String applicationId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") String documentType) {

        DocumentResponse response = documentService.uploadDocument(file, applicationId, documentType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getall/{applicationId}")
    public ResponseEntity<ApplicationDocumentsResponse> getApplicationDocuments(
            @PathVariable String applicationId) {
        ApplicationDocumentsResponse response = documentService.getApplicationDocuments(applicationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/structured/{structuredFilename}")
    public ResponseEntity<Resource> downloadByStructuredName(
            @PathVariable String structuredFilename) {
        Resource resource = documentService.downloadDocumentByStructuredName(structuredFilename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + structuredFilename + "\"")
                .body(resource);
    }
    // download latest document retrival by appId and doctype
    @GetMapping("/download/latest/{applicationId}")
    public ResponseEntity<Resource> downloadLatestDocument(
            @PathVariable String applicationId,
            @RequestParam("documentType") String documentType) {

        Resource file = documentService.downloadLatestDocumentFile(applicationId, documentType);
        if (file == null ) {
            System.out.println(file == null);
            System.out.println(!file.exists());
            System.out.println("fucked");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


    @GetMapping("/preview/structured/{structuredFilename}")
    public ResponseEntity<Resource> previewByStructuredName(
            @PathVariable String structuredFilename) {
        Resource resource = documentService.downloadDocumentByStructuredName(structuredFilename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(resource);
    }



}