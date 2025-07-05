//package com.admissioncrm.applicationmgmtservice.Controllers.Document;
//
//import com.admissioncrm.applicationmgmtservice.Dto.Document.ApplicationDocumentsResponse;
//import com.admissioncrm.applicationmgmtservice.Dto.Document.DocumentResponse;
//import jakarta.annotation.Resource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api/documents")
//public class DocumentController {
//
//    @Autowired
//    private DocumentService documentService;
//
//
//    @PostMapping("/upload/{applicationId}")
//    public ResponseEntity<DocumentResponse> uploadDocument(
//            @PathVariable Long applicationId,
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("documentType") String documentType) {
//
//        DocumentResponse response = documentService.uploadDocument(file, applicationId, documentType);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/application/{applicationId}")
//    public ResponseEntity<ApplicationDocumentsResponse> getApplicationDocuments(
//            @PathVariable Long applicationId) {
//        ApplicationDocumentsResponse response = documentService.getApplicationDocuments(applicationId);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/download/structured/{structuredFilename}")
//    public ResponseEntity<Resource> downloadByStructuredName(
//            @PathVariable String structuredFilename) {
//        Resource resource = documentService.downloadDocumentByStructuredName(structuredFilename);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=\"" + structuredFilename + "\"")
//                .body(resource);
//    }
//
//    @GetMapping("/preview/structured/{structuredFilename}")
//    public ResponseEntity<Resource> previewByStructuredName(
//            @PathVariable String structuredFilename) {
//        Resource resource = documentService.downloadDocumentByStructuredName(structuredFilename);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
//                .body(resource);
//    }
//}