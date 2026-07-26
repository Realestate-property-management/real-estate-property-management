package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import com.example.demo.models.Lease;
import com.example.demo.service.LeaseService;

@RestController
@RequestMapping("/api/leases")
@CrossOrigin(origins = "*")
public class LeaseController {

    @Autowired
    private LeaseService leaseService;

    // ============================
    // Create Lease
    // ============================
    @PostMapping
    public Lease saveLease(@Valid @RequestBody Lease lease) {
        return leaseService.saveLease(lease);
    }

    // ============================
    // Get All Leases
    // ============================
    @GetMapping
    public List<Lease> getAllLeases() {
        return leaseService.getAllLeases();
    }

    // ============================
    // Get Lease By ID
    // ============================
    @GetMapping("/{id}")
    public Lease getLeaseById(@PathVariable Long id) {
        return leaseService.getLeaseById(id);
    }

    // ============================
    // Update Lease
    // ============================
    @PutMapping("/{id}")
    public Lease updateLease(@PathVariable Long id,
                             @Valid @RequestBody Lease lease) {
        return leaseService.updateLease(id, lease);
    }

    // ============================
    // Delete Lease
    // ============================
    @DeleteMapping("/{id}")
    public String deleteLease(@PathVariable Long id) {
        leaseService.deleteLease(id);
        return "Lease deleted successfully.";
    }

    // ============================
    // Upload Lease PDF to Amazon S3
    // ============================
    @PostMapping("/{id}/upload")
    public Lease uploadLeaseDocument(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        return leaseService.uploadLeaseDocument(id, file);
    }

    // ============================
    // Download Lease PDF from Amazon S3
    // ============================
    @GetMapping("/{id}/document")
    public ResponseEntity<byte[]> downloadLeaseDocument(
            @PathVariable Long id) {

        byte[] file = leaseService.downloadLeaseDocument(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"lease.pdf\"")
                .body(file);
    }

    // ============================
    // Delete Lease PDF from Amazon S3
    // ============================
    @DeleteMapping("/{id}/document")
    public String deleteLeaseDocument(@PathVariable Long id) {

        leaseService.deleteLeaseDocument(id);

        return "Lease document deleted successfully.";
    }
}