package com.example.demo.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.integration.S3Service;
import com.example.demo.models.Lease;
import com.example.demo.models.LeaseDocument;
import com.example.demo.models.User;
import com.example.demo.repository.LeaseDocumentRepository;
import com.example.demo.repository.LeaseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LeaseDocumentService;

@Service
public class LeaseDocumentServiceImpl implements LeaseDocumentService {

    @Autowired
    private LeaseDocumentRepository leaseDocumentRepository;

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;

    @Override
    public LeaseDocument saveLeaseDocument(LeaseDocument document) {
        return leaseDocumentRepository.save(document);
    }

    @Override
    public LeaseDocument updateLeaseDocument(Long id, LeaseDocument document) {

        LeaseDocument existingDocument = leaseDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease Document not found with ID: " + id));

        existingDocument.setLease(document.getLease());
        existingDocument.setOriginalFileName(document.getOriginalFileName());
        existingDocument.setStoredFileName(document.getStoredFileName());
        existingDocument.setS3Bucket(document.getS3Bucket());
        existingDocument.setS3ObjectKey(document.getS3ObjectKey());
        existingDocument.setFileSize(document.getFileSize());
        existingDocument.setContentType(document.getContentType());
        existingDocument.setUploadStatus(document.getUploadStatus());
        existingDocument.setUploadedBy(document.getUploadedBy());
        existingDocument.setUploadedAt(document.getUploadedAt());

        return leaseDocumentRepository.save(existingDocument);
    }

    @Override
    public void deleteLeaseDocument(Long id) {

        LeaseDocument existingDocument = leaseDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease Document not found with ID: " + id));

        leaseDocumentRepository.delete(existingDocument);
    }

    @Override
    public LeaseDocument getLeaseDocumentById(Long id) {

        return leaseDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease Document not found with ID: " + id));
    }

    @Override
    public List<LeaseDocument> getAllLeaseDocuments() {

        return leaseDocumentRepository.findAll();
    }

    // ==============================
    // Upload Lease Document to S3
    // ==============================
    @Override
    public LeaseDocument uploadLeaseDocument(Long leaseId,
                                             Long userId,
                                             MultipartFile file) {

        // Upload file to Amazon S3
        s3Service.uploadFile(file);

        // Get Lease
        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() -> new RuntimeException("Lease not found"));

        // Get User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create LeaseDocument
        LeaseDocument document = new LeaseDocument();

        document.setLease(lease);
        document.setUploadedBy(user);

        document.setOriginalFileName(file.getOriginalFilename());
        document.setStoredFileName(file.getOriginalFilename());

        document.setS3Bucket("propaudit-lease-documents");
        document.setS3ObjectKey(file.getOriginalFilename());

        document.setFileSize(file.getSize());
        document.setContentType(file.getContentType());

        document.setUploadStatus("UPLOADED");
        document.setUploadedAt(LocalDateTime.now());

        return leaseDocumentRepository.save(document);
    }
}