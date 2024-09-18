package com.petrol.station.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petrol.station.modal.FuelPump;
import com.petrol.station.modal.FuelSales;
import com.petrol.station.modal.FuelTransaction;
import com.petrol.station.modal.Users;
import com.petrol.station.repository.FuelPumpRepository;
import com.petrol.station.repository.FuelSaleRepository;
import com.petrol.station.repository.FuelTransactionRepository;
import com.petrol.station.repository.UsersRepository;
import com.petrol.station.response.MessageResponse;

@Service
public class FuelSaleService {

    @Autowired
   private FuelPumpRepository fuelPumpRepository;

   @Autowired
   private FuelTransactionRepository fuelTransactionRepository;

   @Autowired
   private UsersRepository usersRepository;

   @Autowired
   private FuelSaleRepository fuelSaleRepository;

   // save Sales Transaction
   public MessageResponse saveSale (FuelSales sales, Long transactionIdTransient, Long userIdTransient, Integer pumpIdTransient){
        MessageResponse resp = new MessageResponse();
        if (sales == null) {
            resp.setMessage("Invalid Sales");            
        }

        if (transactionIdTransient == null) {
            resp.setMessage("Transaction Id cannot null");  
        }

        if (userIdTransient == null) {
            resp.setMessage("User Id cannot null");
        }

        if (pumpIdTransient == null) {
            resp.setMessage("Pump Id cannot null");
        }

        FuelTransaction checkTransaction = fuelTransactionRepository.findByFuelTransactionId(transactionIdTransient);
        if (checkTransaction == null) {
            resp.setMessage("Transaction Id not found");
        }

        Users checkUser = usersRepository.findByUserId(userIdTransient);
        if (checkUser == null) {
            resp.setMessage("User Id Not found"); 
        }

        FuelPump checkPump = fuelPumpRepository.findByPumpId(pumpIdTransient);
        if (checkPump == null) {
            resp.setMessage("Pump Id Not found");
        }

        if (sales.getSaleId() == null || !fuelSaleRepository.existsById(sales.getSaleId())) {
            sales.setTransactionId(checkTransaction);
            sales.setUser(checkUser);
            sales.setPump(checkPump);
            fuelSaleRepository.save(sales);
            resp.setMessage("Sales Transaction Added Successfully");
        }else{
            resp.setMessage("Sales Transaction Already Exist");
        }
        return resp;
   }

   //Update sales
   public MessageResponse UpdateSale (FuelSales sales, Long transactionIdTransient, Long userIdTransient, Integer pumpIdTransient){
    MessageResponse resp = new MessageResponse();
    if (sales == null) {
        resp.setMessage("Invalid Sales");            
    }

    if (transactionIdTransient == null) {
        resp.setMessage("Transaction Id cannot null");  
    }

    if (userIdTransient == null) {
        resp.setMessage("User Id cannot null");
    }

    if (pumpIdTransient == null) {
        resp.setMessage("Pump Id cannot null");
    }

    FuelTransaction checkTransaction = fuelTransactionRepository.findByFuelTransactionId(transactionIdTransient);
    if (checkTransaction == null) {
        resp.setMessage("Transaction Id not found");
    }

    Users checkUser = usersRepository.findByUserId(userIdTransient);
    if (checkUser == null) {
        resp.setMessage("User Id Not found"); 
    }

    FuelPump checkPump = fuelPumpRepository.findByPumpId(pumpIdTransient);
    if (checkPump == null) {
        resp.setMessage("Pump Id Not found");
    }

    if (sales.getSaleId() != null || fuelSaleRepository.existsById(sales.getSaleId())) {
        sales.setTransactionId(checkTransaction);
        sales.setUser(checkUser);
        sales.setPump(checkPump);
        fuelSaleRepository.save(sales);
        resp.setMessage("Sales Transaction Updated Successfully");
    }else{
        resp.setMessage("Sales Transaction Already Exist");
    }
    return resp;
   }

    //Delete Sales
    public MessageResponse DeleteSale ( FuelSales sales){
        MessageResponse resp = new MessageResponse();
        if (sales != null) {
            boolean checkSales = fuelSaleRepository.existsById(sales.getSaleId());
            if (checkSales == true) {
                    
                fuelSaleRepository.delete(sales);
                resp.setMessage("Sales Deleted Successfully");
            }else{
                resp.setMessage("Sales Not Exist");
            }            
                       
        }else{
            resp.setMessage("Invalid Sales");
        }
        return resp;
    }
    
    //Get All Sales
    public List<FuelSales> getAllSales(){
        return fuelSaleRepository.findAll();
    }
}
