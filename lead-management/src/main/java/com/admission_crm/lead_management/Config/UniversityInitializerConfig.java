package com.admission_crm.lead_management.Config;


import com.admission_crm.lead_management.Entity.CoreEntities.University;
import com.admission_crm.lead_management.Repository.UniversityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class UniversityInitializerConfig {

    @Bean
    public CommandLineRunner initUniversity(UniversityRepository universityRepository) {
        return args -> {
            String defaultUniversityName = "CHARUSAT UNIVERSITY";

            // Check if university already exists
            boolean exists = universityRepository.existsByName(defaultUniversityName);

            if (!exists) {
                University university = new University();
                university.setName(defaultUniversityName);
                university.setAddress("Charotar University of Science and Technology, " +
                        "CHARUSAT Campus,Off. Nadiad-Petlad Highway, Changa-388421");
                university.setPhone("+91-2697265011");
                university.setEmail("info@charusat.ac.in");
                university.setWebsite("https://www.charusat.ac.in/");
                university.setLogoUrl("https://example.com/logo.png");
                university.setAdmins(Collections.emptyList());
                university.setInstitutions(Collections.emptyList());

                universityRepository.save(university);
                System.out.println("✅ Default University initialized.");
            } else {
                System.out.println("ℹ️ Default University already exists, skipping initialization.");
            }
        };
    }
}