package com.folksdevbank.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name="name")
    private String name;

    @Column(name="dateOfBirth")
    private int dateOfBirth;

    @Column(name="city")
    private City city;

    @Column(name="address")
    private String address;
}
