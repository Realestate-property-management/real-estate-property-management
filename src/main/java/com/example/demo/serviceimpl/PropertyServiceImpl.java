package com.example.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Property;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public Property updateProperty(Long id, Property property) {

        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));

        existingProperty.setPropertyName(property.getPropertyName());
        existingProperty.setPropertyCode(property.getPropertyCode());
        existingProperty.setAddressLine1(property.getAddressLine1());
        existingProperty.setAddressLine2(property.getAddressLine2());
        existingProperty.setCity(property.getCity());
        existingProperty.setState(property.getState());
        existingProperty.setPostalCode(property.getPostalCode());
        existingProperty.setCountry(property.getCountry());
        existingProperty.setPropertyType(property.getPropertyType());
        existingProperty.setTotalUnits(property.getTotalUnits());
        existingProperty.setOccupiedUnits(property.getOccupiedUnits());
        existingProperty.setYearBuilt(property.getYearBuilt());
        existingProperty.setManager(property.getManager());
        existingProperty.setStatus(property.getStatus());
        existingProperty.setUpdatedAt(property.getUpdatedAt());

        return propertyRepository.save(existingProperty);
    }

    @Override
    public void deleteProperty(Long id) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));

        propertyRepository.delete(property);
    }

    @Override
    public Property getPropertyById(Long id) {

        return propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));
    }

    @Override
    public List<Property> getAllProperties() {

        return propertyRepository.findAll();
    }

}