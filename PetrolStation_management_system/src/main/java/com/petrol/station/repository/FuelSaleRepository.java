package com.petrol.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petrol.station.modal.FuelSales;

@Repository
public interface FuelSaleRepository extends JpaRepository<FuelSales, Long> {

}
