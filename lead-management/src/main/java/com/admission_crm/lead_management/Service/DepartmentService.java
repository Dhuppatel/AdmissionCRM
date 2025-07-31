package com.admission_crm.lead_management.Service;

import com.admission_crm.lead_management.Entity.Academic.Department;
import com.admission_crm.lead_management.Exception.ResourceNotFoundException;
import com.admission_crm.lead_management.Payload.DepartmentDTO;
import com.admission_crm.lead_management.Repository.DepartmentRepository;
import com.admission_crm.lead_management.Repository.InstitutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final InstitutionRepository institutionRepository;

    public DepartmentService(DepartmentRepository departmentRepository, InstitutionRepository institutionRepository) {
        this.departmentRepository = departmentRepository;
        this.institutionRepository = institutionRepository;
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        log.info("Creating department with code: {}", departmentDTO.getCode());

        // Validate institutionId
        if (departmentDTO.getInstitutionId() != null && !institutionRepository.existsById(departmentDTO.getInstitutionId())) {
            log.warn("Institution not found for ID: {}", departmentDTO.getInstitutionId());
            throw new ResourceNotFoundException("Institution not found with ID: " + departmentDTO.getInstitutionId());
        }

        // Validate unique code
        if (departmentRepository.existsByCode(departmentDTO.getCode())) {
            log.warn("Department code already exists: {}", departmentDTO.getCode());
            throw new IllegalArgumentException("Department code already exists: " + departmentDTO.getCode());
        }

        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setCode(departmentDTO.getCode());
        department.setDescription(departmentDTO.getDescription());
        department.setInstitutionId(departmentDTO.getInstitutionId());
        department.setHeadOfDepartment(departmentDTO.getHeadOfDepartment());
        department.setIsActive(departmentDTO.getIsActive() != null ? departmentDTO.getIsActive() : true);
        department.setCourses(departmentDTO.getCourses());

        Department savedDepartment = departmentRepository.save(department);
        log.info("Department created successfully with ID: {}", savedDepartment.getId());
        return convertToDTO(savedDepartment);
    }

    public DepartmentDTO getDepartmentById(String id) {
        log.info("Fetching department with ID: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department not found with ID: {}", id);
                    return new ResourceNotFoundException("Department not found with ID: " + id);
                });
        return convertToDTO(department);
    }

    public List<DepartmentDTO> getAllDepartments() {
        log.info("Fetching all departments");
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DepartmentDTO> getDepartmentsByInstitutionId(String institutionId) {
        log.info("Fetching departments for institution ID: {}", institutionId);
        if (!institutionRepository.existsById(institutionId)) {
            log.warn("Institution not found with ID: {}", institutionId);
            throw new ResourceNotFoundException("Institution not found with ID: " + institutionId);
        }
        return departmentRepository.findByInstitutionId(institutionId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO) {
        log.info("Updating department with ID: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department not found with ID: {}", id);
                    return new ResourceNotFoundException("Department not found with ID: " + id);
                });

        // Validate institutionId
        if (departmentDTO.getInstitutionId() != null && !institutionRepository.existsById(departmentDTO.getInstitutionId())) {
            log.warn("Institution not found for ID: {}", departmentDTO.getInstitutionId());
            throw new ResourceNotFoundException("Institution not found with ID: " + departmentDTO.getInstitutionId());
        }

        // Validate unique code
        if (departmentDTO.getCode() != null && !departmentDTO.getCode().equals(department.getCode())
                && departmentRepository.existsByCode(departmentDTO.getCode())) {
            log.warn("Department code already exists: {}", departmentDTO.getCode());
            throw new IllegalArgumentException("Department code already exists: " + departmentDTO.getCode());
        }

        department.setName(departmentDTO.getName());
        department.setCode(departmentDTO.getCode());
        department.setDescription(departmentDTO.getDescription());
        department.setInstitutionId(departmentDTO.getInstitutionId());
        department.setHeadOfDepartment(departmentDTO.getHeadOfDepartment());
        department.setIsActive(departmentDTO.getIsActive() != null ? departmentDTO.getIsActive() : department.getIsActive());
        department.setCourses(departmentDTO.getCourses());

        Department updatedDepartment = departmentRepository.save(department);
        log.info("Department updated successfully with ID: {}", updatedDepartment.getId());
        return convertToDTO(updatedDepartment);
    }

    public void deleteDepartment(String id) {
        log.info("Deleting department with ID: {}", id);
        if (!departmentRepository.existsById(id)) {
            log.warn("Department not found with ID: {}", id);
            throw new ResourceNotFoundException("Department not found with ID: " + id);
        }
        departmentRepository.deleteById(id);
        log.info("Department deleted successfully with ID: {}", id);
    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setCode(department.getCode());
        dto.setDescription(department.getDescription());
        dto.setInstitutionId(department.getInstitutionId());
        dto.setHeadOfDepartment(department.getHeadOfDepartment());
        dto.setIsActive(department.getIsActive());
        dto.setCourses(department.getCourses());
        return dto;
    }
}