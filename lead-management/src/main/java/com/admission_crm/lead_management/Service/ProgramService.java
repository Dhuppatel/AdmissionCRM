package com.admission_crm.lead_management.Service;

import com.admission_crm.lead_management.Entity.CoreEntities.Program;
import com.admission_crm.lead_management.Exception.ResourceNotFoundException;
import com.admission_crm.lead_management.Payload.ProgramDTO;
import com.admission_crm.lead_management.Repository.InstitutionRepository;
import com.admission_crm.lead_management.Repository.ProgramRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final InstitutionRepository institutionRepository;

    // Create Program
    public ProgramDTO createProgram(ProgramDTO dto) {
        Program p=new Program();
        p.setName(dto.getName());
        p.setCode(dto.getCode());
        p.setDescription(dto.getDescription());
        p.setIsActive(dto.getIsActive());
        p.setInstitution(institutionRepository.findById(dto.getInstitutionId())
                .orElseThrow(() -> new ResourceNotFoundException("Institution not found")));
        Program savedProgram = programRepository.save(p);
        return mapToDTO(savedProgram);
    }

    // Get Program by ID
    public Optional<ProgramDTO> getProgramById(String id) {
        return programRepository.findById(id).map(this::mapToDTO);
    }

    // Get all Programs
    public List<ProgramDTO> getAllPrograms() {
        return programRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update Program
    public Optional<ProgramDTO> updateProgram(String id, ProgramDTO dto) {
        return programRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setCode(dto.getCode());
            existing.setDescription(dto.getDescription());
//            existing.setInstiution(institutionRepository.findById(dto.getInstitutionId())
//                                                    .orElseThrow(() -> new ResourceNotFoundException("Institution not found"))
//                                    );
            existing.setIsActive(dto.getIsActive());
            Program updated = programRepository.save(existing);
            return mapToDTO(updated);
        });
    }

    // Delete (Soft Delete)
    public boolean deleteProgram(String id) {
        return programRepository.findById(id).map(existing -> {
            existing.setIsActive(false); // soft delete
            programRepository.delete(existing);
            return true;
        }).orElse(false);
    }

    // 🔹 Mapping methods
    private ProgramDTO mapToDTO(Program program) {
        return new ProgramDTO(
                program.getId(),
                program.getName(),
                program.getCode(),
                program.getDescription(),
                program.getInstitution() != null ? program.getInstitution().getId() : null,
                program.getIsActive()
        );
    }


    public List<ProgramDTO> getProgramsByInstituion(String institutionId) {
        return programRepository.findByInstitution_Id(institutionId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean updateProgramStatus(String programId, Boolean isActive) {
        return programRepository.findById(programId).map(program -> {
            program.setIsActive(isActive);
            programRepository.save(program);
            return true;
        }).orElse(false);
    }

}
