//package com.admission_crm.lead_management.Service;
//
//import com.admission_crm.lead_management.Entity.Academic.Course;
//import com.admission_crm.lead_management.Payload.Request.CourseCreateRequest;
//import com.admission_crm.lead_management.Payload.Response.CourseResponse;
//import com.admission_crm.lead_management.Repository.CourseRepository;
//import com.admission_crm.lead_management.Repository.DepartmentRepository;
//import com.admission_crm.lead_management.Repository.InstitutionRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class CourseService {
//    private final CourseRepository courseRepository;
//    private final InstitutionRepository institutionRepository;
//    private final DepartmentRepository departmentRepository;
//
//    public CourseResponse createCourse(CourseCreateRequest request) {
//        // Validate required fields
//        if (request.getName() == null || request.getName().isEmpty()) {
//            throw new IllegalArgumentException("Course name is required");
//        }
//        if (request.getCode() == null || request.getCode().isEmpty()) {
//            throw new IllegalArgumentException("Course code is required");
//        }
//        if (request.getInstitutionId() == null || request.getInstitutionId().isEmpty()) {
//            throw new IllegalArgumentException("Institution ID is required");
//        }
//        if (request.getDepartmentId() == null || request.getDepartmentId().isEmpty()) {
//            throw new IllegalArgumentException("Department ID is required");
//        }
//        if (request.getDuration() == null || request.getDuration().isEmpty()) {
//            throw new IllegalArgumentException("Duration is required");
//        }
//
//        // Check if code is unique
//        if (courseRepository.existsByCode(request.getCode())) {
//            throw new IllegalArgumentException("Course code already exists");
//        }
//
//        // Verify institution and department exist
//        if (!institutionRepository.existsById(request.getInstitutionId())) {
//            throw new IllegalArgumentException("Invalid institution ID");
//        }
//        if (!departmentRepository.existsById(request.getDepartmentId())) {
//            throw new IllegalArgumentException("Invalid department ID");
//        }
//
//        // Map DTO to entity
//        Course course = new Course();
//        course.setName(request.getName());
//        course.setCode(request.getCode());
//        course.setDescription(request.getDescription());
//        course.setDuration(request.getDuration());
//        course.setInstitutionId(request.getInstitutionId());
//        course.setDepartmentId(request.getDepartmentId());
//        course.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
//        course.setFees(request.getFees());
//
//        // Save to database
//        Course savedCourse = courseRepository.save(course);
//
//        // Map to response DTO
//        return mapToResponse(savedCourse);
//    }
//
//    public List<CourseResponse> getAllCourses() {
//        return courseRepository.findAll().stream()
//                .map(this::mapToResponse)
//                .collect(Collectors.toList());
//    }
//
//    public CourseResponse getCourseById(String id) {
//        Course course = courseRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + id));
//        return mapToResponse(course);
//    }
//
//    public CourseResponse updateCourse(String id, CourseCreateRequest request) {
//        // Validate required fields
//        if (request.getName() == null || request.getName().isEmpty()) {
//            throw new IllegalArgumentException("Course name is required");
//        }
//        if (request.getCode() == null || request.getCode().isEmpty()) {
//            throw new IllegalArgumentException("Course code is required");
//        }
//        if (request.getInstitutionId() == null || request.getInstitutionId().isEmpty()) {
//            throw new IllegalArgumentException("Institution ID is required");
//        }
//        if (request.getDepartmentId() == null || request.getDepartmentId().isEmpty()) {
//            throw new IllegalArgumentException("Department ID is required");
//        }
//        if (request.getDuration() == null || request.getDuration().isEmpty()) {
//            throw new IllegalArgumentException("Duration is required");
//        }
//
//        // Find existing course
//        Course course = courseRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + id));
//
//        // Check if code is unique (excluding current course)
//        courseRepository.findByCode(request.getCode())
//                .ifPresent(existing -> {
//                    if (!existing.getId().equals(id)) {
//                        throw new IllegalArgumentException("Course code already exists");
//                    }
//                });
//
//        // Verify institution and department exist
//        if (!institutionRepository.existsById(request.getInstitutionId())) {
//            throw new IllegalArgumentException("Invalid institution ID");
//        }
//        if (!departmentRepository.existsById(request.getDepartmentId())) {
//            throw new IllegalArgumentException("Invalid department ID");
//        }
//
//        // Update fields
//        course.setName(request.getName());
//        course.setCode(request.getCode());
//        course.setDescription(request.getDescription());
//        course.setDuration(request.getDuration());
//        course.setInstitutionId(request.getInstitutionId());
//        course.setDepartmentId(request.getDepartmentId());
//        course.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
//
//        // Save updated course
//        Course updatedCourse = courseRepository.save(course);
//
//        return mapToResponse(updatedCourse);
//    }
//
//    public void deleteCourse(String id) {
//        if (!courseRepository.existsById(id)) {
//            throw new IllegalArgumentException("Course not found with ID: " + id);
//        }
//        courseRepository.deleteById(id);
//    }
//
//    private CourseResponse mapToResponse(Course course) {
//        return new CourseResponse(
//                course.getId(),
//                course.getName(),
//                course.getCode(),
//                course.getDescription(),
//                course.getDuration(),
//                course.getFees(),
//                course.getInstitutionId(),
//                course.getDepartmentId(),
//                course.getIsActive(),
//                course.getInterestedLeads(),
//                course.getApplications(),
//                course.getCreatedAt(),
//                course.getUpdatedAt()
//        );
//    }
//}
