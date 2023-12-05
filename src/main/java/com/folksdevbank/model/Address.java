package com.folksdevbank.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Address {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id",updatable = false,nullable = false)
    private String id;

    private City city;
    private String postcode;
    private String addressDetails;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id",nullable = false,referencedColumnName = "id")
    //referencedColumnName = "id" burdaki isimle customer ın id si aynı isimli olmalı.
    //nullable false diyerek her adresin bir tane customer'ı olsun derim.
    //bir tane customer id ekleyeceği için hangi customerı ekleyeceğini yazıyoruz.
    private Customer customer;


}

