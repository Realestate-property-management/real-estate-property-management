package com.example.demo.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DashboardResponse;
import com.example.demo.repository.ComplianceIssueRepository;
import com.example.demo.repository.InspectionRepository;
import com.example.demo.repository.LeaseRepository;
import com.example.demo.repository.MaintenanceRequestRepository;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.TenantRepository;
import com.example.demo.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;

    @Autowired
    private InspectionRepository inspectionRepository;

    @Autowired
    private ComplianceIssueRepository complianceIssueRepository;

    @Override
    public DashboardResponse getDashboardDetails() {

        DashboardResponse dashboard = new DashboardResponse();

        dashboard.setTotalProperties(propertyRepository.count());

        dashboard.setTotalTenants(tenantRepository.count());

        dashboard.setActiveLeases(leaseRepository.count());

        dashboard.setPendingMaintenance(maintenanceRequestRepository.count());

        dashboard.setUpcomingInspections(inspectionRepository.count());

        dashboard.setComplianceIssues(complianceIssueRepository.count());

        return dashboard;
    }

}