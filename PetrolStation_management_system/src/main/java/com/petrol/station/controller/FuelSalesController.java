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

import com.petrol.station.modal.FuelSales;
import com.petrol.station.response.MessageResponse;
import com.petrol.station.service.FuelSaleService;

@RestController
public class FuelSalesController {

    @Autowired
    private FuelSaleService saleService;

    @PostMapping(
        value = "/auth/saveSales",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> saveSales(@RequestBody FuelSales sales){        
        Integer pumpId = sales.getPumpIdTransient();
        if (pumpId ==  null) {
            return new ResponseEntity<>(new MessageResponse("Pump Id cannot null"), HttpStatus.BAD_REQUEST);
        }

        Long transactionId = sales.getTransactionIdTransient();
        if (transactionId == null) {
            return new ResponseEntity<>(new MessageResponse("Transaction Id cannot null"), HttpStatus.BAD_REQUEST);
        }

        Long userId = sales.getUserIdTransient();
        if (userId == null) {
            return new ResponseEntity<>(new MessageResponse("Transaction Id cannot null"), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = saleService.saveSale(sales, transactionId, userId, pumpId);
        HttpStatus httpStatus;

        switch (messageResponse.getMessage()) {
            case "Sales Transaction Added Successfully":
                httpStatus = HttpStatus.CREATED;
                break;
            case "Sales Transaction Already Exist":
                httpStatus = HttpStatus.CONFLICT;
                break;
            case "Transaction Id not found":
            case "User Id Not found":
            case "Pump Id Not found":
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            default:
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
                break;
        }
        return new ResponseEntity<>(messageResponse, httpStatus);
    }

    @PutMapping(
        value = "/auth/updateSales",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> updateSales(@RequestBody FuelSales sales){
        Integer pumpId = sales.getPumpIdTransient();
        if (pumpId ==  null) {
            return new ResponseEntity<>(new MessageResponse("Pump Id cannot null"), HttpStatus.BAD_REQUEST);
        }

        Long transactionId = sales.getTransactionIdTransient();
        if (transactionId == null) {
            return new ResponseEntity<>(new MessageResponse("Transaction Id cannot null"), HttpStatus.BAD_REQUEST);
        }

        Long userId = sales.getUserIdTransient();
        if (userId == null) {
            return new ResponseEntity<>(new MessageResponse("Transaction Id cannot null"), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = saleService.UpdateSale(sales, transactionId, userId, pumpId);
        HttpStatus httpStatus;

        switch (messageResponse.getMessage()) {
            case "Sales Transaction Updated Successfully":
                httpStatus = HttpStatus.CREATED;
                break;
            case "Sales Transaction Already Exist":
                httpStatus = HttpStatus.CONFLICT;
                break;
            case "Transaction Id not found":
            case "User Id Not found":
            case "Pump Id Not found":
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            default:
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
                break;
        }
        return new ResponseEntity<>(messageResponse, httpStatus);
    }

    @DeleteMapping(value = "/auth/deleteSales")
    public ResponseEntity<MessageResponse> deleteSales(@RequestBody FuelSales sales){
        MessageResponse messageResponse = saleService.DeleteSale(sales);
        if (messageResponse.getMessage().equalsIgnoreCase("Sales Deleted Successfully")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Sales Not Exist")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.FORBIDDEN);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Invalid Sales")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_ACCEPTABLE);
        }else{
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(
        value = "/auth/displaySales",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<FuelSales>> displayAllSales(){
        List<FuelSales> Allsales = saleService.getAllSales();
        return ResponseEntity.ok(Allsales);
    }
}
