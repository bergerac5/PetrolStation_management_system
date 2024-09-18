package com.petrol.station.controller;
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
import org.springframework.web.bind.annotation.RestController;


import com.petrol.station.modal.Users;
import com.petrol.station.response.MessageResponse;
import com.petrol.station.service.UsersService;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping(
        value = "/auth/saveUser",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> saveUser(@RequestBody MessageResponse users){
        MessageResponse messageResponse = usersService.saveUser(users);
        if (messageResponse.getMessage().equalsIgnoreCase("User Registrated successfully")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
        }else if (messageResponse.getMessage().equalsIgnoreCase("UserName already Exist")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CONFLICT);
        }else if (messageResponse.getMessage().equalsIgnoreCase("User Not Registrated")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Invalid User")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND); 
        }
    }

    @PutMapping(
        value = "/auth/updateUser"
    )
    public ResponseEntity<MessageResponse> updateUser(@RequestBody Users users){
        MessageResponse messageResponse = usersService.updateUser(users);
        if (messageResponse.getMessage().equalsIgnoreCase("User Info updated successfully")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
        }else if (messageResponse.getMessage().equalsIgnoreCase("User not found")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
        }else if (messageResponse.getMessage().equalsIgnoreCase("User not updated")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Invalid User")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_ACCEPTABLE); 
        }
    }

    @DeleteMapping(
        value = "/auth/deleteUser"
    )
    public ResponseEntity<MessageResponse> deleteUser(@RequestBody Users users){
        MessageResponse messageResponse = usersService.deleteUser(users);
        if (messageResponse.getMessage().equalsIgnoreCase("User deleted successfully")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
        }else if (messageResponse.getMessage().equalsIgnoreCase("User not found")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
        }else if (messageResponse.getMessage().equalsIgnoreCase("Invalid User")) {
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_ACCEPTABLE); 
        }
    }

    @GetMapping(
        value = "/auth/displayUser",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity <MessageResponse>fetchAllUser(){
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    @GetMapping(
        value = "/auth/getUser/{UserId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> getUserById(@PathVariable Long UserId){
        return ResponseEntity.ok(usersService.getUserById(UserId));
    }

    @PostMapping(
        value = "/auth/login",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> login(@RequestBody MessageResponse response){
        return ResponseEntity.ok(usersService.login(response));
    }

    @PostMapping(
        value = "/auth/refresh",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MessageResponse> refreshToken(@RequestBody MessageResponse response){
        return ResponseEntity.ok(usersService.refreshToken(response));
    }
}
