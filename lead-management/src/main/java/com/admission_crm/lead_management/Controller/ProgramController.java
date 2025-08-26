package com.admission_crm.lead_management.Controller;

import com.admission_crm.lead_management.Exception.ResourceNotFoundException;
import com.admission_crm.lead_management.Payload.ProgramDTO;
import com.admission_crm.lead_management.Payload.Response.ApiResponse;
import com.admission_crm.lead_management.Service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/leads/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;


    @PostMapping
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN') or hasRole('INSTITUTE_ADMIN')")
    public ResponseEntity<ApiResponse> createProgram(@RequestBody ProgramDTO programDTO) {
        try {
            log.info("REST request to create program: {}", programDTO.getName());
            ProgramDTO createdProgram = programService.createProgram(programDTO);
            return ResponseEntity.ok(ApiResponse.success("Program created successfully", createdProgram));
        } catch (ResourceNotFoundException e) {
            log.warn("Failed to create program: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Faculty not found", e.getMessage()));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid request to create program: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid request", e.getMessage()));
        } catch (Exception e) {
            log.error("Error creating program: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create program", "An unexpected error occurred"));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN') or hasRole('INSTITUTE_ADMIN')")
    public ResponseEntity<ApiResponse> getProgramById(@PathVariable String id) {
        try {
            log.info("REST request to get program by ID: {}", id);
            Optional<ProgramDTO> program = programService.getProgramById(id);
            return ResponseEntity.ok(ApiResponse.success("Program retrieved successfully", program));
        } catch (ResourceNotFoundException e) {
            log.warn("Program not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Program not found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error retrieving program: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve program", "An unexpected error occurred"));
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN') or hasRole('INSTITUTE_ADMIN')")
    public ResponseEntity<ApiResponse> getAllPrograms() {
        try {
            log.info("REST request to get all programs");
            List<ProgramDTO> programs = programService.getAllPrograms();
            return ResponseEntity.ok(ApiResponse.success("Programs retrieved successfully", programs));
        } catch (Exception e) {
            log.error("Error retrieving programs: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve programs", "An unexpected error occurred"));
        }
    }

    @GetMapping("/institution/{institutionId}")
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN') or hasRole('INSTITUTE_ADMIN')")
    public ResponseEntity<ApiResponse> getProgramsByInstitution(@PathVariable String institutionId) {
        try {
            log.info("REST request to get programs by faculty ID: {}", institutionId);
            List<ProgramDTO> programs = programService.getProgramsByInstituion(institutionId);
            return ResponseEntity.ok(ApiResponse.success("Programs retrieved successfully", programs));
        } catch (ResourceNotFoundException e) {
            log.warn("Faculty not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Faculty not found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error retrieving programs: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve programs", "An unexpected error occurred"));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN') or hasRole('INSTITUTE_ADMIN')")
    public ResponseEntity<ApiResponse> updateProgram(@PathVariable String id, @RequestBody ProgramDTO programDTO) {
        try {
            log.info("REST request to update program with ID: {}", id);
            Optional<ProgramDTO> updatedProgram = programService.updateProgram(id, programDTO);
            return ResponseEntity.ok(ApiResponse.success("Program updated successfully", updatedProgram));
        } catch (ResourceNotFoundException e) {
            log.warn("Program or faculty not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Resource not found", e.getMessage()));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid request to update program: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid request", e.getMessage()));
        } catch (Exception e) {
            log.error("Error updating program: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update program", "An unexpected error occurred"));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    public ResponseEntity<ApiResponse> deleteProgram(@PathVariable String id) {
        try {
            log.info("REST request to delete program with ID: {}", id);
            programService.deleteProgram(id);
            return ResponseEntity.ok(ApiResponse.success("Program deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            log.warn("Program not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Program not found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error deleting program: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete program", "An unexpected error occurred"));
        }
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN') or hasRole('INSTITUTE_ADMIN')")
    public ResponseEntity<ApiResponse> updateProgramStatus(
            @PathVariable String id,
            @RequestBody Map<String, Boolean> request) {
        try {
            Boolean isActive = request.get("isActive");
            log.info("REST request to update status of program with ID: {} to {}", id, isActive);

            boolean updated = programService.updateProgramStatus(id, isActive);

            if (updated) {
                return ResponseEntity.ok(ApiResponse.success("Program status updated successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Program not found", "No program exists with ID: " + id));
            }
        } catch (Exception e) {
            log.error("Error updating program status: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update program status", "An unexpected error occurred"));
        }
    }


}
