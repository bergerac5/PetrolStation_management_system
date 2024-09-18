package com.petrol.station.modal;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class FuelInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fuelId;
    private String fuelType;
    private Double Fuel_quantity;
    private Double Fuel_pricePerUnity;
    @UpdateTimestamp
    private LocalDateTime Last_Updated;
    @JsonIgnore    
    @OneToOne(mappedBy = "fuelType")
    private FuelTransaction transaction;
    @JsonIgnore    
    @OneToMany(mappedBy = "fuelType",fetch = FetchType.EAGER)
    private List<FuelPump> pump;
    public FuelInventory() {
        
    }  

    public FuelInventory(Integer fuelId, String fuelType, Double fuel_quantity, Double fuel_pricePerUnity,
            LocalDateTime last_Updated, FuelTransaction transaction, List<FuelPump> pump) {
        this.fuelId = fuelId;
        this.fuelType = fuelType;
        Fuel_quantity = fuel_quantity;
        Fuel_pricePerUnity = fuel_pricePerUnity;
        Last_Updated = last_Updated;
        this.transaction = transaction;
        this.pump = pump;
    }


    public String getFuelType() {
        return fuelType;
    }
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
    public FuelTransaction getTransaction() {
        return transaction;
    }
    public void setTransaction(FuelTransaction transaction) {
        this.transaction = transaction;
    }
    public Double getFuel_quantity() {
        return Fuel_quantity;
    }
    public void setFuel_quantity(Double fuel_quantity) {
        Fuel_quantity = fuel_quantity;
    }
    public Double getFuel_pricePerUnity() {
        return Fuel_pricePerUnity;
    }
    public void setFuel_pricePerUnity(Double fuel_pricePerUnity) {
        Fuel_pricePerUnity = fuel_pricePerUnity;
    }
    public LocalDateTime getLast_Updated() {
        return Last_Updated;
    }
    public void setLast_Updated(LocalDateTime last_Updated) {
        Last_Updated = last_Updated;
    }

    

    public Integer getFuelId() {
        return fuelId;
    }

    public void setFuelId(Integer fuelId) {
        this.fuelId = fuelId;
    }

    public List<FuelPump> getPump() {
        return pump;
    }

    public void setPump(List<FuelPump> pump) {
        this.pump = pump;
    }

    
}
