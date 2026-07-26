package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Long propertyId;

    @NotBlank(message = "Property name is required")
    @Size(max = 100, message = "Property name cannot exceed 100 characters")
    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @NotBlank(message = "Property code is required")
    @Size(max = 30, message = "Property code cannot exceed 30 characters")
    @Column(name = "property_code", unique = true)
    private String propertyCode;

    @NotBlank(message = "Address Line 1 is required")
    @Size(max = 255, message = "Address Line 1 cannot exceed 255 characters")
    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City cannot exceed 50 characters")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 50, message = "State cannot exceed 50 characters")
    @Column(name = "state")
    private String state;

    @NotBlank(message = "Postal code is required")
    @Pattern(
        regexp = "^[0-9]{6}$",
        message = "Postal code must be a valid 6-digit PIN code"
    )
    @Column(name = "postal_code")
    private String postalCode;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country cannot exceed 50 characters")
    @Column(name = "country")
    private String country;

    @NotBlank(message = "Property type is required")
    @Size(max = 50, message = "Property type cannot exceed 50 characters")
    @Column(name = "property_type")
    private String propertyType;

    @NotNull(message = "Total units is required")
    @Min(value = 1, message = "Total units must be at least 1")
    @Column(name = "total_units")
    private Integer totalUnits;

    @NotNull(message = "Occupied units is required")
    @Min(value = 0, message = "Occupied units cannot be negative")
    @Column(name = "occupied_units")
    private Integer occupiedUnits;

    @Min(value = 1800, message = "Enter a valid construction year")
    @Column(name = "year_built")
    private Integer yearBuilt;

    @NotNull(message = "Property manager is required")
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @NotBlank(message = "Status is required")
    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default Constructor
    public Property() {
    }

    // Parameterized Constructor
    public Property(Long propertyId,
                    String propertyName,
                    String propertyCode,
                    String addressLine1,
                    String addressLine2,
                    String city,
                    String state,
                    String postalCode,
                    String country,
                    String propertyType,
                    Integer totalUnits,
                    Integer occupiedUnits,
                    Integer yearBuilt,
                    User manager,
                    String status,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {

        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.propertyCode = propertyCode;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.propertyType = propertyType;
        this.totalUnits = totalUnits;
        this.occupiedUnits = occupiedUnits;
        this.yearBuilt = yearBuilt;
        this.manager = manager;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(Integer totalUnits) {
        this.totalUnits = totalUnits;
    }

    public Integer getOccupiedUnits() {
        return occupiedUnits;
    }

    public void setOccupiedUnits(Integer occupiedUnits) {
        this.occupiedUnits = occupiedUnits;
    }

    public Integer getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(Integer yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}