package com.example.demo.service;

import java.util.List;
import com.example.demo.models.Property;

public interface PropertyService {

    Property saveProperty(Property property);

    Property updateProperty(Long id, Property property);

    void deleteProperty(Long id);

    Property getPropertyById(Long id);

    List<Property> getAllProperties();

}