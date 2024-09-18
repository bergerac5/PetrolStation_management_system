package com.petrol.station.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petrol.station.modal.Users;
import java.util.List;
import java.util.Optional;




@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{
    List<Users> findByUserName(String userName);
    Users findByUserId(Long userId);
    Optional<Users> findByUserEmail(String userEmail);
}
