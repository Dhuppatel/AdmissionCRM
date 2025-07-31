package com.admission_crm.lead_management.Controller;

import com.admission_crm.lead_management.Payload.Request.CourseCreateRequest;
import com.admission_crm.lead_management.Payload.Response.CourseResponse;
import com.admission_crm.lead_management.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Map<String, Object>> createCourse(@RequestBody CourseCreateRequest request) {
        try {
            CourseResponse course = courseService.createCourse(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Course created successfully");
            response.put("data", course);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to create course");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Map<String, Object>> getAllCourses() {
        try {
            List<CourseResponse> courses = courseService.getAllCourses();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Courses retrieved successfully");
            response.put("data", courses);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve courses");
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Map<String, Object>> getCourseById(@PathVariable String id) {
        try {
            CourseResponse course = courseService.getCourseById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Course retrieved successfully");
            response.put("data", course);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to retrieve course");
            response.put("error", e.getMessage());
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Map<String, Object>> updateCourse(@PathVariable String id, @RequestBody CourseCreateRequest request) {
        try {
            CourseResponse course = courseService.updateCourse(id, request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Course updated successfully");
            response.put("data", course);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update course");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteCourse(@PathVariable String id) {
        try {
            courseService.deleteCourse(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Course deleted successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to delete course");
            response.put("error", e.getMessage());
            return ResponseEntity.status(404).body(response);
        }
    }
}
