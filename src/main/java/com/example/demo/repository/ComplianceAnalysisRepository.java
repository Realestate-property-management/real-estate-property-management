package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.ComplianceAnalysis;

@Repository
public interface ComplianceAnalysisRepository extends JpaRepository<ComplianceAnalysis, Long> {

    List<ComplianceAnalysis> findByStatus(String status);

}