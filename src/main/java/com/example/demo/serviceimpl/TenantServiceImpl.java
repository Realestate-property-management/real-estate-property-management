package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Tenant;
import com.example.demo.repository.TenantRepository;
import com.example.demo.service.TenantService;

@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public Tenant saveTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant updateTenant(Long id, Tenant tenant) {

        Tenant existingTenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + id));

        existingTenant.setFirstName(tenant.getFirstName());
        existingTenant.setLastName(tenant.getLastName());
        existingTenant.setEmail(tenant.getEmail());
        existingTenant.setPhone(tenant.getPhone());
        existingTenant.setDateOfBirth(tenant.getDateOfBirth());
        existingTenant.setEmergencyContactName(tenant.getEmergencyContactName());
        existingTenant.setEmergencyContactPhone(tenant.getEmergencyContactPhone());
        existingTenant.setStatus(tenant.getStatus());
        existingTenant.setUpdatedAt(tenant.getUpdatedAt());

        return tenantRepository.save(existingTenant);
    }

    @Override
    public void deleteTenant(Long id) {

        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + id));

        tenantRepository.delete(tenant);
    }

    @Override
    public Tenant getTenantById(Long id) {

        return tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + id));
    }

    @Override
    public List<Tenant> getAllTenants() {

        return tenantRepository.findAll();
    }

}