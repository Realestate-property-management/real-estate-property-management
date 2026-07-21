package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Inspection;
import com.example.demo.service.InspectionService;

@RestController
@RequestMapping("/api/inspections")
@CrossOrigin(origins = "*")
public class InspectionController {

    @Autowired
    private InspectionService inspectionService;

    @PostMapping
    public Inspection saveInspection(@RequestBody Inspection inspection) {
        return inspectionService.saveInspection(inspection);
    }

    @GetMapping
    public List<Inspection> getAllInspections() {
        return inspectionService.getAllInspections();
    }

    @GetMapping("/{id}")
    public Inspection getInspectionById(@PathVariable Long id) {
        return inspectionService.getInspectionById(id);
    }

    @PutMapping("/{id}")
    public Inspection updateInspection(@PathVariable Long id,
                                       @RequestBody Inspection inspection) {
        return inspectionService.updateInspection(id, inspection);
    }

    @DeleteMapping("/{id}")
    public String deleteInspection(@PathVariable Long id) {
        inspectionService.deleteInspection(id);
        return "Inspection deleted successfully.";
    }

    @PatchMapping("/{id}/status")
    public Inspection updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return inspectionService.updateStatus(id, status);
    }
}