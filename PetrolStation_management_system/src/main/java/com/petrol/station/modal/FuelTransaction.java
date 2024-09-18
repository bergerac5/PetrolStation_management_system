package com.petrol.station.modal;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class FuelTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fuelTransactionId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "fuel_id")
    private FuelInventory fuelType;

    private Double quantity;
    private Double totalPrice;

    @UpdateTimestamp
    private LocalDateTime transactionTime;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'Unused'")
    private String status;
    @JsonIgnore
    @OneToOne(mappedBy = "transactionId")
    private FuelSales sale;

    @Transient
    private String fuelTypeTransient;

    @Transient
    private Long userIdTransient;

    public FuelTransaction() {
        // Default Constructor
        this.status = "Unused";
    }
        
}