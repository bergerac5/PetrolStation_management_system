package com.petrol.station.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class FuelPump {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pumpId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Fuel_id")
    private FuelInventory fuelType;

    private String pumpLocation;
    private String pumpStatus;

    @OneToOne(mappedBy = "pump")
    private FuelSales sale;

    @Transient
    private String fuelTypeTransient; 

    // Default constructor
    public FuelPump() {}

    

    public Integer getPumpId() {
        return pumpId;
    }

    public void setPumpId(Integer pumpId) {
        this.pumpId = pumpId;
    }

    public FuelInventory getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelInventory fuelType) {
        this.fuelType = fuelType;
    }

    public String getPumpLocation() {
        return pumpLocation;
    }

    public void setPumpLocation(String pumpLocation) {
        this.pumpLocation = pumpLocation;
    }

    public String getPumpStatus() {
        return pumpStatus;
    }

    public void setPumpStatus(String pumpStatus) {
        this.pumpStatus = pumpStatus;
    }

    public FuelSales getSale() {
        return sale;
    }

    public void setSale(FuelSales sale) {
        this.sale = sale;
    }

    public String getFuelTypeTransient() {
        return fuelTypeTransient;
    }

    public void setFuelTypeTransient(String fuelTypeTransient) {
        this.fuelTypeTransient = fuelTypeTransient;
    }
}