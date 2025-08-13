package com.admissioncrm.applicationmgmtservice.Entities.Document;


import com.admissioncrm.applicationmgmtservice.Entities.ApplicationForm.ApplicationForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents",
        indexes = {
                @Index(name = "idx_application_id", columnList = "application_id"),
                @Index(name = "idx_document_type", columnList = "document_type"),
                @Index(name = "idx_verification_status", columnList = "verification_status"),
                @Index(name = "idx_structured_filename", columnList = "structured_filename"),
                @Index(name = "idx_upload_date", columnList = "upload_date"),
                @Index(name = "idx_app_type_status", columnList = "application_id, document_type, verification_status"),
                @Index(name = "idx_status_date", columnList = "verification_status, upload_date")
        })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "application_id", nullable = false)
    private String applicationId;

    @Column(name = "document_type", nullable = false, length = 50)
    private String documentType;

    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "structured_filename", nullable = false, unique = true)
    private String structuredFilename;

    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @Column(name = "sequence_number", nullable = false)
    @Builder.Default
    private Integer sequenceNumber = 1;

    @Column(name = "upload_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime uploadDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false)
    @Builder.Default
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @Column(name = "verified_by")
    private String verifiedBy;

    @Column(name = "verification_date")
    private LocalDateTime verificationDate;

    @Column(name = "verification_comments", columnDefinition = "TEXT")
    private String verificationComments;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", insertable = false, updatable = false)
    private ApplicationForm application;

    // Enum for Verification Status
    public enum VerificationStatus {
        PENDING("Pending"),
        APPROVED("Approved"),
        REJECTED("Rejected"),
        RESUBMISSION_REQUIRED("Resubmission Required");

        private final String displayName;

        VerificationStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Helper methods
    public boolean isPending() {
        return this.verificationStatus == VerificationStatus.PENDING;
    }

    public boolean isApproved() {
        return this.verificationStatus == VerificationStatus.APPROVED;
    }

    public boolean isRejected() {
        return this.verificationStatus == VerificationStatus.REJECTED;
    }

    public boolean needsResubmission() {
        return this.verificationStatus == VerificationStatus.RESUBMISSION_REQUIRED;
    }

    public String getFileExtension() {
        if (originalFilename == null || originalFilename.lastIndexOf('.') == -1) {
            return "";
        }
        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
    }

    public String getFormattedFileSize() {
        if (fileSize == null) return "0 B";

        long bytes = fileSize;
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    public boolean isImageFile() {
        return mimeType != null && mimeType.startsWith("image/");
    }

    public boolean isPdfFile() {
        return "application/pdf".equals(mimeType);
    }

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
        if (uploadDate == null) {
            uploadDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Custom toString to avoid circular references
    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", applicationId=" + applicationId +
                ", documentType='" + documentType + '\'' +
                ", originalFilename='" + originalFilename + '\'' +
                ", structuredFilename='" + structuredFilename + '\'' +
                ", fileSize=" + fileSize +
                ", verificationStatus=" + verificationStatus +
                ", uploadDate=" + uploadDate +
                ", isActive=" + isActive +
                '}';
    }
}