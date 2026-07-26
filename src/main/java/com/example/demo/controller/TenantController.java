package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.example.demo.models.Tenant;
import com.example.demo.service.TenantService;

@RestController
@RequestMapping("/api/tenants")
@CrossOrigin(origins = "*")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    // Create Tenant
    @PostMapping
    public Tenant saveTenant(@Valid @RequestBody Tenant tenant) {
        return tenantService.saveTenant(tenant);
    }

    // Get All Tenants
    @GetMapping
    public List<Tenant> getAllTenants() {
        return tenantService.getAllTenants();
    }

    // Get Tenant By ID
    @GetMapping("/{id}")
    public Tenant getTenantById(@PathVariable Long id) {
        return tenantService.getTenantById(id);
    }

    // Update Tenant
    @PutMapping("/{id}")
    public Tenant updateTenant(@PathVariable Long id,
                               @Valid @RequestBody Tenant tenant) {
        return tenantService.updateTenant(id, tenant);
    }

    // Delete Tenant
    @DeleteMapping("/{id}")
    public String deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return "Tenant deleted successfully.";
    }
}