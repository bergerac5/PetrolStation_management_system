package com.petrol.station.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petrol.station.modal.FuelInventory;
import com.petrol.station.response.MessageResponse;
import com.petrol.station.service.FuelInventoryService;


@RestController
public class FuelInventoryController {

    @Autowired
    private FuelInventoryService fuelInventoryService;

    @PostMapping(
        value = "/auth/saveInventory",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> saveInventory(@RequestBody FuelInventory inventory){
        MessageResponse messageResponse = fuelInventoryService.saveInventory(inventory);
        if (messageResponse.getMessage().equalsIgnoreCase("Fuel Inventory Not Exist")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CONFLICT);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Fuel Inventory Added Successfully")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Invalid Inventory")){
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(
        value = "/auth/updateInventory",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> updateInventory(@RequestBody FuelInventory inventory){
        MessageResponse messageResponse = fuelInventoryService.updateInventory(inventory);
        if (messageResponse.getMessage().equalsIgnoreCase("Fuel Inventory Not Exist")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.FORBIDDEN);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Fuel Inventory Updated Successfully")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Invalid Fuel Inventory")){
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/auth/deleteInventory")
    public ResponseEntity<MessageResponse> deleteInventory(@RequestBody FuelInventory inventory){
        MessageResponse messageResponse = fuelInventoryService.deleteInventory(inventory);
        if (messageResponse.getMessage().equalsIgnoreCase("Fuel Inventory Not Exist")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.FORBIDDEN);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Fuel Inventory Deleted Successfully")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Invalid Fuel Inventory")){
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/auth/deleteInventory/{id}")
    public ResponseEntity<MessageResponse> deleteInventoryById(@PathVariable("id") Integer id) {
        MessageResponse messageResponse = fuelInventoryService.deleteInventoryById(id);
        if (messageResponse.getMessage().equalsIgnoreCase("Fuel Inventory Deleted Successfully")) {
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        } else if (messageResponse.getMessage().equalsIgnoreCase("Fuel Inventory Not Exist")) {
            return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(
        value = "/auth/displayInventory",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<FuelInventory>> displayAllInventory(){
        List<FuelInventory> fuelInventories = fuelInventoryService.getAllInventory();
        return ResponseEntity.ok(fuelInventories);
    }

    @GetMapping(
        value = "/auth/inventory/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FuelInventory> getInventoryById(@PathVariable("id") Integer id) {
        FuelInventory inventory = fuelInventoryService.getInventoryById(id);
        if (inventory != null) {
            return ResponseEntity.ok(inventory);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


