package com.admission_crm.lead_management.Controller;

import com.admission_crm.lead_management.Exception.ResourceNotFoundException;
import com.admission_crm.lead_management.Payload.DepartmentDTO;
import com.admission_crm.lead_management.Payload.Response.ApiResponse;
import com.admission_crm.lead_management.Service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Slf4j
@RequiredArgsConstructor
public class DepartmentController {


    private final DepartmentService departmentService;

    @PostMapping
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN') or hasRole('INSTITUTE_ADMIN')")
    public ResponseEntity<ApiResponse> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        try {
            log.info("REST request to create department: {}", departmentDTO.getName());
            DepartmentDTO createdDepartment = departmentService.createDepartment(departmentDTO);
            return ResponseEntity.ok(ApiResponse.success("Department created successfully", createdDepartment));
        } catch (ResourceNotFoundException e) {
            log.warn("Failed to create department: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Program not found", e.getMessage()));
        } catch (IllegalArgumentException e) {
            log.warn("Failed to create department: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid request", e.getMessage()));
        } catch (Exception e) {
            log.error("Error creating department: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create department", "An unexpected error occurred"));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN') or hasRole('INSTITUTE_ADMIN')")
    public ResponseEntity<ApiResponse> getDepartmentById(@PathVariable String id) {
        try {
            log.info("REST request to get department by ID: {}", id);
            DepartmentDTO department = departmentService.getDepartmentById(id);
            return ResponseEntity.ok(ApiResponse.success("Department retrieved successfully", department));
        } catch (ResourceNotFoundException e) {
            log.warn("Department not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Department not found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error retrieving department: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve department", "An unexpected error occurred"));
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN') or hasRole('INSTITUTE_ADMIN')")
    public ResponseEntity<ApiResponse> getAllDepartments() {
        try {
            log.info("REST request to get all departments");
            List<DepartmentDTO> departments = departmentService.getAllDepartments();
            return ResponseEntity.ok(ApiResponse.success("Departments retrieved successfully", departments));
        } catch (Exception e) {
            log.error("Error retrieving departments: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve departments", "An unexpected error occurred"));
        }
    }

    @GetMapping("/program/{programId}")
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN') or hasRole('INSTITUTE_ADMIN')")
    public ResponseEntity<ApiResponse> getDepartmentsByProgramId(@PathVariable String programId) {
        try {
            log.info("REST request to get departments by Program ID: {}", programId);
            List<DepartmentDTO> departments = departmentService.getDepartmentsByProgramId(programId);
            return ResponseEntity.ok(ApiResponse.success("Departments retrieved successfully", departments));
        } catch (ResourceNotFoundException e) {
            log.warn("Program not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Program not found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error retrieving departments: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve departments", "An unexpected error occurred"));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN') or hasRole('INSTITUTE_ADMIN')")
    public ResponseEntity<ApiResponse> updateDepartment(@PathVariable String id, @RequestBody DepartmentDTO departmentDTO) {
        try {
            log.info("REST request to update department with ID: {}", id);
            DepartmentDTO updatedDepartment = departmentService.updateDepartment(id, departmentDTO);
            return ResponseEntity.ok(ApiResponse.success("Department updated successfully", updatedDepartment));
        } catch (ResourceNotFoundException e) {
            log.warn("Department or Program not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Resource not found", e.getMessage()));
        } catch (IllegalArgumentException e) {
            log.warn("Failed to update department: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid request", e.getMessage()));
        } catch (Exception e) {
            log.error("Error updating department: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update department", "An unexpected error occurred"));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('UNIVERSITY_ADMIN')")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable String id) {
        try {
            log.info("REST request to delete department with ID: {}", id);
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok(ApiResponse.success("Department deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            log.warn("Department not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Department not found", e.getMessage()));
        } catch (Exception e) {
            log.error("Error deleting department: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete department", "An unexpected error occurred"));
        }
    }
}
