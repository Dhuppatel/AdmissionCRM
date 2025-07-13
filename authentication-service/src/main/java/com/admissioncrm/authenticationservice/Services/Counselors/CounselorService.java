package com.admissioncrm.authenticationservice.Services.Counselors;

import com.admissioncrm.authenticationservice.DTO.Counselor.CounselorDTO;
import com.admissioncrm.authenticationservice.Entities.CoreEntities.User;
import com.admissioncrm.authenticationservice.Entities.CounselorDetails;
import com.admissioncrm.authenticationservice.Repositories.CounselorDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CounselorService {

    private final CounselorDetailsRepository counselorDetailsRepository;

    public List<CounselorDTO> getAllActiveCounselors() {
        List<CounselorDetails> counselorDetails = counselorDetailsRepository.findByUserIsActiveTrue();

        return counselorDetails.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CounselorDTO getCounselorById(String counselorId) {
        return counselorDetailsRepository.findById(counselorId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<CounselorDTO> getCounselorsByInstitute(String institute) {
        List<CounselorDetails> counselorDetails =
                counselorDetailsRepository.findByAssignedInstituteAndUserIsActiveTrue(institute);

        return counselorDetails.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CounselorDTO convertToDTO(CounselorDetails counselorDetails) {
        User user = counselorDetails.getUser();

        return CounselorDTO.builder()
                .id(counselorDetails.getId())
                .name(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .phone(user.getMobileNumber())
                .assignedInstitute(counselorDetails.getAssignedInstitute())
                .expertiseArea(counselorDetails.getExpertiseArea())
                .isActive(user.getIsActive())
                .department(counselorDetails.getExpertiseArea())
                .build();
    }
}