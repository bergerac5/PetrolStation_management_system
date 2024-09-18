package com.petrol.station.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petrol.station.modal.FuelInventory;
import com.petrol.station.modal.FuelPump;
import com.petrol.station.repository.FuelInventoryRepository;
import com.petrol.station.repository.FuelPumpRepository;
import com.petrol.station.response.MessageResponse;

@Service
public class FuelPumpService {

    @Autowired
    private FuelPumpRepository fuelPumpRepository;

    @Autowired
    private FuelInventoryRepository fuelInventoryRepository;

    public MessageResponse savePump(FuelPump savePump, String fuelTypeTransient) {
        MessageResponse resp = new MessageResponse();
        if (savePump == null) {
            resp.setMessage("Invalid Pump");
        }
    
        if (fuelTypeTransient == null) {
            resp.setMessage("Fuel type cannot be null");
        }
    
        FuelInventory fuelName = fuelInventoryRepository.findByFuelType(fuelTypeTransient);
        if (fuelName == null) {
            resp.setMessage("Fuel Type Not Found: " + fuelTypeTransient);
        }
    
        // Check if savePump is a new pump (not yet persisted)
        if (savePump.getPumpId() == null || !fuelPumpRepository.existsById(savePump.getPumpId())) {
            // Set the fuel type and save the pump
            savePump.setFuelType(fuelName);
            fuelPumpRepository.save(savePump);
            resp.setMessage("Fuel pump added successfully");
        } else {
            resp.setMessage("Fuel pump already exists");
        }
        return resp;
    }    

    //Update Pump
    public MessageResponse updatePump (FuelPump updatedPump, String fuelTypeTransient){
        MessageResponse resp = new MessageResponse();
        if (updatedPump == null) {
            resp.setMessage("Invalid Pump");
        }
    
        if (fuelTypeTransient == null) {
            resp.setMessage("Fuel type cannot be null");
        }
    
        FuelInventory fuelName = fuelInventoryRepository.findByFuelType(fuelTypeTransient);
        if (fuelName == null) {
            resp.setMessage("Fuel Type Not Found: " + fuelTypeTransient);
        }
    
        // Check if savePump is a new pump (not yet persisted)
        if (updatedPump.getPumpId() != null || fuelPumpRepository.existsById(updatedPump.getPumpId())) {
            // Set the fuel type and save the pump
            updatedPump.setFuelType(fuelName);
            fuelPumpRepository.save(updatedPump);
            resp.setMessage("Fuel pump Updated successfully");
        } else {
            resp.setMessage("Fuel pump already exists");
        }
        return resp;
    }

    //delete Pump
    public MessageResponse deletePump (FuelPump deletedPump){
        MessageResponse resp = new MessageResponse();
        if (deletedPump != null) {
            boolean checkPump = fuelPumpRepository.existsById(deletedPump.getPumpId());
            if (checkPump == true) {
                fuelPumpRepository.delete(deletedPump);
                resp.setMessage("Fuel pump deleted Seccessfully");
            }else{
                resp.setMessage("Fuel pump Not Found");
            }
        }else{
            resp.setMessage("Invalid pump ");
        }
        return resp;
    }

    //display Pump
    public List<FuelPump> getAllPump(){
        return fuelPumpRepository.findAll();
    }
}
