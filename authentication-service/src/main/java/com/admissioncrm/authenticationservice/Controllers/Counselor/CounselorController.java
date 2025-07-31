package com.admissioncrm.authenticationservice.Controllers.Counselor;

import com.admissioncrm.authenticationservice.DTO.Counselor.CounselorDTO;
import com.admissioncrm.authenticationservice.Services.Counselors.CounselorService;
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
public class CounselorController {

    private final CounselorService counselorService;

    @GetMapping("/getall")
    public ResponseEntity<List<CounselorDTO>> getAllCounsellors() {
        List<CounselorDTO> counselors = counselorService.getAllActiveCounselors();
        return ResponseEntity.ok(counselors);
    }



    @GetMapping("/{counsellorId}")
    public ResponseEntity<CounselorDTO> getCounsellorById(@PathVariable String counselorId) {
        CounselorDTO counselor = counselorService.getCounselorById(counselorId);
        if (counselor != null) {
            return ResponseEntity.ok(counselor);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/institute/{institute}")
    public ResponseEntity<List<CounselorDTO>> getCounselorsByInstitute(@PathVariable String institute) {
        List<CounselorDTO> counselors = counselorService.getCounselorsByInstitute(institute);
        return ResponseEntity.ok(counselors);
    }
}