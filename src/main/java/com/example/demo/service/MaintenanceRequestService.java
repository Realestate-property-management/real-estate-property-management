package com.example.demo.service;

import java.util.List;
import com.example.demo.models.MaintenanceRequest;

public interface MaintenanceRequestService {

    MaintenanceRequest saveMaintenanceRequest(MaintenanceRequest request);

    MaintenanceRequest updateMaintenanceRequest(Long id, MaintenanceRequest request);

    void deleteMaintenanceRequest(Long id);

    MaintenanceRequest getMaintenanceRequestById(Long id);

    List<MaintenanceRequest> getAllMaintenanceRequests();

    MaintenanceRequest updateStatus(Long id, String status);

}