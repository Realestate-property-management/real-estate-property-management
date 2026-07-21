package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Contractor;
import com.example.demo.repository.ContractorRepository;
import com.example.demo.service.ContractorService;

@Service
public class ContractorServiceImpl implements ContractorService {

    @Autowired
    private ContractorRepository contractorRepository;

    @Override
    public Contractor saveContractor(Contractor contractor) {
        return contractorRepository.save(contractor);
    }

    @Override
    public Contractor updateContractor(Long id, Contractor contractor) {

        Contractor existingContractor = contractorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contractor not found with ID: " + id));

        existingContractor.setCompanyName(contractor.getCompanyName());
        existingContractor.setContactPerson(contractor.getContactPerson());
        existingContractor.setEmail(contractor.getEmail());
        existingContractor.setPhone(contractor.getPhone());
        existingContractor.setServiceType(contractor.getServiceType());
        existingContractor.setAddress(contractor.getAddress());
        existingContractor.setRating(contractor.getRating());
        existingContractor.setStatus(contractor.getStatus());
        existingContractor.setCreatedAt(contractor.getCreatedAt());
        existingContractor.setUpdatedAt(contractor.getUpdatedAt());

        return contractorRepository.save(existingContractor);
    }

    @Override
    public void deleteContractor(Long id) {

        Contractor contractor = contractorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contractor not found with ID: " + id));

        contractorRepository.delete(contractor);
    }

    @Override
    public Contractor getContractorById(Long id) {

        return contractorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contractor not found with ID: " + id));
    }

    @Override
    public List<Contractor> getAllContractors() {

        return contractorRepository.findAll();
    }
}