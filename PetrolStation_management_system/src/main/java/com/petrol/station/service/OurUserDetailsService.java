package com.petrol.station.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.petrol.station.repository.UsersRepository;

@Service
public class OurUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;    

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return usersRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
    }

}
