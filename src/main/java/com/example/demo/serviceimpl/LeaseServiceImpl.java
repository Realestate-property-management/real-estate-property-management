package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Lease;
import com.example.demo.repository.LeaseRepository;
import com.example.demo.service.LeaseService;

@Service
public class LeaseServiceImpl implements LeaseService {

    @Autowired
    private LeaseRepository leaseRepository;

    @Override
    public Lease saveLease(Lease lease) {
        return leaseRepository.save(lease);
    }

    @Override
    public Lease updateLease(Long id, Lease lease) {

        Lease existingLease = leaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease not found with ID: " + id));

        existingLease.setLeaseNumber(lease.getLeaseNumber());
        existingLease.setProperty(lease.getProperty());
        existingLease.setTenant(lease.getTenant());
        existingLease.setUnitNumber(lease.getUnitNumber());
        existingLease.setStartDate(lease.getStartDate());
        existingLease.setEndDate(lease.getEndDate());
        existingLease.setMonthlyRent(lease.getMonthlyRent());
        existingLease.setSecurityDeposit(lease.getSecurityDeposit());
        existingLease.setLeaseStatus(lease.getLeaseStatus());
        existingLease.setAnalysisStatus(lease.getAnalysisStatus());
        existingLease.setCreatedBy(lease.getCreatedBy());
        existingLease.setUpdatedAt(lease.getUpdatedAt());

        return leaseRepository.save(existingLease);
    }

    @Override
    public void deleteLease(Long id) {

        Lease lease = leaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease not found with ID: " + id));

        leaseRepository.delete(lease);
    }

    @Override
    public Lease getLeaseById(Long id) {

        return leaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease not found with ID: " + id));
    }

    @Override
    public List<Lease> getAllLeases() {

        return leaseRepository.findAll();
    }
}