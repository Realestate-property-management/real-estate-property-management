package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.example.demo.models.Property;
import com.example.demo.service.PropertyService;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    // Create Property
    @PostMapping
    public Property saveProperty(@Valid @RequestBody Property property) {
        return propertyService.saveProperty(property);
    }

    // Get All Properties
    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    // Get Property By ID
    @GetMapping("/{id}")
    public Property getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id);
    }

    // Update Property
    @PutMapping("/{id}")
    public Property updateProperty(@PathVariable Long id,
                                   @Valid @RequestBody Property property) {
        return propertyService.updateProperty(id, property);
    }

    // Delete Property
    @DeleteMapping("/{id}")
    public String deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return "Property deleted successfully.";
    }
}