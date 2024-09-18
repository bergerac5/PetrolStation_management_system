package com.petrol.station.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petrol.station.modal.FuelTransaction;
import com.petrol.station.response.MessageResponse;
import com.petrol.station.service.FuelTransactionService;

@RestController

public class FuelTransactionController {

    @Autowired
    private FuelTransactionService fuelTransactionService;

    @PostMapping(
        value = "/auth/saveTransaction",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> saveTransaction(@RequestBody FuelTransaction transaction){
        String fuelType = transaction.getFuelTypeTransient();
        if (fuelType == null) {
            return new ResponseEntity<>(new MessageResponse("Fuel type cannot be null"), HttpStatus.BAD_REQUEST);
        }

        Long userId = transaction.getUserIdTransient();
        if (userId == null) {
            return new ResponseEntity<>(new MessageResponse("User Id cannot be null"), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = fuelTransactionService.saveTransaction(transaction, fuelType, userId);
        HttpStatus httpStatus;

        switch (messageResponse.getMessage()) {
            case "Fuel Transaction Added Successfully":
                httpStatus = HttpStatus.CREATED;
                break;
            case "Fuel Type not Exist":
            case "User Not Exit":
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            case "Invalid Transaction":
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case "Fuel Transaction Already Exist":
                httpStatus = HttpStatus.CONFLICT;    
            default:
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
                break;
        }

        return new ResponseEntity<>(messageResponse,httpStatus);
    }

    @PutMapping(
        value = "/auth/updateTransaction",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> updateTransaction(@RequestBody FuelTransaction transaction){
        String fuelType = transaction.getFuelTypeTransient();
        if (fuelType == null) {
            return new ResponseEntity<>(new MessageResponse("Fuel type cannot be null"), HttpStatus.BAD_REQUEST);
        }

        Long userId = transaction.getUserIdTransient();
        if (userId == null) {
            return new ResponseEntity<>(new MessageResponse("User Id cannot be null"), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = fuelTransactionService.updateTransaction(transaction, fuelType, userId);
        HttpStatus httpStatus;

        switch (messageResponse.getMessage()) {
            case "Fuel Transaction Added Successfully":
                httpStatus = HttpStatus.CREATED;
                break;
            case "Fuel Type not Exist":
            case "User Not Exit":
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            case "Invalid Transaction":
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case "Fuel Transaction Already Exist":
                httpStatus = HttpStatus.CONFLICT;    
            default:
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
                break;
        }

        return new ResponseEntity<>(messageResponse,httpStatus);         
    }

    @PutMapping(
        value = "/auth/updateStatus",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> changeStatus(@RequestBody FuelTransaction transaction){
        MessageResponse messageResponse = fuelTransactionService.updateTransactionStatus(transaction);
        if (messageResponse.getMessage().equalsIgnoreCase("Transaction Updated Successfully")) {
            return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.CREATED);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Transaction Not found")) {
            return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_ACCEPTABLE); 
        }
    }

    @DeleteMapping(value = "/auth/deleteTransaction")
    public ResponseEntity<MessageResponse> deleteTransaction(@RequestBody FuelTransaction transaction){
        MessageResponse messageResponse = fuelTransactionService.deleteTransaction(transaction);
        if (messageResponse.getMessage().equalsIgnoreCase("Transaction Deleted Successfully")) {
            return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.CREATED);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Transaction Not found")) {
            return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.NOT_FOUND);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Invalid Transaction")) {
            return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_ACCEPTABLE); 
        }         
    }

    @GetMapping(
        value = "/auth/displayTransaction",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<FuelTransaction>> displayAllTransaction(){
        List<FuelTransaction> transactions = fuelTransactionService.getAllTransaction();
        return ResponseEntity.ok(transactions);      
    }

    @GetMapping(
        value = "/auth/displayUserTransaction",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<FuelTransaction>> displayUserTransaction(@RequestBody FuelTransaction transaction){
        List<FuelTransaction> transactions = fuelTransactionService.getAllUserTransaction(transaction);
        return ResponseEntity.ok(transactions);  
    }
}
