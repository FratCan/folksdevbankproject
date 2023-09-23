package com.folksdevbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseCustomerRequest {

    private String name;
    private int dateOfBirth;
    private CityDto city;
    private String address;
}
