package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.ComplianceIssue;

@Repository
public interface ComplianceIssueRepository extends JpaRepository<ComplianceIssue, Long> {

    List<ComplianceIssue> findByRiskLevel(String riskLevel);

}