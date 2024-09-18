package com.petrol.station.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petrol.station.modal.FuelInventory;
import com.petrol.station.modal.FuelTransaction;
import com.petrol.station.modal.Users;
import com.petrol.station.repository.FuelInventoryRepository;
import com.petrol.station.repository.FuelTransactionRepository;
import com.petrol.station.repository.UsersRepository;
import com.petrol.station.response.MessageResponse;

@Service
public class FuelTransactionService {

    @Autowired
    private FuelTransactionRepository fuelTransactionRepository;

    @Autowired
    private FuelInventoryRepository fuelInventoryRepository;

    @Autowired
    private UsersRepository usersRepository;

    //Save Transaction
    public MessageResponse saveTransaction(FuelTransaction saveTransaction, String fuelTypeTransient, Long userIdTransient){
        MessageResponse resp = new MessageResponse();
        if (saveTransaction == null) {
            resp.setMessage("Invalid Transaction");
        }

        if (fuelTypeTransient == null) {
            resp.setMessage("Fuel type cannot be null");
        }

        if (userIdTransient == null) {
            resp.setMessage("User Id cannot be null");
        }

        FuelInventory fuelType = fuelInventoryRepository.findByFuelType(fuelTypeTransient);
        if (fuelType == null) {
            resp.setMessage("Fuel Type not Exist");
        }

        Users ckeckUser = usersRepository.findByUserId(userIdTransient);
        if (ckeckUser == null) {
            resp.setMessage("User Not Exit");
        }

        if (saveTransaction.getFuelTransactionId() == null || !fuelTransactionRepository.existsById(saveTransaction.getFuelTransactionId())) {
            saveTransaction.setFuelType(fuelType);
            saveTransaction.setUser(ckeckUser);
            fuelTransactionRepository.save(saveTransaction);
            resp.setMessage("Fuel Transaction Updated Successfully");
        }else{
            resp.setMessage("Fuel Transaction Already Exist");
        }
        return resp;
    }

    //Update Transaction
    public MessageResponse updateTransaction(FuelTransaction updateTransaction, String fuelTypeTransient, Long userIdTransient){
        MessageResponse resp = new MessageResponse();
        if (updateTransaction == null) {
            resp.setMessage("Invalid Transaction");
        }

        if (fuelTypeTransient == null) {
            resp.setMessage("Fuel type cannot be null");
        }

        if (userIdTransient == null) {
            resp.setMessage("User Id cannot be null");
        }

        FuelInventory fuelType = fuelInventoryRepository.findByFuelType(fuelTypeTransient);
        if (fuelType == null) {
            resp.setMessage("Fuel Type not Exist");
        }

        Users ckeckUser = usersRepository.findByUserId(userIdTransient);
        if (ckeckUser == null) {
            resp.setMessage("User Not Exit");
        }

        if (updateTransaction.getFuelTransactionId() != null || fuelTransactionRepository.existsById(updateTransaction.getFuelTransactionId())) {
            updateTransaction.setFuelType(fuelType);
            updateTransaction.setUser(ckeckUser);
            fuelTransactionRepository.save(updateTransaction);
            resp.setMessage("Fuel Transaction Added Successfully");
        }else{
            resp.setMessage("Fuel Transaction Already Exist");
        }
        return resp;
    }

    //change Transaction Status
    public MessageResponse updateTransactionStatus(FuelTransaction transaction){
        MessageResponse resp = new MessageResponse();
        if (transaction != null) {
            boolean checkTransaction = fuelTransactionRepository.existsById(transaction.getFuelTransactionId());
            if (checkTransaction == true) {
                transaction.setStatus("Used");
                fuelTransactionRepository.save(transaction);
                resp.setMessage("Transaction Updated Successfully");
            }else{
                resp.setMessage("Transaction Not found");
            }
        }else{
            resp.setMessage("Invalid Transaction");
        }
        return resp;
    }

    //Delete Transaction
    public MessageResponse deleteTransaction(FuelTransaction deleteTransaction){
        MessageResponse resp = new MessageResponse();
        if (deleteTransaction != null) {
            boolean checkTransaction = fuelTransactionRepository.existsById(deleteTransaction.getFuelTransactionId());
            if (checkTransaction == true) {           
                fuelTransactionRepository.delete(deleteTransaction);
                resp.setMessage("Transaction Deleted Successfully");                
            }else{
                resp.setMessage("Transaction Not found");
            }
        }else{
            resp.setMessage("Invalid Transaction");
        }
        return resp;
    }

    //Display Transaction
    public List<FuelTransaction> getAllTransaction(){
        return fuelTransactionRepository.findAll();
    }

    //Display Transaction By UserId
    public List<FuelTransaction> getAllUserTransaction(FuelTransaction transaction){
        return fuelTransactionRepository.findByUser(transaction.getUser());
    }
}
