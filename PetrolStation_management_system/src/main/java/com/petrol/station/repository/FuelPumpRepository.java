package com.petrol.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petrol.station.modal.FuelPump;



@Repository
public interface FuelPumpRepository extends JpaRepository<FuelPump, Integer> {
    FuelPump findByPumpId(Integer pumpId);
}
