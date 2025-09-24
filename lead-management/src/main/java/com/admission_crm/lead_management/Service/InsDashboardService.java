package com.admission_crm.lead_management.Service;

import com.admission_crm.lead_management.Entity.CoreEntities.Institution;
import com.admission_crm.lead_management.Repository.InstitutionRepository;
import com.admission_crm.lead_management.Service.Leads.LeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InsDashboardService {
    private final DepartmentService departmentService;
    private final InstitutionRepository institutionRepository;
    private final LeadService leadService;

    public Object getDashboardStats(String instituteId) {


        Map<String,Long> leadStats=leadService.getLeadConversionFunnel(instituteId);

        long activeLeads=leadStats.get("CONTACTED");
        activeLeads+=leadStats.get("FOLLOW_UP");
        activeLeads+=leadStats.get("IN_PROGRESS");
        activeLeads+=leadStats.get("ASSIGNED");
        activeLeads+=leadStats.get("NEW");
        activeLeads+=leadStats.get("QUEUED");

        long totalLeads=leadStats.get("CONVERTED");
        totalLeads+=leadStats.get("REJECTED");
        totalLeads+=activeLeads;


        Institution institution=institutionRepository.findById(instituteId).get();

        Map<String, Object> stats = new HashMap<>();
        stats.put("departmentCount", departmentService.countDistinctDepartmentsByInstituteId(instituteId));
        stats.put("totalLeads", totalLeads); // dummy, replace with repo call
        stats.put("activeLeads", activeLeads);
        stats.put("totalStudents", 125); // dummy
        stats.put("totalApplications", 07); // dummy
        stats.put("todayApplications", 1);//dummy
        stats.put("acceptanceRate", 72.56);//dummmy
        stats.put("totalCounselors",institution.getCounselors().size());
        return stats;
    }
}
