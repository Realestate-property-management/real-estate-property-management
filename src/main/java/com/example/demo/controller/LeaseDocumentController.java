package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.LeaseDocument;
import com.example.demo.service.LeaseDocumentService;

@RestController
@RequestMapping("/api/lease-documents")
@CrossOrigin(origins = "*")
public class LeaseDocumentController {

    @Autowired
    private LeaseDocumentService leaseDocumentService;

    // ==========================
    // Upload Lease Document to S3
    // ==========================
    @PostMapping("/upload/{leaseId}/{userId}")
    public LeaseDocument uploadLeaseDocument(
            @PathVariable Long leaseId,
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {

        return leaseDocumentService.uploadLeaseDocument(leaseId, userId, file);
    }

    // Save Lease Document
    @PostMapping
    public LeaseDocument saveLeaseDocument(@RequestBody LeaseDocument document) {
        return leaseDocumentService.saveLeaseDocument(document);
    }

    // Get All Lease Documents
    @GetMapping
    public List<LeaseDocument> getAllLeaseDocuments() {
        return leaseDocumentService.getAllLeaseDocuments();
    }

    // Get Lease Document By ID
    @GetMapping("/{id}")
    public LeaseDocument getLeaseDocumentById(@PathVariable Long id) {
        return leaseDocumentService.getLeaseDocumentById(id);
    }

    // Update Lease Document
    @PutMapping("/{id}")
    public LeaseDocument updateLeaseDocument(@PathVariable Long id,
                                             @RequestBody LeaseDocument document) {

        return leaseDocumentService.updateLeaseDocument(id, document);
    }

    // Delete Lease Document
    @DeleteMapping("/{id}")
    public String deleteLeaseDocument(@PathVariable Long id) {

        leaseDocumentService.deleteLeaseDocument(id);

        return "Lease Document deleted successfully.";
    }

}