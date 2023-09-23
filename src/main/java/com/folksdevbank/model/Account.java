package com.folksdevbank.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name="customerId")
    private String customerId;

    @Column(name="balance")
    private double balance;

    @Column(name="city")
    private City city;

    @Column(name="currency")
    private Currency currency;
}
