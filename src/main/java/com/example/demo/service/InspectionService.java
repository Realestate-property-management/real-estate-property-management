package com.example.demo.service;

import java.util.List;
import com.example.demo.models.Inspection;

public interface InspectionService {

    Inspection saveInspection(Inspection inspection);

    Inspection updateInspection(Long id, Inspection inspection);

    void deleteInspection(Long id);

    Inspection getInspectionById(Long id);

    List<Inspection> getAllInspections();

    Inspection updateStatus(Long id, String status);

}