package com.folksdevbank.model;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "Account")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode //test sınıfındaki account ile service sınıfındakinin aynı nesne olmamasından kaynaklanan hatayı yok eder.
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
