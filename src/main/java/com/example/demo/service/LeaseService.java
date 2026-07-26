package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.Lease;

public interface LeaseService {

    // Create Lease
    Lease saveLease(Lease lease);

    // Update Lease
    Lease updateLease(Long id, Lease lease);

    // Delete Lease
    void deleteLease(Long id);

    // Get Lease By ID
    Lease getLeaseById(Long id);

    // Get All Leases
    List<Lease> getAllLeases();

    // Upload Lease PDF to Amazon S3
    Lease uploadLeaseDocument(Long leaseId, MultipartFile file);

    // Download Lease PDF from Amazon S3
    byte[] downloadLeaseDocument(Long leaseId);

    // Delete Lease PDF from Amazon S3
    void deleteLeaseDocument(Long leaseId);
}