package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.MaintenanceRequest;
import com.example.demo.repository.MaintenanceRequestRepository;
import com.example.demo.service.MaintenanceRequestService;

@Service
public class MaintenanceRequestServiceImpl implements MaintenanceRequestService {

    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;

    @Override
    public MaintenanceRequest saveMaintenanceRequest(MaintenanceRequest request) {
        return maintenanceRequestRepository.save(request);
    }

    @Override
    public MaintenanceRequest updateMaintenanceRequest(Long id, MaintenanceRequest request) {

        MaintenanceRequest existingRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance Request not found with ID: " + id));

        existingRequest.setProperty(request.getProperty());
        existingRequest.setTenant(request.getTenant());
        existingRequest.setTitle(request.getTitle());
        existingRequest.setDescription(request.getDescription());
        existingRequest.setCategory(request.getCategory());
        existingRequest.setPriority(request.getPriority());
        existingRequest.setStatus(request.getStatus());
        existingRequest.setContractor(request.getContractor());
        existingRequest.setScheduledDate(request.getScheduledDate());
        existingRequest.setCompletedDate(request.getCompletedDate());
        existingRequest.setEstimatedCost(request.getEstimatedCost());
        existingRequest.setActualCost(request.getActualCost());
        existingRequest.setCreatedBy(request.getCreatedBy());
        existingRequest.setCreatedAt(request.getCreatedAt());
        existingRequest.setUpdatedAt(request.getUpdatedAt());

        return maintenanceRequestRepository.save(existingRequest);
    }

    @Override
    public void deleteMaintenanceRequest(Long id) {

        MaintenanceRequest existingRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance Request not found with ID: " + id));

        maintenanceRequestRepository.delete(existingRequest);
    }

    @Override
    public MaintenanceRequest getMaintenanceRequestById(Long id) {

        return maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance Request not found with ID: " + id));
    }

    @Override
    public List<MaintenanceRequest> getAllMaintenanceRequests() {

        return maintenanceRequestRepository.findAll();
    }


    @Override
    public MaintenanceRequest updateStatus(Long id, String status) {

        MaintenanceRequest existingRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance Request not found with ID: " + id));

        existingRequest.setStatus(status);

        return maintenanceRequestRepository.save(existingRequest);
    }
}