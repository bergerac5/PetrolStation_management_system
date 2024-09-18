package com.petrol.station.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.petrol.station.modal.Users;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageResponse {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String userPhone;
    private String userAddress;
    private String userName;
    private String userPassword;
    private String userRole;
    private Users ourUsers;
    private List<Users> oursersList;

    public MessageResponse() {}

    public MessageResponse(String message) {
        this.message = message;
    }
}
