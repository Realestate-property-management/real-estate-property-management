package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.LeaseDocument;

public interface LeaseDocumentService {

    LeaseDocument saveLeaseDocument(LeaseDocument document);

    LeaseDocument updateLeaseDocument(Long id, LeaseDocument document);

    void deleteLeaseDocument(Long id);

    LeaseDocument getLeaseDocumentById(Long id);

    List<LeaseDocument> getAllLeaseDocuments();

    LeaseDocument uploadLeaseDocument(Long leaseId,
                                      Long userId,
                                      MultipartFile file);

}