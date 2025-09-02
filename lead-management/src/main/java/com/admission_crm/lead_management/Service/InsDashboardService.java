package com.admission_crm.lead_management.Service;

import com.admission_crm.lead_management.Entity.CoreEntities.Institution;
import com.admission_crm.lead_management.Repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InsDashboardService {
    private final DepartmentService departmentService;
    private final InstitutionRepository institutionRepository;

    public Object getDashboardStats(String instituteId) {



        Institution institution=institutionRepository.findById(instituteId).get();

        Map<String, Object> stats = new HashMap<>();
        stats.put("departmentCount", departmentService.countDistinctDepartmentsByInstituteId(instituteId));
        stats.put("totalLeads", institution.getLeads().size()); // dummy, replace with repo call
        stats.put("totalStudents", 125); // dummy
        stats.put("totalApplications", 15); // dummy
        stats.put("todayApplications", 1);//dummy
        stats.put("acceptanceRate", 72.56);//dummmy
        stats.put("totalCounselors",institution.getCounselors().size());
        return stats;
    }
}
