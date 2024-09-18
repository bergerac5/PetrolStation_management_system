package com.petrol.station.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.petrol.station.modal.Users;
import com.petrol.station.repository.UsersRepository;
import com.petrol.station.response.MessageResponse;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // method for registrate
    public MessageResponse saveUser(MessageResponse user) {
        MessageResponse resp = new MessageResponse();
        if (user != null) {
            
                Users saveUsers = new Users();
                saveUsers.setFirstName(user.getFirstName());
                saveUsers.setLastName(user.getLastName());
                saveUsers.setUserEmail(user.getUserEmail());
                saveUsers.setUserRole(user.getUserRole());
                saveUsers.setUserAddress(user.getUserAddress());
                saveUsers.setUserName(user.getUserName());
                saveUsers.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
                saveUsers.setUserRole(user.getUserRole());
                saveUsers.setUserPhone(user.getUserPhone());
                Users savedUsers = usersRepository.save(saveUsers);
                if (savedUsers != null) {
                    resp.setOurUsers(savedUsers);
                    resp.setMessage("User Registered successfully");
                }
            
        } else {
            resp.setMessage("Invalid User");
        }
        return resp;
    }

    // method for update
    public MessageResponse updateUser(Users updateUser) {
        MessageResponse resp = new MessageResponse();
        if (updateUser != null && updateUser.getUserId() != 0) {
            boolean checkUser = usersRepository.existsById(updateUser.getUserId());
            if (checkUser == true) {
                usersRepository.save(updateUser);
                resp.setMessage("User Info updated successfully");
            } else {
                resp.setMessage("User not found");
            }
        } else {
            resp.setMessage("Invalid User");
        }
        return resp;

    }

    // method for delete
    public MessageResponse deleteUser(Users deleteUser) {
        MessageResponse resp = new MessageResponse();
        if (deleteUser != null && deleteUser.getUserId() != 0) {
            boolean checkUser = usersRepository.existsById(deleteUser.getUserId());
            if (checkUser == true) {
                usersRepository.delete(deleteUser);

                resp.setMessage("User deleted successfully");

            } else {
                resp.setMessage("User not found");
            }
        } else {
            resp.setMessage("Invalid User");
        }
        return resp;
    }

    // method to display
    public MessageResponse getAllUsers() {
        MessageResponse resp = new MessageResponse();
        try {
            List<Users> result = usersRepository.findAll();
            if (!result.isEmpty()) {
                resp.setOursersList(result);
                resp.setStatusCode(200);
                resp.setMessage("Successfull");
            }else{
                resp.setStatusCode(404);
                resp.setMessage("No User found");  
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("error accured + " + e.getMessage());
            
        }
        return resp;
    }

    // Login
    public MessageResponse login(MessageResponse loginRequest) {
        MessageResponse response = new MessageResponse();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(),
                            loginRequest.getUserPassword()));
            var userToken = usersRepository.findByUserEmail(loginRequest.getUserEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(userToken);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), userToken);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setUserRole(userToken.getUserRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logger in");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    // RefreshToken
    public MessageResponse refreshToken(MessageResponse refreshTokenRequest) {
        MessageResponse response = new MessageResponse();
        try {
            String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            Users users = usersRepository.findByUserEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hrs");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    // Search
    public MessageResponse getMyInfo(String email) {
        MessageResponse response = new MessageResponse();
        try {
            Optional<Users> userOptional = usersRepository.findByUserEmail(email);
            if (userOptional.isPresent()) {
                response.setOurUsers(userOptional.get());
                response.setStatusCode(200);
                response.setMessage("successfully");
            } else {
                response.setStatusCode(500);
                response.setMessage("User Not Found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("error: " + e.getMessage());
        }
        return response;
    }

    public MessageResponse getUserById(Long id){
        MessageResponse response = new MessageResponse();
        try {
             Users UserId = usersRepository.findByUserId(id);
            if (UserId != null) {
                response.setOurUsers(UserId);
                response.setStatusCode(200);
                response.setMessage("User found");                
            }else{
                response.setStatusCode(404);
                response.setMessage("User NOT found");
            }            
        } catch (Exception e) {
            response.setStatusCode(500);
                response.setMessage("Error accured " +e.getMessage());
        }
        return response;
    }
}
