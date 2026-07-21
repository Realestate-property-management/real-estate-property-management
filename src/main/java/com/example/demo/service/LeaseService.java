package com.example.demo.service;

import java.util.List;
import com.example.demo.models.Lease;

public interface LeaseService {

    Lease saveLease(Lease lease);

    Lease updateLease(Long id, Lease lease);

    void deleteLease(Long id);

    Lease getLeaseById(Long id);

    List<Lease> getAllLeases();

}