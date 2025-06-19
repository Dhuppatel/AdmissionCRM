package com.admissioncrm.applicationmgmtservice.Services;

import com.admissioncrm.applicationmgmtservice.Repositories.ApplicationFormRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ReferenceIdService {

    private final ApplicationFormRepository applicationFormRepository;

    @Transactional
    synchronized public String generateReferenceId(){

        int currentYear = LocalDate.now().getYear();

        long applicationsCountThisYear = applicationFormRepository.countByCredatedAtYear(currentYear);

        long sequenceNumber=applicationsCountThisYear+1;


        // Format: APP-2025-000001
        return String.format("APP-%d-%06d", currentYear, sequenceNumber);

    }
    public boolean isReferenceIdExists(String RefId)
    {
        return applicationFormRepository.existsByReferenceId(RefId);
    }
}
