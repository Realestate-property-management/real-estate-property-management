package com.example.demo.service;

import java.util.List;
import com.example.demo.models.Contractor;

public interface ContractorService {

    Contractor saveContractor(Contractor contractor);

    Contractor updateContractor(Long id, Contractor contractor);

    void deleteContractor(Long id);

    Contractor getContractorById(Long id);

    List<Contractor> getAllContractors();

}