package com.admissioncrm.authenticationservice.Controllers.Counsellor;

import com.admissioncrm.authenticationservice.DTO.Counsellor.CounsellorDTO;
import com.admissioncrm.authenticationservice.Services.Counsellors.CounsellorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/counsellor")
@RequiredArgsConstructor
public class CounsellorController {

    private final CounsellorService counsellorService;

    @GetMapping("/getall")
    public ResponseEntity<List<CounsellorDTO>> getAllCounsellors() {
        List<CounsellorDTO> counsellors = counsellorService.getAllActiveCounsellors();
        return ResponseEntity.ok(counsellors);
    }

    @GetMapping("/{counsellorId}")
    public ResponseEntity<CounsellorDTO> getCounsellorById(@PathVariable String counsellorId) {
        CounsellorDTO counsellor = counsellorService.getCounsellorById(counsellorId);
        if (counsellor != null) {
            return ResponseEntity.ok(counsellor);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/institute/{institute}")
    public ResponseEntity<List<CounsellorDTO>> getCounsellorsByInstitute(@PathVariable String institute) {
        List<CounsellorDTO> counsellors = counsellorService.getCounsellorsByInstitute(institute);
        return ResponseEntity.ok(counsellors);
    }
}