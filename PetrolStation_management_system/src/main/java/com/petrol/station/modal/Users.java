package com.petrol.station.modal;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Users_Table")
public class Users implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String userPhone;
    private String userAddress;
    @Column(name = "user_name", unique = true)
    private String userName;    
    private String userPassword;
    @Column(name = "user_role", columnDefinition = "varchar(255) default 'USER'")
    private String userRole;
    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<FuelTransaction> transactions;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER) 
    private List<FuelSales> sales;   

    public Users() {
        this.userRole = "USER";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole));
    }

    @Override
    public String getPassword() {
        
        return userPassword;
    }

    @Override
    public String getUsername() {
        
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        
        return true;
    }

    @Override
    public boolean isEnabled() {
        
        return true;
    }
}