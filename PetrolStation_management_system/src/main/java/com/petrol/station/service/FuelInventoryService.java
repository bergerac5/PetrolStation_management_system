package com.petrol.station.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petrol.station.modal.FuelInventory;
import com.petrol.station.repository.FuelInventoryRepository;
import com.petrol.station.response.MessageResponse;

@Service
public class FuelInventoryService {

    @Autowired
    private FuelInventoryRepository fuelInventoryReposistory;

    //save Inventory
    public MessageResponse saveInventory(FuelInventory fuelInventory){
        MessageResponse resp = new MessageResponse();
        if (fuelInventory != null) {
            FuelInventory checkType = fuelInventoryReposistory.findByFuelType(fuelInventory.getFuelType());
            if (checkType != null) {
                resp.setMessage("Fuel Type Already exist");
            }else{
                fuelInventoryReposistory.save(fuelInventory);
                resp.setMessage("Fuel Inventory Added Successfully");
            }
        }else{
            resp.setMessage("Invalid Inventory");
        }
        return resp;
    }

    //update Inventory
    public MessageResponse updateInventory(FuelInventory updateInventory){
        MessageResponse resp = new MessageResponse();
        if (updateInventory != null) {
            boolean checkInventory = fuelInventoryReposistory.existsById(updateInventory.getFuelId());
            if (checkInventory == true) {
                fuelInventoryReposistory.save(updateInventory);
                resp.setMessage("Fuel Inventory Updated Successfully");
            }else{
                resp.setMessage("Fuel Inventory Not Exist");
            }
        }else{
            resp.setMessage("Invalid Fuel Inventory");
        }
        return resp;
    }

    //delete by id
    public MessageResponse deleteInventoryById(Integer id){
        MessageResponse resp = new MessageResponse();
        if (id != null) {
            boolean checkInventory = fuelInventoryReposistory.existsById(id);
            if (checkInventory) {
                fuelInventoryReposistory.deleteById(id);
                resp.setMessage("Fuel Inventory Deleted Successfully");
            } else {
                resp.setMessage("Fuel Inventory Not Exist");
            }
        } else {
            resp.setMessage("Invalid Fuel Inventory Id");
        }
        return resp;
    }

    //delete Inventory
    public MessageResponse deleteInventory(FuelInventory deleteInventory){
        MessageResponse resp = new MessageResponse();
        if (deleteInventory != null) {
            boolean checkInventory = fuelInventoryReposistory.existsById(deleteInventory.getFuelId());
            if (checkInventory == true) {
                fuelInventoryReposistory.delete(deleteInventory);
                resp.setMessage("Fuel Inventory Deleted Successfully");
            }else{
                resp.setMessage("Fuel Inventory Not Exist");
            }
        }else{
            resp.setMessage("Invalid Fuel Inventory");
        }
        return resp;
    }

    //Display Inventory
    public List<FuelInventory> getAllInventory(){
        return fuelInventoryReposistory.findAll();
    }

    // Get Inventory by Id
    public FuelInventory getInventoryById(Integer id) {
        Optional<FuelInventory> inventory = fuelInventoryReposistory.findById(id);
        if (inventory.isPresent()) {
            return inventory.get();
        } else {
            return null; // or throw an exception, based on your requirement
        }
    }
    
}
