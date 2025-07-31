package com.admission_crm.lead_management.Repository;

import com.admission_crm.lead_management.Entity.Academic.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Optional<Course> findByCode(String code);
    boolean existsByCode(String code);
}
