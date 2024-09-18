package com.petrol.station.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petrol.station.modal.FuelInventory;

@Repository
public interface FuelInventoryRepository extends JpaRepository<FuelInventory, Integer> {
    FuelInventory findByFuelType(String fuelType);

    FuelInventory findByFuelId(Integer fuelId);
}