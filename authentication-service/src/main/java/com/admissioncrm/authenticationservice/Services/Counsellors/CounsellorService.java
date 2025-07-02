package com.admissioncrm.authenticationservice.Services.Counsellors;

import com.admissioncrm.authenticationservice.DTO.Counsellor.CounsellorDTO;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;
import com.admissioncrm.authenticationservice.Entities.CounsellorDetails;
import com.admissioncrm.authenticationservice.Repositories.CounsellorDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CounsellorService {

    private final CounsellorDetailsRepository counsellorDetailsRepository;

    public List<CounsellorDTO> getAllActiveCounsellors() {
        List<CounsellorDetails> counsellorDetails = counsellorDetailsRepository.findByUserIsActiveTrue();

        return counsellorDetails.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CounsellorDTO getCounsellorById(String counsellorId) {
        return counsellorDetailsRepository.findById(counsellorId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<CounsellorDTO> getCounsellorsByInstitute(String institute) {
        List<CounsellorDetails> counsellorDetails =
                counsellorDetailsRepository.findByAssignedInstituteAndUserIsActiveTrue(institute);

        return counsellorDetails.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CounsellorDTO convertToDTO(CounsellorDetails counsellorDetails) {
        User user = counsellorDetails.getUser();

        return CounsellorDTO.builder()
                .id(counsellorDetails.getId())
                .name(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .phone(user.getMobileNumber())
                .assignedInstitute(counsellorDetails.getAssignedInstitute())
                .expertiseArea(counsellorDetails.getExpertiseArea())
                .isActive(user.getIsActive())
                .department(counsellorDetails.getExpertiseArea())
                .build();
    }
}