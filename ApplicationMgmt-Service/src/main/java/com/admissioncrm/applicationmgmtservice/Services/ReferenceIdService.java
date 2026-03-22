package com.admissioncrm.applicationmgmtservice.Services;

import com.admissioncrm.applicationmgmtservice.Repositories.ApplicationFormRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReferenceIdService {

    private final ApplicationFormRepository applicationFormRepository;
    @Transactional
    synchronized public String generateReferenceId() {

        int currentYear = LocalDate.now().getYear();

        List<String> refs = applicationFormRepository.findReferenceIdsByYear(currentYear);

        long maxSequence = refs.stream()
                .map(ref -> ref.substring(ref.length() - 6))
                .mapToLong(Long::parseLong)
                .max()
                .orElse(0L);

        long sequenceNumber = maxSequence + 1;

        return String.format("APP-%d-%06d", currentYear, sequenceNumber);
    }
    public boolean isReferenceIdExists(String RefId)
    {
        return applicationFormRepository.existsByReferenceId(RefId);
    }
}