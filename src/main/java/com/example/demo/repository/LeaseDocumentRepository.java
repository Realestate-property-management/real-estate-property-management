package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.LeaseDocument;

@Repository
public interface LeaseDocumentRepository extends JpaRepository<LeaseDocument, Long> {

}