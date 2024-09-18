package com.petrol.station.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petrol.station.modal.FuelTransaction;
import com.petrol.station.modal.Users;

@Repository
public interface FuelTransactionRepository extends JpaRepository<FuelTransaction, Long>{
    List<FuelTransaction> findByUser(Users user);
    FuelTransaction findByFuelTransactionId(Long fuelTransactionId);
}
