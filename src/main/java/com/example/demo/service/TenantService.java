package com.example.demo.service;

import java.util.List;
import com.example.demo.models.Tenant;

public interface TenantService {

    Tenant saveTenant(Tenant tenant);

    Tenant updateTenant(Long id, Tenant tenant);

    void deleteTenant(Long id);

    Tenant getTenantById(Long id);

    List<Tenant> getAllTenants();

}