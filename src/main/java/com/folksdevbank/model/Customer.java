package com.folksdevbank.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Table(name = "Customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
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
    //private String address;//bunun yerine one to one ilişki kurucam private Address address derim.
    /*
        @OneToOne(cascade = CascadeType.ALL)
        JoinColumn(name="address_id",referencedColumnName="id");

    */

    //fetch vermezsek cutomer adresi adres customer'ı yükler sonusuz döngü oluşur.Burdaki mantık set<Address> i customer verilerini
    // çektiğimde hemen görmek istemiyosam o an sadece customer verilerine ihtiyacım varsa lazy derim ben set etmediğim sürece adres
    // bilgilerini bana getirme der.Eager tam tersi customerın içinde adres bilgilerinide getir demek.
    //mappedby one to many de one olan kısım hangisiyse onu belirtir.
    @OneToMany(fetch = FetchType.LAZY,mappedBy ="customer",cascade = CascadeType.ALL)
    private Set<Address> address;



}
