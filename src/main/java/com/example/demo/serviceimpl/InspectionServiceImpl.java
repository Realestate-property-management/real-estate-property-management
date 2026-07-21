package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Inspection;
import com.example.demo.repository.InspectionRepository;
import com.example.demo.service.InspectionService;

@Service
public class InspectionServiceImpl implements InspectionService {

    @Autowired
    private InspectionRepository inspectionRepository;

    @Override
    public Inspection saveInspection(Inspection inspection) {
        return inspectionRepository.save(inspection);
    }

    @Override
    public Inspection updateInspection(Long id, Inspection inspection) {

        Inspection existingInspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inspection not found with ID: " + id));

        existingInspection.setProperty(inspection.getProperty());
        existingInspection.setInspectionType(inspection.getInspectionType());
        existingInspection.setTitle(inspection.getTitle());
        existingInspection.setDescription(inspection.getDescription());
        existingInspection.setScheduledDate(inspection.getScheduledDate());
        existingInspection.setDueDate(inspection.getDueDate());
        existingInspection.setStatus(inspection.getStatus());
        existingInspection.setPriority(inspection.getPriority());
        existingInspection.setContractor(inspection.getContractor());
        existingInspection.setSourceType(inspection.getSourceType());
        existingInspection.setExternalEventId(inspection.getExternalEventId());
        existingInspection.setNotes(inspection.getNotes());
        existingInspection.setCreatedAt(inspection.getCreatedAt());
        existingInspection.setUpdatedAt(inspection.getUpdatedAt());

        return inspectionRepository.save(existingInspection);
    }

    @Override
    public void deleteInspection(Long id) {

        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inspection not found with ID: " + id));

        inspectionRepository.delete(inspection);
    }

    @Override
    public Inspection getInspectionById(Long id) {

        return inspectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inspection not found with ID: " + id));
    }

    @Override
    public List<Inspection> getAllInspections() {

        return inspectionRepository.findAll();
    }

  
    @Override
    public Inspection updateStatus(Long id, String status) {

        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inspection not found with ID: " + id));

        inspection.setStatus(status);

        return inspectionRepository.save(inspection);
    }
}