package com.admissioncrm.applicationmgmtservice.Entities.ApplicationFormMGMT;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "required_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequiredDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // 'photo', 'signature', etc.

    private String name;
    private String description;

    private boolean required = true;
    private boolean active = true;
    private boolean isDefault = false;

    private int maxFileSize; // in MB

    @ElementCollection
    @CollectionTable(name = "document_allowed_formats", joinColumns = @JoinColumn(name = "document_id"))
    @Column(name = "format")
    private List<String> allowedFormats = new ArrayList<>();


    private String programId;
}
