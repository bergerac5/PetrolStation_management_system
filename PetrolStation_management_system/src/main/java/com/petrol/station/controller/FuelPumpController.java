package com.petrol.station.controller;


import java.util.List;


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

import com.petrol.station.modal.FuelPump;
import com.petrol.station.response.MessageResponse;
import com.petrol.station.service.FuelPumpService;

@RestController
@RequestMapping
public class FuelPumpController {

    private final FuelPumpService fuelPumpService;

    public FuelPumpController(FuelPumpService fuelPumpService) {
        this.fuelPumpService = fuelPumpService;
    }

    @PostMapping(
        value = "/auth/savePump",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> savePump(@RequestBody FuelPump fuelPump) {
        String fuelType = fuelPump.getFuelTypeTransient();
        if (fuelType == null) {
            return new ResponseEntity<>(new MessageResponse("Fuel type cannot be null"), HttpStatus.BAD_REQUEST);
        }
    
        MessageResponse messageResponse = fuelPumpService.savePump(fuelPump, fuelType);
        HttpStatus httpStatus;    
    
        switch (messageResponse.getMessage()) {
            case "Fuel pump Added Successfully":
                httpStatus = HttpStatus.CREATED;
                break;
            case "Fuel Type Not Found":
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            case "Invalid Fuel Type ID":
            case "Invalid Pump":
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            default:
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
                break;
        }
    
        return new ResponseEntity<>(messageResponse, httpStatus);
    }
    


    @PutMapping(
        value = "/auth/updatePump",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> updatePump(@RequestBody FuelPump fuelPump){
        String fuelType = fuelPump.getFuelTypeTransient();
        if (fuelType == null) {
            return new ResponseEntity<>(new MessageResponse("Fuel type cannot be null"), HttpStatus.BAD_REQUEST);
        }
    
        MessageResponse messageResponse = fuelPumpService.updatePump(fuelPump,fuelType);
        HttpStatus httpStatus;
    
        switch (messageResponse.getMessage()) {
            case "Fuel pump Added Successfully":
                httpStatus = HttpStatus.CREATED;
                break;
            case "Fuel Type Not Found":
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            case "Invalid Fuel Type ID":
            case "Invalid Pump":
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            default:
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
                break;
        }
    
        return new ResponseEntity<>(messageResponse, httpStatus);
    }

    @DeleteMapping(value = "/auth/deletePump")
    public ResponseEntity<MessageResponse> deletePump(@RequestBody FuelPump fuelPump){
        MessageResponse messageResponse = fuelPumpService.deletePump(fuelPump);
        if (messageResponse.getMessage().equalsIgnoreCase("Fuel pump deleted Seccessfully")) {
            return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.CREATED);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Fuel pump Not Found")) {
            return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.NOT_FOUND); 
        }else if (messageResponse.getMessage().equalsIgnoreCase("Invalid pump")) {
            return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.BAD_REQUEST); 
        }else{
            return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.NOT_ACCEPTABLE); 
        }
    }

    @GetMapping(
        value = "/auth/displayPump",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<FuelPump>> displayAllPump(){
        List<FuelPump> fuelPumps = fuelPumpService.getAllPump();
        return ResponseEntity.ok(fuelPumps);
    }
}
