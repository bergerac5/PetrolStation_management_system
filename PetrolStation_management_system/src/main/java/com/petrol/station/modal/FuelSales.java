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
public class FuelSales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "FuelTransactionId")
    private FuelTransaction transactionId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "pumpId")
    private FuelPump pump;
    private Double amountPaid;
    @UpdateTimestamp
    private LocalDateTime saleDate;

    @Transient
    private Long transactionIdTransient;

    @Transient
    private Long userIdTransient;

    @Transient
    private Integer pumpIdTransient;
    public FuelSales() {
        // Default Constructor
    }    
}
